package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A BadgeActions has an ActionType and a Integer quantity associated. The badge
 * is unlocked when an user completes with success a number of tasks with
 * ActionType in it equals to quantity.
 * 
 * @author danielecampogiani
 * @see ActionType
 *
 */
@Entity
public class BadgeActions extends AbstractBadge {

	private static final long serialVersionUID = 3495058929223295781L;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@JsonIgnore
	private Integer quantity;

	/**
	 * Returns the ActionType associated with the badge.
	 * 
	 * @return the ActionType associated with the badge
	 * @see ActionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Sets the ActionType associated with the badge.
	 * 
	 * @param actionType
	 *            the ActionType associated with the badge
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns an Integer representing the quantity needed to unlock the badge.
	 * 
	 * @return quantity needed to unlock the badge.
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets an Integer representing the quantity needed to unlock the badge.
	 * 
	 * @param quantity
	 *            needed to unlock the badge.
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
