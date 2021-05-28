package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.UserListItem;

public class UserListItemBuilder extends EntityBuilder<UserListItem> {
	@Override
	void initEntity() {
		
		entity = new UserListItem();

	}

	@Override
	UserListItem assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */
	public UserListItemBuilder setAll(Long id, Long userId, Long listId, boolean removed) {
		this.setId(id);
		this.setUserId(userId);
		this.setListId(listId);
		this.setRemoved(removed);
		return this;
	}

	public UserListItemBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserListItemBuilder setUserId(Long userId) {
		entity.setUserId(userId);
		return this;
	}

	public UserListItemBuilder setListId(Long listId) {
		entity.setListId(listId);
		return this;
	}

	public UserListItemBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}