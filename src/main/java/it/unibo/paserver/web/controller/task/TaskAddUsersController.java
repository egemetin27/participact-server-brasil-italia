package it.unibo.paserver.web.controller.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.GCMController;

@Controller
public class TaskAddUsersController {

	@Autowired
	GCMController gcmController;
	
	@Autowired
	TaskService taskService;

	@Autowired
	UserService userService;

	@Autowired
	TaskUserService taskUserService;
	@Autowired
	TaskReportService taskReportService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskAddUsersController.class);

	public TaskAssignedUsersForm initAssignedUsersForm() {
		return new TaskAssignedUsersForm();
	}

	public Task getTask(Long taskId) {
		try {
			return taskService.findById(taskId);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public  List<String> getAllUsers() {
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

	public Event validateSelectedUsers(long taskId,
			TaskAssignedUsersForm assignedUsers, MessageContext messageContext) {
		List<String> unknown = new ArrayList<String>();
		List<String> inactive = new ArrayList<String>();
		List<String> alreadyAssigned = new ArrayList<String>();

		getAsUsers(taskId, assignedUsers.getUserList(), unknown, inactive,
				alreadyAssigned);
		if (unknown.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("unknown.assignedUsersForm.listUser");
			messageBuilder.args(new String[] { StringUtils.join(unknown, ", ") });
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		if (inactive.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("inactive.assignedUsersForm.listUser");
			messageBuilder.args(new String[] { StringUtils.join(inactive, ", ") });
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}
		if (alreadyAssigned.size() > 0) {
			MessageBuilder messageBuilder = new MessageBuilder().error();
			messageBuilder.source("userList");
			messageBuilder.code("alreadyAssigned.assignedUsersForm.listUser");
			messageBuilder.args(new String[] { StringUtils.join(alreadyAssigned, ", ") });
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(assignedUsers);
		}

		return new EventFactorySupport().success(assignedUsers);
	}
	
	
	

	public Event assignNewUsers(long taskId,
			TaskAssignedUsersForm taskAssignedUsersForm) {
		List<String> userStrings = new ArrayList<String>();
		for (String s : taskAssignedUsersForm.getUserList().split(
				"[^a-zA-Z0-9@.]")) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			userStrings.add(s.trim());
			logger.info("Adding user {} to task {}", s.trim(), taskId);
		}
		try {
			taskService.addUsersToTask(taskId, userStrings);
			gcmController.notifyUsers(PANotification.Type.NEW_TASK, userStrings);
			
		} catch (IllegalArgumentException e) {
			return new EventFactorySupport().error(taskAssignedUsersForm, e);
		}
		return new EventFactorySupport().success(taskAssignedUsersForm);
	}
	
//	@Async
//	private void notifyUsers(Task task, Collection<String> users) {
//		Message.Builder mb = new Message.Builder();
//		mb.collapseKey("NEW_TASK")
//				.delayWhileIdle(true)
//				.addData(PANotification.KEY,
//						PANotification.Type.NEW_TASK.toString())
//				.addData(PANotificationConst.TASK_ID, task.getId().toString())
//				.restrictedPackageName(PANotificationConst.PACKAGE_NAME);
//		Message msg = mb.build();
//		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
//		List<String> gcmids = new ArrayList<String>();
//		for (String user : users) {
//			User u = userService.getUser(user);
//			if (u == null) {
//				logger.error("Unable to notify user {}: user not found", user);
//			} else {
//				String gcmid = u.getGcmId();
//				if (gcmid == null) {
//					logger.error(
//							"Unable to notify user {}: no GCMid available",
//							u.getOfficialEmail());
//				} else {
//					gcmids.add(gcmid);
//					logger.info("Added user {} with gcmid {}", user,
//							StringUtils.abbreviate(gcmid, 25));
//				}
//			}
//		}
//		try {
//			if (gcmids.size() > 0) {
//				sender.send(msg, gcmids, 8);
//			} else {
//				logger.error("No GCMid found");
//			}
//		} catch (IOException e) {
//			logger.error("Error", e);
//		}
//	}

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
	public Collection<String> getAsUsers(long taskId, String userList,
			Collection<String> unknownStrings,
			Collection<String> inactiveUsers, Collection<String> alreadyAssigned) {
		Set<String> users = new LinkedHashSet<String>();
		Set<String> currentUsers = taskReportService
				.getAssignedOfficialEmailByTask(taskId);

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
					if (currentUsers.contains(userString)) {
						if (alreadyAssigned != null) {
							alreadyAssigned.add(userString);
						}
					} else {
						users.add(userString);
					}
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

}