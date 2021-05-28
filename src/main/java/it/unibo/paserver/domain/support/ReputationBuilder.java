package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;

/**
 * Build a Reputation object using builder pattern
 * 
 * @author danielecampogiani
 * @see Reputation
 *
 */
public class ReputationBuilder extends EntityBuilder<Reputation> {

	@Override
	void initEntity() {
		entity = new Reputation();

	}

	@Override
	Reputation assembleEntity() {
		return entity;
	}

	/**
	 * Sets Reputation User.
	 * 
	 * @param user Reputation User
	 * @return this builder
	 * @see User
	 */
	public ReputationBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * Sets Reputation value.
	 * 
	 * @param value Reputation value
	 * @return this builder
	 */
	public ReputationBuilder setValue(Integer value) {
		entity.setValue(value);
		return this;
	}

	/**
	 * Sets Reputation ActionType.
	 * 
	 * @param actionType Reputation ActionType
	 * @return this builder
	 * @see ActionType
	 */
	public ReputationBuilder setActionType(ActionType actionType) {
		entity.setActionType(actionType);
		return this;
	}

	/**
	 * Sets Reputation id.
	 * 
	 * @param id Reputation id
	 * @return this builder
	 */
	public ReputationBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
