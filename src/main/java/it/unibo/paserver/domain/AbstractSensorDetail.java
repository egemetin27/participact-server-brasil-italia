package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractSensorDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "sensor_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonIgnore
	@OneToOne(mappedBy="sensorDetail", cascade=CascadeType.ALL)
	private Sensor sensor;
	
	@JsonIgnore
	@Transient
	private SensorType sensorType;
	
	private String name;

	public SensorType getSensorType(){
		return sensorType;
	}

	public void setSensorType(SensorType sensorType){
		this.sensorType = sensorType;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String toHtmlString();

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
