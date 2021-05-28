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
public class SystemBackup implements Serializable {
	
	private static final long serialVersionUID = 4472151026824336840L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String name;	
	@NotEmpty
	private String hostname;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private Long port = 22L;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "limitDeliveryDate", updatable = true, nullable = true)
	private DateTime limitDeliveryDate;

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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the port
	 */
	public Long getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(Long port) {
		this.port = port;
	}
	/**
	 * @return the limitDeliveryDate
	 */
	public DateTime getLimitDeliveryDate() {
		return limitDeliveryDate;
	}
	/**
	 * @param limitDeliveryDate the limitDeliveryDate to set
	 */
	public void setLimitDeliveryDate(DateTime limitDeliveryDate) {
		this.limitDeliveryDate = limitDeliveryDate;
	}
	/**
	 * @return the creationDate
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the updateDate
	 */
	public DateTime getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}
	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}