package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "user_list_task")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserListTask implements Serializable {
	private static final long serialVersionUID = 6886840672096942101L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "userListId")
	private UserList userListId;
	
	@OneToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "taskId")
	private Task taskId;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;
	/**
	 * Constructor
	 */
	public UserListTask(Long id, UserList userListId, Task taskId, DateTime creationDate, DateTime updateDate, boolean removed) {
		super();
		this.id = id;
		this.userListId = userListId;
		this.taskId = taskId;
		this.removed = removed;
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}
	}
	
	public UserListTask() {}
	/**
	 * Setters/Getters
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserList getUserListId() {
		return userListId;
	}
	public void setUserListId(UserList userListId) {
		this.userListId = userListId;
	}
	public Task getTaskId() {
		return taskId;
	}
	public void setTaskId(Task taskId) {
		this.taskId = taskId;
	}
	public DateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}
	public DateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}
	public boolean isRemoved() {
		return removed;
	}
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}	
}
