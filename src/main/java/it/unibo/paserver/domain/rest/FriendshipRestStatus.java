package it.unibo.paserver.domain.rest;

/**
 * Utility Class.
 * Used to obtain a REST-friendly representation of a
 * FriendshipStatus
 * 
 * @author danielecampogiani
 * @see FriendshipStatus
 *
 */
public class FriendshipRestStatus {

	private String status;

	/**
	 * Returns the status
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * 
	 * @param status the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
