package it.unibo.paserver.domain;

/**
 * Emails do sistema
 * @author Claudio
 */

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemEmail implements Serializable {
	private static final long serialVersionUID = -662226538442520702L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String fromEmail;
	@NotEmpty
	private String fromName;
	@NotEmpty
	private String smtpHost;
	@Column(name = "smtpPort", nullable = true)
	private Long smtpPort = 465L;
	
	
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private String encryption;

	@Column(name = "limitSending", nullable = true)
	private Long limitSending = 500L;		
	@Column(name = "limitPeriod", nullable = true)
	private Long limitPeriod = 1L;
	@Column(name = "limitPer", nullable = true)
	private Integer limitPer = 0;	
	@Column(name = "limitTime", nullable = true)
	private Long limitTime = 86500L;	

	@Column(name = "limitCounterCurrent", nullable = true)
	private Long limitCounterCurrent = 0L;	

	@Column(name = "limitCounterTotal", nullable = true)
	private Long limitCounterTotal = 0L;		
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "limitDeliveryDate", updatable = true, nullable = true)
	private DateTime limitDeliveryDate;
	
	@Column(name = "isActive", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isActive = false;
	
	@Column(name = "isProcessing", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isProcessing = false;

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
	 * Setter/Getters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Long getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Long smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public Long getLimitSending() {
		return limitSending;
	}

	public void setLimitSending(Long limitSending) {
		this.limitSending = limitSending;
	}

	public Long getLimitPeriod() {
		return limitPeriod;
	}

	public void setLimitPeriod(Long limitPeriod) {
		this.limitPeriod = limitPeriod;
	}

	public Integer getLimitPer() {
		return limitPer;
	}

	public void setLimitPer(Integer limitPer) {
		this.limitPer = limitPer;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public DateTime getLimitDeliveryDate() {
		return limitDeliveryDate;
	}

	public void setLimitDeliveryDate(DateTime limitDeliveryDate) {
		this.limitDeliveryDate = limitDeliveryDate;
	}

	public Long getLimitCounterCurrent() {
		return limitCounterCurrent;
	}

	public void setLimitCounterCurrent(Long limitCounterCurrent) {
		this.limitCounterCurrent = limitCounterCurrent;
	}

	public Long getLimitCounterTotal() {
		return limitCounterTotal;
	}

	public void setLimitCounterTotal(Long limitCounterTotal) {
		this.limitCounterTotal = limitCounterTotal;
	}

	public boolean isProcessing() {
		return isProcessing;
	}

	public void setProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}
}