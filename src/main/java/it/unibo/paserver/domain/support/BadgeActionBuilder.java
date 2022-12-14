package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.BadgeActions;

/**
 * Build a BadgeActions object using the builder pattern.
 * 
 * @author danielecampogiani
 * @see BadgeActions
 *
 */
public class BadgeActionBuilder extends EntityBuilder<BadgeActions> {

	@Override
	void initEntity() {
		entity = new BadgeActions();

	}

	@Override
	BadgeActions assembleEntity() {
		return entity;
	}

	/**
	 * Sets the BadgeActions title.
	 * 
	 * @param title the BadgeActions title
	 * @return this builder
	 */
	public BadgeActionBuilder setTitle(String title) {
		entity.setTitle(title);
		return this;
	}

	/**
	 * Sets the BadgeActions description.
	 * 
	 * @param description the BadgeActions description
	 * @return this builder
	 */
	public BadgeActionBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	/**
	 * Sets the BadgeActions ActionType.
	 * 
	 * @param actionType the BadgeActions ActionType
	 * @return this builder
	 * @see ActionType
	 */
	public BadgeActionBuilder setActionType(ActionType actionType) {
		entity.setActionType(actionType);
		return this;
	}

	/**
	 * Sets the BadgeActions quantity.
	 * 
	 * @param quantity the BadgeActions quantity
	 * @return this builder
	 */
	public BadgeActionBuilder setQuantity(Integer quantity) {
		entity.setQuantity(quantity);
		return this;
	}

	/**
	 * Sets the BadgeActions id.
	 * 
	 * @param id the BadgeActions id
	 * @return this builder
	 */
	public BadgeActionBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

}
