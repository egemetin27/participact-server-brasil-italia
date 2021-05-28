package it.unibo.paserver.domain;

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
public class TaskGeoSpherical implements Serializable {
	private static final long serialVersionUID = 5211952396443247794L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String guid;
	private String key;
	private String lat;
	private String lng;
	private String type;
	private Double radius;
	@Column(name = "isCenter", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isCenter = false;
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
	 * Construtor
	 */
	public TaskGeoSpherical(String guid, String key, String lat, String lng, String type, Double radius, boolean isCenter) {
		super();
		this.key = key;
		this.lat = lat;
		this.lng = lng;
		this.type = type;
		this.radius = radius;
		this.isCenter = isCenter;
		this.guid = guid;
		this.creationDate = new DateTime();
		this.updateDate = new DateTime();
		this.removed = false;
	}
	
	public TaskGeoSpherical() {
		// TODO Auto-generated constructor stub
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public boolean isCenter() {
		return isCenter;
	}

	public void setCenter(boolean isCenter) {
		this.isCenter = isCenter;
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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
