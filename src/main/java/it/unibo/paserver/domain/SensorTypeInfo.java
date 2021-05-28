package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SensorTypeInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "sensor_type_info_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sensorTypeInfo")
	private Set<Sensor> sensors;
	
	@NotNull
	private SensorType sensorType;
	
	private boolean isLogic;
	
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sensDescr")
	private Set<PipelineDescription> pipelineDescr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<PipelineDescription> getPipelineDescr() {
		return pipelineDescr;
	}

	public void setPipelineDescr(Set<PipelineDescription> pipelineDescr) {
		this.pipelineDescr = pipelineDescr;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public boolean isLogic() {
		return isLogic;
	}

	public void setLogic(boolean isLogic) {
		this.isLogic = isLogic;
	}	
	
	@Override
	public String toString(){
		if(this.description!=null)
			return sensorType.toString()+ ": "+this.description;
		else
			return sensorType.toString();
	}
	
	public Set<Sensor> getSensors(){
		return sensors;
	}
	
	public void setSensors(Set<Sensor> sensors){
		this.sensors=sensors;
	}

}
