package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserList;
import it.unibo.paserver.domain.UserListPush;

public class UserListPushBuilder extends EntityBuilder<UserListPush> {
	@Override
	void initEntity() {
		
		entity = new UserListPush();

	}

	@Override
	UserListPush assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */
	public UserListPushBuilder setAll(Long id, UserList userListId, PushNotifications pushId, boolean removed) {
		this.setId(id);
		this.setUserListId(userListId);
		this.setPushId(pushId);
		this.setRemoved(removed);
		return this;
	}

	public UserListPushBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserListPushBuilder setUserListId(UserList userListId) {
		entity.setUserListId(userListId);
		return this;
	}

	public UserListPushBuilder setPushId(PushNotifications pushId) {
		entity.setPushId(pushId);
		return this;
	}

	public UserListPushBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}