package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskUserExcluded implements Serializable {
	private static final long serialVersionUID = 4407504230617393124L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Index(name = "idx_task_user_excluded_task_id")
	private Long taskId;
	@NotNull
	@Index(name = "idx_task_user_excluded_user_id")
	private Long userId;
	@Column(name = "parentId")
	@Index(name = "idx_task_user_excluded_parent_id")
	private Long parentId;
	@Column(name = "isSelectAll", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isSelectAll = false;
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
	public TaskUserExcluded(Long id, Long taskId, Long userId, Long parentId, boolean isSelectAll,
			DateTime creationDate, DateTime updateDate, boolean removed) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.userId = userId;
		this.parentId = parentId;
		this.isSelectAll = isSelectAll;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.removed = removed;
	}

	public TaskUserExcluded() {
	}

	/**
	 * Setter/Getters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean isSelectAll() {
		return isSelectAll;
	}

	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
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
