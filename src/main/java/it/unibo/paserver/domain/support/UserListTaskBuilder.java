package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserList;
import it.unibo.paserver.domain.UserListTask;

public class UserListTaskBuilder extends EntityBuilder<UserListTask> {
	@Override
	void initEntity() {
		
		entity = new UserListTask();

	}

	@Override
	UserListTask assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */
	public UserListTaskBuilder setAll(Long id, UserList userListId, Task TaskId, boolean removed) {
		this.setId(id);
		this.setUserListId(userListId);
		this.setTaskId(TaskId);
		this.setRemoved(removed);
		return this;
	}

	public UserListTaskBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserListTaskBuilder setUserListId(UserList userListId) {
		entity.setUserListId(userListId);
		return this;
	}

	public UserListTaskBuilder setTaskId(Task TaskId) {
		entity.setTaskId(TaskId);
		return this;
	}

	public UserListTaskBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}