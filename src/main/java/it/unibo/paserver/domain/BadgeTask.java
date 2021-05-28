package it.unibo.paserver.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * A BadgeTask is unlocked when the user completes with success
 * the associated Task
 * 
 * @author danielecampogiani
 * @see Task
 *
 */
@Entity
public class BadgeTask extends AbstractBadge {

	private static final long serialVersionUID = 7337607012950805028L;

	@JsonIgnore
	@OneToOne(optional = true, fetch = FetchType.EAGER, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Task task;

	/**
	 * Returns the associated Task object.
	 * 
	 * @return the associated Task object
	 * @see Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the associated Task object.
	 * 
	 * @param task the associated Task object
	 * @see Task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

}
