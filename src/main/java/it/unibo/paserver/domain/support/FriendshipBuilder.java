package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;

/**
 * Build a Friendship object using the builder pattern.
 * 
 * @author danielecampogiani
 * @see Friendship
 *
 */
public class FriendshipBuilder extends EntityBuilder<Friendship> {

	@Override
	void initEntity() {
		entity = new Friendship();

	}

	@Override
	Friendship assembleEntity() {
		return entity;
	}

	/**
	 * Sets the Friendship sender.
	 * 
	 * @param sender the Friendship sender
	 * @return this builder
	 * @see User
	 */
	public FriendshipBuilder setSender(User sender) {
		entity.setSender(sender);
		return this;
	}

	/**
	 * Sets the Friendship receiver.
	 * 
	 * @param receiver the Friendship receiver
	 * @return this builder
	 * @see User
	 */
	public FriendshipBuilder setReceiver(User receiver) {
		entity.setReceiver(receiver);
		return this;
	}

	/**
	 * Sets the Friendship status.
	 * 
	 * @param status the Friendship status
	 * @return this builder
	 * @see FriendshipStatus
	 */
	public FriendshipBuilder setStatus(FriendshipStatus status) {
		entity.setStatus(status);
		return this;
	}

	/**
	 * Sets the FriendshipStatus id.
	 * 
	 * @param id the FriendshipStatus id
	 * @return this builder
	 */
	public FriendshipBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
