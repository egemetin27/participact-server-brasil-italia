package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "user_secret_key")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserSecretKey implements Serializable {
	private static final long serialVersionUID = -1204187356692872687L;
	@Id
	@NotNull
	@Column(name = "userId")
	private Long userId;

	@NotNull
	@Column(name = "UUID")
	@Index(name = "idx_user_secret_key_uuid")
	private UUID uuid;

	@Column(name = "progenitorId")
	@Index(name = "idx_user_secret_key_progenitor_id")
	private Long progenitorId = 0L;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate = new DateTime();
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;
	/**
	 * Constructor
	 */
	public UserSecretKey(Long userId, UUID uuid) {
		super();
		this.userId = userId;
		this.uuid = uuid;

		this.updateDate = new DateTime();
	}	
	public UserSecretKey() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Setter/Getters
	 */
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
	/**
	 * @return the progenitorId
	 */
	public Long getProgenitorId() {
		return progenitorId;
	}
	/**
	 * @param progenitorId the progenitorId to set
	 */
	public void setProgenitorId(Long progenitorId) {
		this.progenitorId = progenitorId;
	}
}