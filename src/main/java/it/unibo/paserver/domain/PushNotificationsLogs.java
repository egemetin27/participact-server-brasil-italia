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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "pushnotifications_logs")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PushNotificationsLogs implements Serializable {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1757039776501603495L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "parentId", nullable = false)
	private Long parentId = 0L;

	@Column(name = "taskId", nullable = true)
	@Index(name = "idx_pushnotifications_logs_taskid_id")
	private Long taskId = 0L;

	private Long userId;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pushnotifications_id")
	private PushNotifications pushNotificationsId;

	@Column(name = "isQueue", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isQueue = false;

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
	 * FCM Response
	 */
	@Column(name = "multicast_id", nullable = false)
	private Long multicastId = 0L;
	private Long success = 0L;
	private Long failure = 0L;

	@Column(name = "canonical_ids", nullable = false)
	private Long canonicalIds = 0L;

	@Column(name = "results_message_id", nullable = true)
	private String resultsMessageId = null;

	@Column(name = "results_registration_id", nullable = true)
	private String resultsRegistrationId = null;

	@Column(name = "results_error", nullable = true)
	private String resultsError = null;
	/**
	 * Constructor
	 */
	public PushNotificationsLogs(Long id, Long parentId, Long taskId, PushNotifications pushNotificationsId, boolean isQueue, Long multicastId, Long success, Long failure, Long canonicalIds, String resultsMessageId, String resultsRegistrationId, String resultsError) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.taskId = taskId;
		this.pushNotificationsId = pushNotificationsId;
		this.isQueue = isQueue;
		this.multicastId = multicastId;
		this.success = success;
		this.failure = failure;
		this.canonicalIds = canonicalIds;
		this.resultsMessageId = resultsMessageId;
		this.resultsRegistrationId = resultsRegistrationId;
		this.resultsError = resultsError;
		
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}
	}
	
	public PushNotificationsLogs() {
		super();
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public PushNotifications getPushNotificationsId() {
		return pushNotificationsId;
	}

	public void setPushNotificationsId(PushNotifications pushNotificationsId) {
		this.pushNotificationsId = pushNotificationsId;
	}

	public boolean isQueue() {
		return isQueue;
	}

	public void setQueue(boolean isQueue) {
		this.isQueue = isQueue;
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

	public Long getMulticastId() {
		return multicastId;
	}

	public void setMulticastId(Long multicastId) {
		this.multicastId = multicastId;
	}

	public Long getSuccess() {
		return success;
	}

	public void setSuccess(Long success) {
		this.success = success;
	}

	public Long getFailure() {
		return failure;
	}

	public void setFailure(Long failure) {
		this.failure = failure;
	}

	public Long getCanonicalIds() {
		return canonicalIds;
	}

	public void setCanonicalIds(Long canonicalIds) {
		this.canonicalIds = canonicalIds;
	}

	public String getResultsMessageId() {
		return resultsMessageId;
	}

	public void setResultsMessageId(String resultsMessageId) {
		this.resultsMessageId = resultsMessageId;
	}

	public String getResultsRegistrationId() {
		return resultsRegistrationId;
	}

	public void setResultsRegistrationId(String resultsRegistrationId) {
		this.resultsRegistrationId = resultsRegistrationId;
	}

	public String getResultsError() {
		return resultsError;
	}

	public void setResultsError(String resultsError) {
		this.resultsError = resultsError;
	}

	public void setUserId(Long userId) {
		
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
}