package it.unibo.paserver.service;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.Task;

public interface TaskPublishService {
	void publish(Task task, Boolean isSaveHistory);

	void sendPushies(PushNotifications push);

	void resendEmail(Task task);

	void resendScheduleEmail(Task task, Boolean isSaveHistory);

	void sendParticipantListPush(PushNotifications p);

	void sendParticipantListTask(Task task, Boolean isSaveHistory);
}
