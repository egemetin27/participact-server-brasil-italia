package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.PushNotifications;

public class PushNotificationsBuilder extends EntityBuilder<PushNotifications> {
	@Override
	void initEntity() {
		
		entity = new PushNotifications();
	}

	@Override
	PushNotifications assembleEntity() {
		
		return entity;
	}

	public PushNotificationsBuilder setAll(long id, Long parentId, PANotification.Type type, String assignFilter, boolean isQueue, String message) {
		this.setId(id);
		this.setParentId(parentId);
		this.setType(type);
		this.setAssignFilter(assignFilter);
		this.setQueue(isQueue);
		this.setMessage(message);
		return this;
	}

	public PushNotificationsBuilder setMessage(String message) {
		entity.setMessage(message);
		return this;
	}

	public PushNotificationsBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public PushNotificationsBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public PushNotificationsBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public PushNotificationsBuilder setType(PANotification.Type type) {
		entity.setType(type);
		return this;
	}

	public PushNotificationsBuilder setAssignFilter(String assignFilter) {
		entity.setAssignFilter(assignFilter);
		return this;
	}

	public PushNotificationsBuilder setQueue(boolean isQueue) {
		entity.setQueue(isQueue);
		return this;
	}

	public PushNotificationsBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public PushNotificationsBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public PushNotificationsBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public PushNotificationsBuilder setPublish(boolean isPublish) {
		entity.setPublish(isPublish);
		return this;
	}

	public PushNotificationsBuilder setMail(boolean isMail) {
		entity.setMail(isMail);
		return this;
	}

	public PushNotificationsBuilder setEmailSubject(String emailSubject) {
		
		entity.setEmailSubject(emailSubject);
		return this;
	}

	public PushNotificationsBuilder setEmailBody(String emailBody) {
		
		entity.setEmailBody(emailBody);
		return this;
	}

	public PushNotificationsBuilder setEmailSystemId(Long emailSystemId) {
		
		entity.setEmailSystemId(emailSystemId);
		return this;
	}
}
