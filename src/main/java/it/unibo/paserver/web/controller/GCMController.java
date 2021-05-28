package it.unibo.paserver.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.fcm.FcmHelper;
import com.google.fcm.FcmMessageResponse;
import com.google.fcm.FcmMessageResultItem;
import com.google.gson.JsonObject;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.participact.domain.PANotification;
import it.unibo.participact.domain.PANotificationConst;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.PushNotificationsLogs;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.PushNotificationsLogsBuilder;
import it.unibo.paserver.service.PushNotificationsLogsService;
import it.unibo.paserver.service.PushNotificationsService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.validator.SelectUsersGCMFormValidator;

@Controller
public class GCMController {
	@Autowired
	SelectUsersGCMFormValidator selectUsersGCMFormValidator;
	@Autowired
	private PushNotificationsService pushNotificationsService;
	@Autowired
	private PushNotificationsLogsService pushNotificationsLogsService;
	@Autowired
	UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(GCMController.class);

	@ModelAttribute("selectUsersGCMForm")
	public SelectUsersGCMForm getSelectUsersGCMForm() {
		return new SelectUsersGCMForm();
	}

	@InitBinder("selectUsersGCMForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(selectUsersGCMFormValidator);
	}

	@RequestMapping(value = "/protected/gcm/send", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView getGCMSend(ModelAndView modelAndView) {
		addObjects(modelAndView);
		modelAndView.setViewName("/protected/gcm/selectUsers");
		return modelAndView;
	}

	private void addObjects(ModelAndView modelAndView) {
		modelAndView.addObject("selectUsersGCMForm", new SelectUsersGCMForm());
		Map<String, String> gcmTypes = new LinkedHashMap<String, String>();
		for (PANotification.Type gcmType : PANotification.Type.values()) {
			gcmTypes.put(gcmType.toString(), gcmType.toString());
		}
		modelAndView.addObject("gcmTypes", gcmTypes);
	}

	@RequestMapping(value = "/protected/gcm/send", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView sendRequest(@ModelAttribute @Validated SelectUsersGCMForm selectUsersGCMForm, BindingResult bindingResult, ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			addObjects(modelAndView);
			modelAndView.addObject("selectUsersGCMForm", selectUsersGCMForm);
			modelAndView.setViewName("/protected/gcm/selectUsers");
			return modelAndView;
		}
		String[] userStrings = selectUsersGCMForm.getUserList().split("[^a-zA-Z0-9@.]");
		List<String> userEmails = Arrays.asList(userStrings);
		logger.info("Sending GCM request {} to users {}", selectUsersGCMForm.getGcmType().toString(), StringUtils.join(userEmails, ", "));
		notifyUsers(selectUsersGCMForm.getGcmType(), userEmails);
		modelAndView.setViewName("/protected/gcm/confirmation");
		return modelAndView;
	}

	@Async
	public void notifyUsers(PANotification.Type gcmType, Collection<String> users) {
		Message.Builder mb = new Message.Builder();
		mb.collapseKey("NEW_TASK").delayWhileIdle(true).addData(PANotification.KEY, gcmType.toString()).restrictedPackageName(PANotificationConst.PACKAGE_NAME);
		Message msg = mb.build();
		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
		List<String> gcmids = new ArrayList<String>();
		for (String user : users) {
			if (StringUtils.isBlank(user)) {
				continue;
			}
			User u = userService.getUser(user);
			if (u == null) {
				logger.error("Unable to notify user {}: user not found", user);
			} else {
				String gcmid = u.getGcmId();
				if (gcmid == null) {
					logger.error("Unable to notify user {}: no GCMid available", u.getOfficialEmail());
				} else {
					gcmids.add(gcmid);
					logger.info("Added user {} with gcmid {}", user, StringUtils.abbreviate(gcmid, 25));
				}
			}
		}
		try {
			if (gcmids.size() > 0) {
				sender.send(msg, gcmids, 8);
				logger.error(msg.toString());
			} else {
				logger.error("No GCMid found");
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}
	}

	/**
	 * Notificando um Usuario
	 * 
	 * @param gcmType
	 * @param users
	 */
	@Async
	public void notifyByListUser(PANotification.Type gcmType, List<User> users) {
		Message.Builder mb = new Message.Builder();
		mb.collapseKey("NEW_TASK").delayWhileIdle(true).addData(PANotification.KEY, gcmType.toString()).restrictedPackageName(PANotificationConst.PACKAGE_NAME);
		Message msg = mb.build();

		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
		FcmHelper fcm = FcmHelper.getInstance(PANotificationConst.FCM_SERVER_KEY);
		String[] haystack = { "iPhone", "iPad", "iPod" };

		List<String> gcmids = new ArrayList<String>();
		for (User u : users) {
			String gcmid = u.getGcmId();
			String device = u.getDevice();
			Integer deviceVersion = u.getDeviceVersion();

			// System.out.println("Device " + device + " " + u.getOfficialEmail());

			if (gcmid == null) {
				logger.error("Unable to notify user {}: no GCMid available", u.getOfficialEmail());
			} else {
				if (deviceVersion == 3 || !Validator.isEmptyString(device) && (Validator.isStringContains(haystack, device) || Validator.isStringinSentence(haystack, device))) {
					JsonObject dataObject = new JsonObject();
					dataObject.addProperty("title", "ParticipAct");
					dataObject.addProperty("text", "ParticipAct");
					dataObject.addProperty("badge", 1);
					try {
						fcm.sendNotification(FcmHelper.TYPE_TO, gcmid, dataObject);
					} catch (IOException e) {
						e.printStackTrace(System.out);
					}
					// System.out.println("FCM iOS " + u.getOfficialEmail());
					logger.info("FCM iOS " + u.getOfficialEmail());
				} else {
					gcmids.add(gcmid);
					logger.info("GCM Google " + u.getOfficialEmail());
					// System.out.println("GCM Google " + u.getOfficialEmail());
				}
			}
		}
		try {
			if (gcmids.size() > 0) {
				sender.send(msg, gcmids, 8);
			} else {
				logger.error("No GCMid found");
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}
	}

	@Async
	public void notifyByListPushies(PANotification.Type gcmType, List<User> users, PushNotifications push) {
		// FCM
		FcmHelper fcm = FcmHelper.getInstance(PANotificationConst.FCM_SERVER_KEY);
		long totalProcessed = 0L;
		long totalSubmitted = 0L;
		long totalFailed = 0L;
		String[] haystack = { "iPhone", "iPad", "iPod" };
		String fcmRes = null;
		// List
		if (!users.isEmpty() && users.size() > 0) {
			for (User u : users) {
				String gcmid = u.getGcmId();
				String device = u.getDevice();
				//String xPlataform = u.getxPlataform();
				//String xDeviceModel = u.getxDeviceModel();
				Long userId = u.getId();
				// Push Logs
				Integer deviceVersion = Validator.isEmptyString(u.getDeviceVersion()) ? 0 : u.getDeviceVersion();

				Long multicastId = 0L;
				Long success = 0L;
				Long failure = 0L;
				Long canonicalIds = 0L;
				String resultsMessageId = null;
				String resultsRegistrationId = null;
				String resultsError = null;
				fcmRes = null;
				// Counter
				totalProcessed++;
				System.out.println("Device " + device + " " + u.getOfficialEmail());
				System.out.println(gcmid);
				System.out.println(deviceVersion);

				if (gcmid == null) {
				} else {
					JsonObject dataObject = new JsonObject();
					dataObject.addProperty("title", "ParticipAct");
					dataObject.addProperty("text", push.getMessage());
					dataObject.addProperty("badge", 1);
					// System.out.println(" DATAOBJECT " + dataObject.toString());
					try {
						fcmRes = fcm.sendNotification(FcmHelper.TYPE_TO, gcmid, dataObject);
					} catch (IOException e) {
						e.printStackTrace(System.out);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Contabilizando/Logando Resposta
					totalSubmitted++;
					/** BEGIN LOGS **/
					try {
						if (fcmRes != null) {
							// System.out.println(fcmRes.toString());
							FcmMessageResponse fcmMessageResponse = fcm.getFromJson(fcmRes);
							if (fcmMessageResponse != null) {
								multicastId = fcmMessageResponse.getMulticastId();
								success = Long.valueOf(fcmMessageResponse.getNumberOfSuccess());
								failure = Long.valueOf(fcmMessageResponse.getNumberOfFailure());
								canonicalIds = Long.valueOf(fcmMessageResponse.getNumberOfCanonicalIds());
								FcmMessageResultItem fcmMessageResultItem = fcmMessageResponse.getFirst();
								resultsMessageId = fcmMessageResultItem.getMessageId();
								resultsRegistrationId = fcmMessageResultItem.getCanonicalRegistrationId();
								// System.out.println(fcmMessageResultItem.toString());
								if (failure > 0) {
									resultsError = fcmMessageResultItem.getErrorCode().toString();
									totalFailed++;
								}
							}
						} else {
							totalFailed++;
						}

						// Save Response
						PushNotificationsLogsBuilder pnlm = new PushNotificationsLogsBuilder();
						pnlm.setAll(0L, push.getParentId(), push.getTaskId(), push, (push.getTaskId() > 0), multicastId, success, failure, canonicalIds, resultsMessageId, resultsRegistrationId, resultsError);
						pnlm.setUserId(userId);
						PushNotificationsLogs pnl = pnlm.build(true);
						pushNotificationsLogsService.saveOrUpdate(pnl);
					} catch (IOException e) {
						e.printStackTrace(System.out);
					} catch (Exception e) {
						e.printStackTrace();
					}
					/** END LOGS **/
				}
			}
		}

		// Update stats
		push.setTotalProcessed(totalProcessed);
		push.setTotalSubmitted(totalSubmitted);
		push.setTotalFailed(totalFailed);
		push.setQueue(true);
		pushNotificationsService.saveOrUpdate(push);
	}
}