package it.unibo.paserver.domain.support;

import java.util.UUID;

import it.unibo.paserver.domain.UserSecretKey;

public class UserSecretKeyBuilder extends EntityBuilder<UserSecretKey> {
	@Override
	void initEntity() {
		
		entity = new UserSecretKey();

	}

	@Override
	UserSecretKey assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */
	public UserSecretKeyBuilder setAll(Long userId, UUID uuid, boolean removed) {
		this.setUserId(userId);
		this.setUuid(uuid);
		this.setRemoved(removed);
		return this;
	}

	public UserSecretKeyBuilder setUserId(Long userId) {
		entity.setUserId(userId);
		return this;
	}

	public UserSecretKeyBuilder setUuid(UUID uuid) {
		entity.setUuid(uuid);
		return this;
	}

	public UserSecretKeyBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}