package it.unibo.paserver.domain.rest;

import java.util.Set;

/**
 * Utility Class.
 * Used to receive request from REST-friendly device which
 * specify a Set of id, representing his/her friends on
 * a Social Network.
 * 
 * @author danielecampogiani
 * @see SocialPresence
 *
 */
public class SocialPresenceFriendsRequest {

	Set<String> ids;

	/**
	 * Returns friends ids.
	 * 
	 * @return friends ids
	 */
	public Set<String> getIds() {
		return ids;
	}

	/**
	 * Sets friends ids.
	 * @param ids friends ids
	 */
	public void setIds(Set<String> ids) {
		this.ids = ids;
	}

}
