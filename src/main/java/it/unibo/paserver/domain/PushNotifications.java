package it.unibo.paserver.domain;

/**
 * Notificacoes enviadas
 * @author Claudio
 */

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import it.unibo.participact.domain.PANotification;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PushNotifications implements Serializable {
	private static final long serialVersionUID = 262935293805301548L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "parentId", nullable = true)
	private Long parentId = null;
	private Long totalSubmitted = 0L;
	private Long totalProcessed = 0L;
	private Long totalFailed = 0L;
	@Column(name = "taskId", nullable = true)
	@Index(name = "idx_pushnotifications_taskid_id")
	private Long taskId = 0L;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "typeId")
	private PANotification.Type type;
	
	@Column(name = "isPublish", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isPublish = true;
	
	@Column(name = "isMail", nullable = false)
	@Type(type = "org.hibernate.type.BooleanType")
	private boolean isMail = false;	
	@Column(columnDefinition = "TEXT")
	private String emailSubject;
	@Column(columnDefinition = "TEXT")
	private String emailBody;
	private Long emailSystemId = 0L;	
	
	@NotEmpty
	@Column(columnDefinition = "TEXT")
	private String assignFilter;
	@NotEmpty
	@Column(columnDefinition = "TEXT")	
	private String message;
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
	 * Constructor
	 */
	public PushNotifications(){}
	/**
	 * Setter/Getters
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public PANotification.Type getType() {
		return type;
	}
	public void setType(PANotification.Type type) {
		this.type = type;
	}
	public String getAssignFilter() {
		return assignFilter;
	}
	public void setAssignFilter(String assignFilter) {
		this.assignFilter = assignFilter;
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

	public Long getTotalSubmitted() {
		return totalSubmitted;
	}

	public void setTotalSubmitted(Long totalSubmitted) {
		this.totalSubmitted = totalSubmitted;
	}

	public Long getTotalProcessed() {
		return totalProcessed;
	}

	public void setTotalProcessed(Long totalProcessed) {
		this.totalProcessed = totalProcessed;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTotalFailed() {
		return totalFailed;
	}
	public void setTotalFailed(Long totalFailed) {
		this.totalFailed = totalFailed;
	}
	public boolean isPublish() {
		return isPublish;
	}
	public void setPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}
	/**
	 * @return the isMail
	 */
	public boolean isMail() {
		return isMail;
	}
	/**
	 * @param isMail the isMail to set
	 */
	public void setMail(boolean isMail) {
		this.isMail = isMail;
	}
	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}
	/**
	 * @param emailSubject the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	/**
	 * @return the emailBody
	 */
	public String getEmailBody() {
		return emailBody;
	}
	/**
	 * @param emailBody the emailBody to set
	 */
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	/**
	 * @return the emailSystemId
	 */
	public Long getEmailSystemId() {
		return emailSystemId;
	}
	/**
	 * @param emailSystemId the emailSystemId to set
	 */
	public void setEmailSystemId(Long emailSystemId) {
		this.emailSystemId = emailSystemId;
	}	
}