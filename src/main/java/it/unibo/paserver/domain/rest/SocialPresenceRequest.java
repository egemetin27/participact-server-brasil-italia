package it.unibo.paserver.domain.rest;

/**
 * Utility Class.
 * Used to receive request from REST-friendly device which
 * specify his/her id on a Social Network
 * 
 * @author danielecampogiani
 * @see SocialPresence
 *
 */
public class SocialPresenceRequest {

	private String socialId;

	/**
	 * Returns the User's id on Social Network.
	 * @return User's id on Social Network
	 */
	public String getSocialId() {
		return socialId;
	}

	/**
	 * Sets the User's id on Social Network.
	 * @param socialId User's id on Social Network.
	 */
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

}
