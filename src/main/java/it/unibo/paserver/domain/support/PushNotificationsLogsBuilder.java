package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.PushNotificationsLogs;

public class PushNotificationsLogsBuilder extends EntityBuilder<PushNotificationsLogs> {
	@Override
	void initEntity() {
		entity = new PushNotificationsLogs();
	}

	@Override
	PushNotificationsLogs assembleEntity() {
		return entity;
	}

	public PushNotificationsLogsBuilder setAll(Long id, Long parentId, Long taskId,
			PushNotifications pushNotificationsId, boolean isQueue, Long multicastId, Long success, Long failure,
			Long canonicalIds, String resultsMessageId, String resultsRegistrationId, String resultsError) {

		this.setId(id);
		this.setParentId(parentId);
		this.setTaskId(taskId);
		this.setPushNotificationsId(pushNotificationsId);
		this.setQueue(isQueue);
		this.setMulticastId(multicastId);
		this.setSuccess(success);
		this.setFailure(failure);
		this.setCanonicalIds(canonicalIds);
		this.setResultsMessageId(resultsMessageId);
		this.setResultsRegistrationId(resultsRegistrationId);
		this.setResultsError(resultsError);

		return this;
	}

	/**
	 * Setters
	 */
	public PushNotificationsLogsBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public PushNotificationsLogsBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public PushNotificationsLogsBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public PushNotificationsLogsBuilder setPushNotificationsId(PushNotifications pushNotificationsId) {
		entity.setPushNotificationsId(pushNotificationsId);
		return this;
	}

	public PushNotificationsLogsBuilder setQueue(boolean isQueue) {
		entity.setQueue(isQueue);
		return this;
	}

	public PushNotificationsLogsBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public PushNotificationsLogsBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public PushNotificationsLogsBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public PushNotificationsLogsBuilder setMulticastId(Long multicastId) {
		entity.setMulticastId(multicastId);
		return this;
	}

	public PushNotificationsLogsBuilder setSuccess(Long success) {
		entity.setSuccess(success);
		return this;
	}

	public PushNotificationsLogsBuilder setFailure(Long failure) {
		entity.setFailure(failure);
		return this;
	}

	public PushNotificationsLogsBuilder setCanonicalIds(Long canonicalIds) {
		entity.setCanonicalIds(canonicalIds);
		return this;
	}

	public PushNotificationsLogsBuilder setResultsMessageId(String resultsMessageId) {
		entity.setResultsMessageId(resultsMessageId);
		return this;
	}

	public PushNotificationsLogsBuilder setResultsRegistrationId(String resultsRegistrationId) {
		entity.setResultsRegistrationId(resultsRegistrationId);
		return this;
	}

	public PushNotificationsLogsBuilder setResultsError(String resultsError) {
		entity.setResultsError(resultsError);
		return this;
	}

	public PushNotificationsLogsBuilder setUserId(Long userId) {
		
		entity.setUserId(userId);
		return this;
	}
}
