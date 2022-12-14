package it.unibo.paserver.web.controller.task;

import it.unibo.participact.domain.PANotification;
import it.unibo.participact.domain.PANotificationConst;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.gamificationlogic.PointsStrategy;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.PointsStrategyForTaskService;
import it.unibo.paserver.service.PointsStrategyService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.GCMController;
import it.unibo.paserver.web.controller.badge.IdTaskHolder;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Controller
public class TaskAddController {

	@Autowired
	GCMController gcmController;

	
	@Autowired
	TaskService taskService;

	@Autowired
	TaskUserService taskUserService;

	@Autowired
	UserService userService;

	@Autowired
	PointsStrategyService pointsStrategyService;

	@Autowired
	PointsStrategyForTaskService pointsStrategyForTaskService;

	@Autowired
	FriendshipService friendshipService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskAddController.class);

	public TaskAssignedUsersForm initTaskAssignedUsers() {
		return new TaskAssignedUsersForm();
	}

	public Map<Integer, String> initAvailablePipelines(Task task) {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();

		// Get available pipelines in alphabetical order
		List<Pipeline.Type> ptypes = new ArrayList<Pipeline.Type>();
		for (Pipeline.Type ptype : Pipeline.Type.values()) {
			ptypes.add(ptype);
		}
		Collections.sort(ptypes, new PipelineTypesComparator());

		result.put(null, "");
		// make only unused pipelines available
		for (Pipeline.Type ptype : ptypes) {
			if (ptype == Pipeline.Type.DUMMY) {
				// skip the dummy pipeline
				continue;
			}
			boolean addPType = true;
			for (Action action : task.getActions()) {
				if (action instanceof ActionSensing) {
					ActionSensing actionSensing = (ActionSensing) action;
					if (actionSensing.getInput_type() == ptype.toInt()) {
						addPType = false;
					}
				}
			}
			if (addPType) {
				result.put(ptype.toInt(), ptype.toString());
			}
		}
		return result;
	}

	
	public Boolean hasActivityDetection(Task task) {
		return task.hasActivityDetection();
	}

	public Event addActionToTask(Task task, Action action) {
		EventFactorySupport eventFactorySupport = new EventFactorySupport();
		task.getActions().add(action);
		return eventFactorySupport.success(task);
	}

	public Task initTask() {
		Task task = new Task();
		task.setCanBeRefused(true);
		task.setStart(new DateTime());
		task.setDeadline(task.getStart());
		task.setDuration(0L);
		task.setSensingDuration(0L);
		task.setActions(new LinkedHashSet<Action>());
		return task;
	}

	public ActionSensing initActionSensing() {
		return new ActionSensing();
	}

	public ActionPhoto initActionPhoto() {
		ActionPhoto result = new ActionPhoto();
		result.setNumeric_threshold(1);
		return result;
	}

	public ActionQuestionaire initActionQuestionaire() {
		ActionQuestionaire result = new ActionQuestionaire();
		return result;
	}

	public Question initOpenQuestion(ActionQuestionaire actionQuestionaire) {
		Question result = new Question();
		int questionOrder = actionQuestionaire.getNextQuestionOrder();
		result.setQuestionOrder(questionOrder);
		return result;
	}

	public SimpleMultipleQuestion initSimpleMultipleQuestion() {
		return new SimpleMultipleQuestion();
	}

	public Event validateWTK(String wktString, String source,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();
		if (StringUtils.isEmpty(wktString)) {
			messageBuilder.source(source);
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(source);
		}
		WKTReader wktReader = new WKTReader();
		String[] shapesStr = StringUtils.split(wktString, ";");
		for (String shapeStr : shapesStr) {
			try {
				if (StringUtils.isEmpty(shapeStr)) {
					throw new ParseException("Empty string");
				}
				wktReader.read(shapeStr);
			} catch (ParseException e) {
				logger.warn("String {} is not valid WKT", shapeStr);
				messageBuilder.source(source);
				messageBuilder.code("invalid.task");
				messageContext.addMessage(messageBuilder.build());
				return new EventFactorySupport().error(source);
			}
		}
		return new EventFactorySupport().success(source);
	}

	public Event validateTaskDescription(Task task,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();
		if (StringUtils.isBlank(task.getName())) {
			messageBuilder.source("name");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (StringUtils.isBlank(task.getDescription())) {
			messageBuilder.source("description");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getStart() == null) {
			messageBuilder.source("start");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getStart().isAfter(task.getDeadline())) {
			messageBuilder.source("start");
			messageBuilder.code("invalid.task.start");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getDeadline() == null) {
			messageBuilder.source("deadline");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getDeadline().isBeforeNow()) {
			messageBuilder.source("deadline");
			messageBuilder.code("invalid.task.deadline");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getDuration() == null) {
			messageBuilder.source("duration");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getDuration() <= 0) {
			messageBuilder.source("duration");
			messageBuilder.code("negative.task.duration");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		return new EventFactorySupport().success(task);
	}

	public Event validateActivityDetection(Task task,
			ActionActivityDetection activityDetection,
			MessageContext messageContext) {
		if (activityDetection == null) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("duration_threshold");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}
		if (activityDetection.getDuration_threshold() == null
				|| activityDetection.getDuration_threshold() < 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("duration_threshold");
			messageBuilder.code("negative.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}
		task.getActions().add(activityDetection);
		return new EventFactorySupport().success(task);
	}

	public Event validateActionSensing(Task task, ActionSensing actionSensing,
			MessageContext messageContext) {
		if (actionSensing == null || actionSensing.getInput_type() == null) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("input_type");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		return new EventFactorySupport().success(task);
	}

	public Event validateActionPhoto(Task task, ActionPhoto actionSensing,
			MessageContext messageContext) {
		if (actionSensing == null
				|| StringUtils.isBlank(actionSensing.getName())) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("name");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (actionSensing.getNumeric_threshold() == null) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("numeric_threshold");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (actionSensing.getNumeric_threshold() < 1) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("numeric_threshold");
			messageBuilder.code("greatherthanzero.task.numeric_threshold");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		return new EventFactorySupport().success(task);
	}

	public Event validateActionQuestionnaire(Task task,
			ActionQuestionaire actionQuestionaire, MessageContext messageContext) {
		if (actionQuestionaire == null
				|| StringUtils.isBlank(actionQuestionaire.getTitle())) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("title");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		return new EventFactorySupport().success(task);
	}

	public Event validateOpenQuestion(ActionQuestionaire actionQuestionaire,
			Question openQuestion, MessageContext messageContext) {
		if (openQuestion == null
				|| StringUtils.isBlank(openQuestion.getQuestion())) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("question");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(openQuestion);
		}
		actionQuestionaire.getQuestions().add(openQuestion);
		openQuestion.setIsClosedAnswers(false);
		openQuestion.setIsMultipleAnswers(false);
		return new EventFactorySupport().success(openQuestion);
	}

	public Event validateClosedQuestion(ActionQuestionaire actionQuestionaire,
			SimpleMultipleQuestion simpleMultipleQuestion,
			boolean singleChoice, MessageContext messageContext) {
		int numAnswers = 0;
		int i = 0;
		int firstEmptyAnswer = -1;

		if (StringUtils.isBlank(simpleMultipleQuestion.getQuestion())) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("question");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(simpleMultipleQuestion);
		}


		// count non empty answers
		for (String s : simpleMultipleQuestion.getAnswers()) {
			if (!StringUtils.isBlank(s)) {
				numAnswers++;
			} else if (firstEmptyAnswer < 0) {
				firstEmptyAnswer = i;
			}
			i++;
		}

		if (numAnswers < 2) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source(String
					.format("answers[%d]", firstEmptyAnswer));
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(simpleMultipleQuestion);
		}

		Question question = new Question();
		question.setIsClosedAnswers(true);
		question.setIsMultipleAnswers(!singleChoice);
		question.setQuestion(simpleMultipleQuestion.getQuestion());
		question.setQuestionOrder(actionQuestionaire.getNextQuestionOrder());
		for (String q : simpleMultipleQuestion.getAnswers()) {
			if (!StringUtils.isBlank(q)) {
				ClosedAnswer ca = new ClosedAnswer();
				ca.setQuestion(question);
				ca.setAnswerDescription(q);
				ca.setAnswerOrder(question.getNextClosedAnswerOrder());
				question.getClosed_answers().add(ca);
			}
		}
		actionQuestionaire.getQuestions().add(question);
		return new EventFactorySupport().success(simpleMultipleQuestion);
	}

	public void validateAndAddQuestionnaire(Task task,
			ActionQuestionaire actionQuestionaire) {
		if (actionQuestionaire.getQuestions().size() > 0) {
			task.getActions().add(actionQuestionaire);
		}
	}

	private class PipelineTypesComparator implements Comparator<Pipeline.Type> {

		@Override
		public int compare(Pipeline.Type o1, Pipeline.Type o2) {
			return o1.toString().compareTo(o2.toString());
		}

	}

	public Event createTask(Task task) {
		purgeDuration(task);
		task = taskService.save(task);
		return new EventFactorySupport().success(task);

	}

	public Event createTaskUser(Task task,Principal principal,Collection<String> friends) {
		User user = userService.getUser(principal.getName());
		purgeDuration(task);
		
		TaskUser taskUser = new TaskUser();
		taskUser.setOwner(user);
		taskUser.setTask(task);
		
		if(friends != null && !friends.isEmpty()){
			List<User> friendsUser = new ArrayList<User>();
			for(String s : friends)	{
				User u = userService.getUser(s);
				friendsUser.add(u);
			}
			taskUser.setUsersToAssign(new HashSet<User>(friendsUser));
		}
		
		taskUserService.save(taskUser);
		return new EventFactorySupport().success(task);	
	}

	public Event createAndAssignTask(Task task, Collection<String> users, StrategyHolder strategyHolder) {
		purgeDuration(task);

		// assign task to users and save it to DB
		task = taskService.assignTaskToUsers(task, users);

		pointsStrategyForTaskService.create(task, strategyHolder.getId());

		// notify users about the new task via GCM
		gcmController.notifyUsers(PANotification.Type.NEW_TASK, users);
		return new EventFactorySupport().success(task);
	}

	/**
	 * Removes sensing duration from task if it has no passive sensing actions.
	 * 
	 * @param task
	 *            Task to clean.
	 */
	public void purgeDuration(Task task) {
		// remove sensing duration if there are no sensing actions
		boolean hasSensing = false;
		for (Action a : task.getActions()) {
			if (a instanceof ActionSensing) {
				hasSensing = true;
				break;
			}
		}
		if (!hasSensing) {
			task.setSensingDuration(null);
		}
	}


	
	public Event validateSelectedUsersForTaskUser( Principal creator, TaskAssignedUsersForm assignedUsers,MessageContext messageContext)
	{
		List<String> unknown = new ArrayList<String>();
		List<String> inactive = new ArrayList<String>();
		List<String> notFriends = new ArrayList<String>();
		getAsUsers(creator,assignedUsers.getUserList(), unknown, inactive, notFriends);
		if (unknown.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("unknown.assignedUsersForm.listUser");
			String[] args = new String[] { StringUtils.join(inactive, ", ") };
			messageBuilder.args(args);
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		if(notFriends.size() > 0 ) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("notFriends.assignedUsersForm.listUser");
			String[] args = new String[] { StringUtils.join(inactive, ", ") };
			messageBuilder.args(args);
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
			
		}
		if (inactive.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("inactive.assignedUsersForm.listUser");
			String[] args = new String[] { StringUtils.join(inactive, ", ") };
			messageBuilder.args(args);
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		return new EventFactorySupport().success(assignedUsers);

		
	}

	

	

	public Event validateSelectedUsers(TaskAssignedUsersForm assignedUsers,
			MessageContext messageContext) {
		List<String> unknown = new ArrayList<String>();
		List<String> inactive = new ArrayList<String>();
		getAsUsers(assignedUsers.getUserList(), unknown, inactive);
		if (unknown.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("unknown.assignedUsersForm.listUser");
			String[] args = new String[] { StringUtils.join(inactive, ", ") };
			messageBuilder.args(args);
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		if (inactive.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("inactive.assignedUsersForm.listUser");
			String[] args = new String[] { StringUtils.join(inactive, ", ") };
			messageBuilder.args(args);
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		return new EventFactorySupport().success(assignedUsers);
	}

	public Event validateSensingDuration(Task task,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (task.getSensingDuration() == null) {
			messageBuilder.source("sensingDuration");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		if (task.getSensingDuration() <= 0) {
			messageBuilder.source("sensingDuration");
			messageBuilder.code("negative.task.sensingduration");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(task);
		}

		return new EventFactorySupport().success(task);
	}

	public ActionActivityDetection initActionActivityDetection() {
		return new ActionActivityDetection();
	}

	public Collection<String> getAsUsers(Principal principal, String userList,
			List<String> unknown, List<String> inactive, List<String> notFriends) {
		
		User creator = userService.getUser(principal.getName()); 
		Collection<String> users = getAsUsers(userList, unknown, inactive);
		List<Friendship> friendsReceiver = friendshipService.getFriendshipsForUserAndStatus(creator.getId(),FriendshipStatus.ACCEPTED, true);
		List<Friendship> friendsSender = friendshipService.getFriendshipsForUserAndStatus(creator.getId(),FriendshipStatus.ACCEPTED, false);

		List<String> receiver = new ArrayList<String>();
		List<String> sender = new ArrayList<String>();
		
		for(Friendship f : friendsReceiver)
			receiver.add(f.getReceiver().getOfficialEmail());
		for(Friendship f : friendsSender)
			sender.add(f.getSender().getOfficialEmail());
		for(String u : users)
		{
			if(!receiver.contains(u) && !sender.contains(u))
			{
				users.remove(u);
				notFriends.add(u);
			}
		}
		return users;
	}
	
	/**
	 * Transforms a string of users IDs or emails to a list of users
	 * 
	 * @param userList
	 *            String to analyze
	 * @param unknownStrings
	 *            Strings within userList that do not match any existing user.
	 *            Can be null.
	 * @param inactiveUsers
	 *            Strings within userList that match inactive users (see
	 *            {@link User#getIsActive()}). Can be null.
	 * @return List of users.
	 */
	public Collection<String> getAsUsers(String userList,
			Collection<String> unknownStrings, Collection<String> inactiveUsers) {
		Set<String> users = new LinkedHashSet<String>();
		if (userList == null || userList.length() == 0) {
			return users;
		}
		String[] userStrings = userList.split("[^a-zA-Z0-9@.]");
		Arrays.sort(userStrings);
		for (String userString : userStrings) {
			// discard blank lines
			if (StringUtils.isBlank(userString)) {
				continue;
			}
			User u = null;
			// look for user by id and official email
			u = userService.getUser(userString);

			if (u == null) {
				// the string does not identify a user
				if (unknownStrings != null) {
					unknownStrings.add(userString);
				}
				continue;
			} else {
				if (u.getIsActive()) {
					users.add(userString);
				} else {
					// the user is not active
					if (inactiveUsers != null) {
						inactiveUsers.add(userString);
					}
				}
			}
		}
		return users;
	}
	
	

	public void copyNotificationFromActivation(Task task) {
		task.setNotificationArea(task.getActivationArea());
	}

	public void copyActivationFromNotification(Task task) {
		task.setActivationArea(task.getNotificationArea());
	}

	@RequestMapping(value = "/protected/activeUsers", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody List<String> clientVersion() {
		return userService.getActiveUsers();
	}
	
	@RequestMapping(value = "/protected/allUsers", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody List<String> getAllUsers() {
		List<String> result;
		List<User> allUsers = userService.getUsers();
		if(allUsers!=null) {
			result = new ArrayList<String>(allUsers.size());
			for(User currentUser:allUsers)
				result.add(currentUser.getOfficialEmail());
			return result;
		}
		else {
			result = new ArrayList<String>(1);
			result.add("");
			return result;
		}
	}
	
	public String initAllUsers() {
		List<String> list = getAllUsers();
		String resultString = "[";
		int len = list.size();
		for(int i=0;i<len;i++){
			resultString+="\""+list.get(i)+"\"";
			if(!(i==len-1))
				resultString+=",";
		}
		resultString+="]";
		return resultString;
	}
	
	@RequestMapping(value = "/protected/friends/me", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody List<String> getFriendsOfPrincipal(Principal p) {
		User user = userService.getUser(p.getName());
		logger.info("Received request getFriendsOf {}", user.getOfficialEmail());
		
		List<String> resutlList = new ArrayList<String>();


		if(user!=null) {

			Long id = user.getId();

			List<Friendship> friendships = friendshipService.getFriendshipsForUserAndStatus(id, FriendshipStatus.ACCEPTED);

			for(Friendship currentFriendship: friendships) {
				if(currentFriendship.getSender().getId() ==id)
					resutlList.add(currentFriendship.getReceiver().getOfficialEmail());
				else
					resutlList.add(currentFriendship.getSender().getOfficialEmail());
			}

			return resutlList;

		}
		else {
			resutlList.add("");
			return resutlList;
		}
	}

	@RequestMapping(value = "/protected/friends/{mail:.+}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody List<String> getFriendsOf(@PathVariable String mail) {
		
		logger.info("Received request getFriendsOf {}", mail);
		
		List<String> resutlList = new ArrayList<String>();

		User user = userService.getUser(mail);

		if(user!=null) {

			Long id = user.getId();

			List<Friendship> friendships = friendshipService.getFriendshipsForUserAndStatus(id, FriendshipStatus.ACCEPTED);

			for(Friendship currentFriendship: friendships) {
				if(currentFriendship.getSender().getId() ==id)
					resutlList.add(currentFriendship.getReceiver().getOfficialEmail());
				else
					resutlList.add(currentFriendship.getSender().getOfficialEmail());
			}

			return resutlList;

		}
		else {
			resutlList.add("");
			return resutlList;
		}
	}

	public StrategyHolder initStategyHolder() {
		return new StrategyHolder();
	}

	public Map<Integer, String> initAvailablePointsStrategies() {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();

		List<PointsStrategy> allStrategies = pointsStrategyService.getAllStrategies();
		result.put(null, "");

		for (PointsStrategy currentStrategy : allStrategies)
			result.put(currentStrategy.getId(), currentStrategy.getName());
		return result;
	}

	public Event validateSelectedStrategy(StrategyHolder strategyHolder,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (strategyHolder==null || strategyHolder.getId()==null || strategyHolder.getId() <= 0) {
			messageBuilder.source("task");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(strategyHolder);
		}

		return new EventFactorySupport().success(strategyHolder);

	}
}