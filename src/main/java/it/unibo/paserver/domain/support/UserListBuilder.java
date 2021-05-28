package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.UserList;

public class UserListBuilder extends EntityBuilder<UserList> {
	@Override
	void initEntity() {
		
		entity = new UserList();

	}

	@Override
	UserList assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */

	public UserListBuilder setAll(Long id, Long parentId, boolean removed) {
		this.setId(id);
		this.setParentId(parentId);
		this.setRemoved(removed);
		return this;
	}

	public UserListBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserListBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public UserListBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}