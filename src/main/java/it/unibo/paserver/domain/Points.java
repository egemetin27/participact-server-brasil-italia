package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Whenever a user should be rewarded for a specific task we use points as reward.
 * Each Points object has a reference to the User which should be rewarded, a reference
 * to the Task which is completed, an Integer value and a time stamp.
 * 
 * @author danielecampogiani
 * @see User
 * @see Task
 *
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Points implements Serializable {

	private static final long serialVersionUID = -6717545661213514031L;
	
	public enum PointsType {
		TASK_COMPLETED_WITH_SUCCESS,USER_TASK_APPROVED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private User user;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Task task;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;

	@NotNull
	private Integer value;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PointsType type;

	/**
	 * Returns the User to be rewarded .
	 * 
	 * @return the User to be rewarded 
	 * @see User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the User to be rewarded.
	 * 
	 * @param user the User to be rewarded 
	 * @see User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the completed Task.
	 * 
	 * @return the completed Task
	 * @see Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the completed Task
	 * 
	 * @param task the completed Task
	 * @see Task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Returns the time stamp.
	 * 
	 * @return the time stamp
	 * @see DateTime
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * Sets the time stamp.
	 * 
	 * @param date the time stamp
	 * @see DateTime
	 */
	public void setDate(DateTime date) {
		this.date = date;
	}

	/**
	 * Returns the Integer value.
	 * 
	 * @return the Integer value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the Integer value.
	 * 
	 * @param value the Integer value
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns Points type.
	 * 
	 * @return Points type
	 * @see PointsType
	 */
	public PointsType getType() {
		return type;
	}

	/**
	 * Sets Points type.
	 * 
	 * @param type Points type
	 * @see PointsType
	 */
	public void setType(PointsType type) {
		this.type = type;
	}

}
