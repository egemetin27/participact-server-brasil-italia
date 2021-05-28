package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sensor implements Serializable {

	private static final long serialVersionUID = 7707672856848864209L;
	
	@Id
	@Column(name = "sensor_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "device_id")
	@JsonIgnore
	private Devices device;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH },
			fetch = FetchType.EAGER)
	@JoinColumn(name = "sensor_type_info_id")
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	private SensorTypeInfo sensorTypeInfo;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "sensor_detail_id")
	private AbstractSensorDetail sensorDetail;

	public Devices getDevice() {
		return device;
	}

	public Long getId() {
		return id;
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AbstractSensorDetail getSensorDetail() {
		return sensorDetail;
	}

	public void setSensorDetail(AbstractSensorDetail sensorDetail) {
		this.sensorDetail = sensorDetail;
	}
	
	@Override
	public String toString(){
		return sensorDetail.toHtmlString();
	}

	public SensorTypeInfo getSensorTypeInfo() {
		return sensorTypeInfo;
	}

	public void setSensorTypeInfo(SensorTypeInfo sensorTypeInfo) {
		this.sensorTypeInfo = sensorTypeInfo;
	}
	
}
