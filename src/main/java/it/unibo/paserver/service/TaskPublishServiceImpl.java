package it.unibo.paserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.AudienceSelector;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.ReceiveAdvancedSearch;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserListPush;
import it.unibo.paserver.domain.UserListTask;
import it.unibo.paserver.domain.UserMessage;
import it.unibo.paserver.domain.support.UserMessageBuilder;
import it.unibo.paserver.web.controller.GCMController;
import it.unibo.paserver.web.controller.MailerController;
import it.unibo.paserver.web.controller.ParticipantController;

@SuppressWarnings("ALL")
@Service
public class TaskPublishServiceImpl implements TaskPublishService {
	@Autowired
	private ParticipantController participantController;
	@Autowired
	private GCMController gcmController;
	@Autowired
	private MailerController mailerController;
	@Autowired
	private TaskReportService taskReportService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected UserMessageService userMessageService;
	@Autowired
	protected TaskUserExcludedService taskUserExcludedService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private UserListPushService userListPushService;
	@Autowired
	private UserListTaskService userListTaskService;

	/**
	 * Inicia o processo de publicacao de uma campanha
	 */
	@Override
	@Async
	public void publish(Task task, Boolean isSaveHistory) {
		// Variaveis de controle do fluxo
		boolean isSelectAll = task.isSelectAll();
		String assignFilter = task.getAssignFilter();
		// Params / MultiMap
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		// Garantindo que notificacoes nao sera enviadas para campanhas nao publicadas
		if (task.isPublish() && task.getDeadline().isAfter(new DateTime())) {
			// Coletando usuarios
			if (isSelectAll) {// Todos?
				sendTasks(task, params, isSaveHistory);
			} else {// Filtro
					// Json
				ReceiveJson r = new ReceiveJson("{hashmap:" + assignFilter + "}");
				ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
				params = participantController.advancedQueryParameters(res, params);
				sendTasks(task, params, isSaveHistory);
			}
		} else {
			System.out.println("TaskPublishServiceImpl NO TASK");
		}
	}

	/**
	 * TASK
	 */
	@Override
	@Async
	public void sendParticipantListTask(Task task, Boolean isSaveHistory) {
		try {
			if (task.isPublish() && task.getDeadline().isAfter(new DateTime())) {
				UserListTask userListTask = userListTaskService.findUserListTask(task.getId());
				if (userListTask != null) {
					AudienceSelector audienceSelector = userListTask.getUserListId().getAudienceSelector();
					String assignFilter = "[]";
					ListMultimap<String, Object> params = ArrayListMultimap.create();
					if (audienceSelector != null) {
						if (audienceSelector.equals(AudienceSelector.SELECTOR_ALL)) {
							// Execute
							sendTasks(task, params, isSaveHistory);
						} else if (audienceSelector.equals(AudienceSelector.SELECTOR_RESTRICTED)) {
							// Filtro
							assignFilter = userListTask.getUserListId().getHashmap();
							task.setAssignFilter(assignFilter);
							// Converter
							ReceiveJson r = new ReceiveJson("{hashmap:" + assignFilter + "}");
							ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
							params = participantController.advancedQueryParameters(res, params);
							// Execute
							sendTasks(task, params, isSaveHistory);
						} else if (audienceSelector.equals(AudienceSelector.SELECTOR_CLOSED)) {
							// Filtro
							HashMap<String, String> hashmap = new HashMap<String, String>();
							hashmap.put("key", "FILTER_USERLISTID");
							hashmap.put("value", userListTask.getUserListId().getId().toString());
							hashmap.put("item", userListTask.getUserListId().getId().toString());
							hashmap.put("label", "info");

							Gson gson = new Gson();
							assignFilter = "[" + gson.toJson(hashmap) + "]";

							task.setAssignFilter(assignFilter);
							// Converter
							ReceiveJson r = new ReceiveJson("{hashmap:" + assignFilter + "}");
							ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
							params = participantController.advancedQueryParameters(res, params);
							// Execute
							sendTasks(task, params, isSaveHistory);
						} else {
							throw new Exception("SELECTOR_? " + audienceSelector.toString());
						}
					} else {
						throw new Exception("audienceSelector");
					}
				} else {
					throw new Exception("userListTask");
				}
			} else {
				throw new Exception("isPublish");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("TaskPublishServiceImpl sendParticipantListTask : " + e.getMessage());
		}
	}

	/**
	 * PUSH
	 */
	@Override
	@Async
	public void sendParticipantListPush(PushNotifications push) {
		try {
			if (push.isPublish()) {
				UserListPush userListPush = userListPushService.findUserListPush(push.getId());
				if (userListPush != null) {
					AudienceSelector audienceSelector = userListPush.getUserListId().getAudienceSelector();
					String assignFilter = "[]";
					if (audienceSelector != null) {
						if (audienceSelector.equals(AudienceSelector.SELECTOR_ALL)) {
							// Execute
							sendPushies(push);
						} else if (audienceSelector.equals(AudienceSelector.SELECTOR_RESTRICTED)) {
							// Filtro
							assignFilter = userListPush.getUserListId().getHashmap();
							push.setAssignFilter(assignFilter);
							// Execute
							sendPushies(push);
						} else if (audienceSelector.equals(AudienceSelector.SELECTOR_CLOSED)) {
							// Filtro
							HashMap<String, String> hashmap = new HashMap<String, String>();
							hashmap.put("key", "FILTER_USERLISTID");
							hashmap.put("value", userListPush.getUserListId().getId().toString());
							hashmap.put("item", userListPush.getUserListId().getId().toString());
							hashmap.put("label", "info");

							Gson gson = new Gson();
							assignFilter = "[" + gson.toJson(hashmap) + "]";

							push.setAssignFilter(assignFilter);
							// Execute
							sendPushies(push);
						} else {
							throw new Exception("SELECTOR_? " + audienceSelector.toString());
						}
					} else {
						throw new Exception("audienceSelector");
					}
				} else {
					throw new Exception("userListPush");
				}
			} else {
				throw new Exception("isPublish");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("TaskPublishServiceImpl sendParticipantListPush : " + e.getMessage());
		}
	}

	@Async
	public void sendTasks(Task task, ListMultimap<String, Object> params, Boolean isSaveHistory) {
		// Vars
		List<User> recipients = new ArrayList<User>();
		List<User> users = new ArrayList<User>();
		users = participantController.filter(params, null);
		// Validate
		if (!users.isEmpty() && users.size() > 0) {
			for (User u : users) {
				// System.out.println("sendTasks");
				if (Validator.isValidEmail(u.getOfficialEmail()) && u.getIsActive()) {
					// System.out.println("sendTasks isValidEmail");
					if (!taskUserExcludedService.isExcluded(task.getId(), u.getId())) {
						// System.out.println("sendTasks isExcluded");
						if (taskReportService.getTaskReportsCountByUserAndTask(u.getId(), task.getId()) == 0) {
							// System.out.println("sendTasks getTaskReportsCountByUserAndTask");
							TaskReport e = new TaskReport();
							e.setUser(u);
							e.setTask(task);
							e.setCurrentState(TaskState.AVAILABLE);
							e.setHistory(new ArrayList<TaskHistory>());
							// e.setTaskResult(new TaskResult());
							Set<TaskReport> r = new HashSet<TaskReport>();
							r.add(e);
							task.setTaskReport(r);

							TaskHistory h = new TaskHistory();
							h.setState(TaskState.AVAILABLE);
							h.setTaskReport(e);
							h.setTimestamp(new DateTime());
							e.addHistory(h);
							if (taskService.save(task) != null) {
								recipients.add(u);
							}
						}
					}
				}
			}
		}
		// Notificando
		if (!recipients.isEmpty() && recipients.size() > 0) {
			// System.out.println("sendTasks " + recipients.size());
			mailerController.inviteScheduleMailSending(task, recipients, isSaveHistory, false, null);
		}
	}

	@Override
	@Async
	public void sendPushies(PushNotifications push) {
		// Vars
		String assignFilter = push.getAssignFilter();
		List<User> users = new ArrayList<User>();
		List<User> recipients = new ArrayList<User>();
		// Params / MultiMap
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		// Json
		ReceiveJson r = new ReceiveJson("{hashmap:" + assignFilter + "}");
		ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
		params = participantController.advancedQueryParameters(res, params);
		System.out.println(params.toString());
		users = participantController.filter(params, null);
		// Loop, pegando os usuarios
		if (!users.isEmpty() && users.size() > 0) {
			for (User u : users) {
				if (Validator.isValidEmail(u.getOfficialEmail()) && u.getIsActive()) {
					recipients.add(u);
					if (push.getType().equals(PANotification.Type.MESSAGE)) {
						UserMessage um = new UserMessageBuilder().setAll(u, push, false, null, false).build(true);
						userMessageService.saveOrUpdate(um);
						// System.out.println(u.getOfficialEmail());
					}
				}
			}
		}

		System.out.println(String.format("sendPushies %d", recipients.size()));
		// Notificando
		if (push.isMail()) {
			// Email
			if (!recipients.isEmpty() && recipients.size() > 0) {
				// Fake task
				Task t = new Task();
				t.setId(0L);
				t.setSendEmail(push.isMail());
				t.setEmailSystemId(push.getEmailSystemId());
				t.setEmailSubject(push.getEmailSubject());
				t.setEmailBody(push.getEmailBody());
				// Send
				mailerController.inviteScheduleMailSending(t, recipients, false, true, push);
			}
		} else {
			// Push
			gcmController.notifyByListPushies(push.getType(), recipients, push);
		}
	}

	/**
	 * EMAILS
	 */
	@Override
	@Async
	public void resendScheduleEmail(Task task, Boolean isSaveHistory) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Variaveis de controle do fluxo
		String assignFilter = task.getAssignFilter();
		List<User> users = new ArrayList<User>();
		List<User> recipients = new ArrayList<User>();
		// Params / MultiMap
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		// Chegando Campanha
		if (task.isPublish() && task.getDeadline().isAfter(new DateTime())) {
			ReceiveJson r = new ReceiveJson("{hashmap:" + assignFilter + "}");
			ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
			// Add Task
			params.put("taskId", task.getId());
			// Search
			params = participantController.advancedQueryParameters(res, params);
			users = participantController.filter(params, null);
			// Loop, pegando os usuarios
			if (!users.isEmpty() && users.size() > 0) {
				for (User u : users) {
					if (Validator.isValidEmail(u.getOfficialEmail()) && u.getIsActive()) {
						if (!taskUserExcludedService.isExcluded(task.getId(), u.getId())) {
							if (taskReportService.getTaskReportAccptedOrRejected(task.getId(), u.getId()) == null) {
								recipients.add(u);
							}
						}
					}
				}
			}
			// Notificando
			if (!recipients.isEmpty() && recipients.size() > 0) {
				mailerController.inviteScheduleMailSending(task, recipients, isSaveHistory, false, null);
			}
		} else {
			System.out.println("TaskPublishServiceImpl resendScheduleEmail");
		}
	}

	@Override
	@Async
	public void resendEmail(Task task) {
		List<User> recipients = new ArrayList<User>();
		List<Object[]> items = taskReportService.search(task.getId(), null, null, null);
		if (items.size() > 0) {
			for (Object[] item : items) {
				long userId = ((Long) item[1]).longValue();
				User u = participantService.findById(userId);
				if (u != null) {
					if (Validator.isValidEmail(u.getOfficialEmail()) && u.getIsActive()) {
						recipients.add(u);
					}
				}
			}
		}
		// Reenviando
		if (!recipients.isEmpty() && recipients.size() > 0) {
			mailerController.inviteScheduleMailSending(task, recipients, false, false, null);
			// if (Config.emailEnable) {
			// mailerController.inviteByListUser(task, recipients);
			// }
		}
	}
}