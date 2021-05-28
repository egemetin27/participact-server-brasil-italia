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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskGeoDrawing implements Serializable {
	private static final long serialVersionUID = 5243938182768495355L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private Double radius;
	@NotNull
	private String type;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "TaskGeoDrawing_Spherical")
	private Set<TaskGeoSpherical> spherical;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "TaskGeoDrawing_Center")
	private Set<TaskGeoSpherical> center;
	private Long taskId;
	@Column(name = "isNotification", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isNotification = false;
	@Column(name = "isActivation", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isActivation = false;
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
	public TaskGeoDrawing(Long id, Double radius, String type, Set<TaskGeoSpherical> spherical,
			Set<TaskGeoSpherical> center, Long taskId, boolean isNotification, boolean isActivation, boolean removed) {
		super();
		this.id = id;
		this.radius = radius;
		this.type = type;
		this.spherical = spherical;
		this.center = center;
		this.taskId = taskId;
		this.isNotification = isNotification;
		this.isActivation = isActivation;
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}
		this.removed = false;
	}
	public TaskGeoDrawing() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Setter/Getter
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<TaskGeoSpherical> getSpherical() {
		return spherical;
	}

	public void setSpherical(Set<TaskGeoSpherical> spherical) {
		this.spherical = spherical;
	}

	public Set<TaskGeoSpherical> getCenter() {
		return center;
	}

	public void setCenter(Set<TaskGeoSpherical> center) {
		this.center = center;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public boolean isNotification() {
		return isNotification;
	}

	public void setNotification(boolean isNotification) {
		this.isNotification = isNotification;
	}

	public boolean isActivation() {
		return isActivation;
	}

	public void setActivation(boolean isActivation) {
		this.isActivation = isActivation;
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
