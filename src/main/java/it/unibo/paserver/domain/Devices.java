package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Devices implements Serializable {

	private static final long serialVersionUID = -742591782892100372L;
	@Id
	@Column(name = "device_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * The consumer-visible brand with which the product/hardware will be associated, if any.
	 */
	@NotEmpty
	private String brand = "Placeholder";
	/**
	 * The end-user-visible name for the end product.
	 */
	@NotEmpty
	private String model = "Placeholder";
	/**
	 * The manufacturer of the product/hardware.
	 */
	private String manufacturer;
	
	//Could be useful in future!
	private Platform platform;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Sensor> sensors;
	
	/**
	 * A build ID string meant for displaying to the user
	 */
	private String display;
	/**
	 * A string that uniquely identifies this build.
	 */
	private String fingerprint;
	/**
	 * The name of the hardware (from the kernel command line or /proc).
	 */
	private String hardware;
	/**
	 * Comma-separated tags describing the build, like "unsigned,debug".
	 */
	private String tags;
	/**
	 * The type of build, like "user" or "eng".
	 */
	private String type;
	/**
	 * Either a changelist number, or a label like "M4-rc20".
	 */
	private String changelist;
	@Column(name = "isActive", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isActive = false;
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
	 */
	public Long getId() {
		return id;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getHardware() {
		return hardware;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getChangelist() {
		return changelist;
	}
	public void setChangelist(String changelist) {
		this.changelist = changelist;
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
	
	public Set<Sensor> getSensors() {
		return sensors;
	}
	
	public void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;
	}
	
}