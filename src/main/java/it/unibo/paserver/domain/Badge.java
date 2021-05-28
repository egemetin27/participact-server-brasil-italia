package it.unibo.paserver.domain;

import java.util.List;

/**
 * Badge interface with getter and setters for title, description and list of
 * users who currently have unlocked the badge
 * 
 * @author danielecampogiani
 * @see User
 *
 */
public interface Badge {

	/**
	 * Returns a String object representing the title of the badge.
	 * 
	 * @return the title of the badge
	 */
	public String getTitle();

	/**
	 * Sets the title of the badge.
	 * 
	 * @param title the text of the title
	 */
	public void setTitle(String title);

	/**
	 * Returns a String object representing the description of the badge.
	 * 
	 * @return the description of the badge
	 */
	public String getDescription();

	/**
	 * Sets the description of the badge.
	 * 
	 * @param description the text of the description
	 */
	public void setDescription(String description);

	/**
	 * Return a Long object representing the id of the badge.
	 * 
	 * @return the id of the badge
	 */
	public Long getId();

	/**
	 * Sets the id of the badge.
	 * 
	 * @param id the id of the badge
	 */
	public void setId(Long id);

	/**
	 * Return a List of users who currently have unlocked the badge.
	 * 
	 * @return users who currently have unlocked the badge
	 * @see User
	 */
	public List<User> getUsers();

	/**
	 * Set a List of users who currently have unlocked the badge.
	 * 
	 * @param users users who currently have unlocked the badge
	 * @see User
	 */
	public void setUsers(List<User> users);

}
