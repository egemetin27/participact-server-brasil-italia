package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import it.unibo.participact.domain.PANotification;

@Entity
@Table(name = "mailing_logs")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MailingLogs implements Serializable {
	private static final long serialVersionUID = -8399317817764718646L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Index(name = "idx_mailing_logs_task_id")
	private Long taskId;
	@NotNull
	@Index(name = "idx_mailing_logs_user_id")
	private Long userId;
	private String userName;
	private String userEmail;
	private String userDevice;
	@Column(columnDefinition = "TEXT")
	private String userDeviceToken;
	private String userDeviceOS;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "userDevicePushTypeId")
	private PANotification.Type userDevicePushTypeId;	
	
	@Column(name = "pushNotificationId", nullable = true)
	private long pushNotificationId = 0L;

	@NotNull
	@Index(name = "idx_mailing_logs_email_id")
	private Long emailId;
	private String emailTitle;
	@Column(columnDefinition = "TEXT")
	private String emailBody;

	@Column(name = "isQueued", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isQueued = false;

	@Column(name = "isProcessed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isProcessed = false;

	@Column(name = "isAccepted", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isAccepted = false;

	@Column(name = "isRejected", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isRejected = false;

	@Column(name = "isDelivered", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isDelivered = false;

	@Column(name = "isDropped", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isDropped = false;

	@Column(name = "isResend", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isResend = false;

	@Column(name = "isPushed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isPushed = false;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "deliveryDate", updatable = true, nullable = false)
	private DateTime deliveryDate;

	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;
	
	@Column(columnDefinition = "TEXT")
	private String qrCodeToken;
	
	@Column(name = "qrCodeUsed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean qrCodeUsed = false;	

	/**
	 * Constructor
	 */
	public MailingLogs(Long id, Long taskId, Long userId, String userEmail, String userDevice, String userDeviceToken,
			String userDeviceOS, Long emailId, String emailTitle, String emailBody, boolean isQueued,
			boolean isProcessed, boolean isAccepted, boolean isRejected, boolean isDelivered, boolean isDropped,
			boolean isResend, boolean isPushed, DateTime deliveryDate, boolean removed) {
		super();
		this.id = id;
		this.isProcessed = isProcessed;
		this.taskId = taskId;
		this.userId = userId;

		this.userEmail = userEmail;
		this.userDevice = userDevice;
		this.userDeviceToken = userDeviceToken;
		this.userDeviceOS = userDeviceOS;
		this.emailId = emailId;
		this.emailTitle = emailTitle;
		this.emailBody = emailBody;
		this.isQueued = isQueued;

		this.isAccepted = isAccepted;
		this.isRejected = isRejected;
		this.isDelivered = isDelivered;
		this.isDropped = isDropped;
		this.isResend = isResend;
		this.isPushed = isPushed;
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}
		this.deliveryDate = deliveryDate;
		this.removed = removed;
	}

	public MailingLogs() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MailingLogs clone() throws CloneNotSupportedException {
			MailingLogs mq = (MailingLogs) super.clone();        
	        mq.setId(null);
	        return mq;   
	}
	/**
	 * Setter/Getter
	 * 
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(String userDevice) {
		this.userDevice = userDevice;
	}

	public String getUserDeviceToken() {
		return userDeviceToken;
	}

	public void setUserDeviceToken(String userDeviceToken) {
		this.userDeviceToken = userDeviceToken;
	}

	public String getUserDeviceOS() {
		return userDeviceOS;
	}

	public void setUserDeviceOS(String userDeviceOS) {
		this.userDeviceOS = userDeviceOS;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public boolean isQueued() {
		return isQueued;
	}

	public void setQueued(boolean isQueued) {
		this.isQueued = isQueued;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public boolean isDropped() {
		return isDropped;
	}

	public void setDropped(boolean isDropped) {
		this.isDropped = isDropped;
	}

	public boolean isResend() {
		return isResend;
	}

	public void setResend(boolean isResend) {
		this.isResend = isResend;
	}

	public boolean isPushed() {
		return isPushed;
	}

	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
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

	public DateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(DateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public PANotification.Type getUserDevicePushTypeId() {
		return userDevicePushTypeId;
	}

	public void setUserDevicePushTypeId(PANotification.Type userDevicePushTypeId) {
		this.userDevicePushTypeId = userDevicePushTypeId;
	}

	public String getQrCodeToken() {
		return qrCodeToken;
	}

	public void setQrCodeToken(String qrCodeToken) {
		this.qrCodeToken = qrCodeToken;
	}

	public boolean isQrCodeUsed() {
		return qrCodeUsed;
	}

	public void setQrCodeUsed(boolean qrCodeUsed) {
		this.qrCodeUsed = qrCodeUsed;
	}

	public void setPushNotificationId(long id) {
		this.pushNotificationId = id;
	}

	/**
	 * @return the pushNotificationId
	 */
	public long getPushNotificationId() {
		return pushNotificationId;
	}
}
