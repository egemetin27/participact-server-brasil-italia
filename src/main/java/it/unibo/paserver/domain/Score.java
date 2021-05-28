package it.unibo.paserver.domain;

/**
 * Utility Class.
 * Used to aggregate different Points of User.
 * 
 * @author danielecampogiani
 * @see Points
 * @see User
 *
 */
public class Score implements Comparable<Score> {

	private User user;
	private int value;

	/**
	 * Returns the user to whom Points are associated.
	 * 
	 * @return the user to whom Points are associated
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user to whom Points are associated.
	 * 
	 * @param user the user to whom Points are associated
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the Integer value.
	 * 
	 * @return the Integer value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the Integer value.
	 * 
	 * @param value the Integer value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Score o) {

		if (o == null)
			return 1;
		Score score = (Score) o;
		if (equals(o))
			return 0;
		if (value > o.value)
			return 1;
		else if (value < o.value)
			return -1;
		else {
			return user.compareTo(score.user);
		}
	}
}
