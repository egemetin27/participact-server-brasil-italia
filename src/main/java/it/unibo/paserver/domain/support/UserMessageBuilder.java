package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserMessage;

public class UserMessageBuilder extends EntityBuilder<UserMessage> {

	@Override
	void initEntity() {
		entity = new UserMessage();
	}

	@Override
	UserMessage assembleEntity() {
		return entity;
	}

	/**
	 * Valores padrao
	 * 
	 * @param id
	 * @param userId
	 * @param messageId
	 * @param isRead
	 * @param readingDate
	 * @param removed
	 * @return
	 */
	public UserMessageBuilder setAll(User userId, PushNotifications messageId, boolean isRead,
			DateTime readingDate, boolean removed) {
		this.setUserId(userId);
		this.setMessageId(messageId);
		this.setRead(isRead);
		this.setReadingDate(readingDate);
		this.setRemoved(removed);
		return this;
	}

	public UserMessageBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserMessageBuilder setUserId(User userId) {
		entity.setUserId(userId);
		return this;
	}

	public UserMessageBuilder setMessageId(PushNotifications messageId) {
		entity.setMessageId(messageId);
		return this;
	}

	public UserMessageBuilder setRead(boolean isRead) {
		entity.setRead(isRead);
		return this;
	}

	public UserMessageBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public UserMessageBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public UserMessageBuilder setReadingDate(DateTime readingDate) {
		entity.setReadingDate(readingDate);
		return this;
	}

	public UserMessageBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
