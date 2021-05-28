package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "mailing_history")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MailingHistory implements Serializable {
	private static final long serialVersionUID = -8399317817764718646L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	@NotNull
	@Index(name = "idx_mailing_history_task_id")
	private Long taskId;
	// Usuario que criou
	@Index(name = "idx_mailing_history_parent_id")
	@Column(name = "parentId", nullable = true)
	private Long parentId = null;
	
	@Column(columnDefinition = "TEXT")
	private String emailSubject;
	@Column(columnDefinition = "TEXT")
	private String emailBody;
	private Boolean isSendEmail = false;
	private Long emailSystemId = 0L;
	
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
	public MailingHistory(Long id, Long taskId, Long parentId, String emailSubject, String emailBody, Boolean isSendEmail, Long emailSystemId, boolean removed) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.parentId = parentId;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		this.isSendEmail = isSendEmail;
		this.emailSystemId = emailSystemId;
		this.removed = removed;		
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}		
	}
	
	public MailingHistory() {
		//TODO Auto-generated constructor stub
	}	
	/**
	 * Setter/Getter
	 * @return
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public Boolean getIsSendEmail() {
		return isSendEmail;
	}

	public void setIsSendEmail(Boolean isSendEmail) {
		this.isSendEmail = isSendEmail;
	}

	public Long getEmailSystemId() {
		return emailSystemId;
	}

	public void setEmailSystemId(Long emailSystemId) {
		this.emailSystemId = emailSystemId;
	}	
}
