package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;

/**
 * Build SocialPresence object using builder pattern.
 * 
 * @author danielecampogiani
 * @see SocialPresence
 *
 */
public class SocialPresenceBuilder extends EntityBuilder<SocialPresence> {

	@Override
	void initEntity() {
		entity = new SocialPresence();
	}

	@Override
	SocialPresence assembleEntity() {
		return entity;
	}

	/**
	 * Sets SocialPresence User.
	 * 
	 * @param user SocialPresence User
	 * @return this builder
	 * @see User
	 */
	public SocialPresenceBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * Sets SocialPresence social network.
	 * 
	 * @param socialNetwork SocialPresence social network
	 * @return this builder
	 * @see SocialPresence
	 */
	public SocialPresenceBuilder setSocialNetwork(
			SocialPresenceType socialNetwork) {
		entity.setSocialNetwork(socialNetwork);
		return this;
	}

	/**
	 * Sets SocialPresence social id.
	 * 
	 * @param socialId SocialPresence social id
	 * @return this builder
	 */
	public SocialPresenceBuilder setSocialId(String socialId) {
		entity.setSocialId(socialId);
		return this;
	}

	/**
	 * Sets SocialPresence id
	 * 
	 * @param id SocialPresence id
	 * @return this builder
	 */
	public SocialPresenceBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
