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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import it.unibo.paserver.domain.support.Pipeline;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PipelineDescription implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "pipeline_description_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Pipeline.Type type;
	
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "pipeline_sensors", 
	joinColumns = {
			@JoinColumn(name = "pipeline_description_id") },
			inverseJoinColumns = { @JoinColumn(name = "sensor_type_info_id") 
		})
	private Set<SensorTypeInfo> sensDescr;
	
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

	public Set<SensorTypeInfo> getSensDescr() {
		return sensDescr;
	}

	public void setSensDescr(Set<SensorTypeInfo> sensDescr) {
		this.sensDescr = sensDescr;
	}

	public Pipeline.Type getType() {
		return type;
	}

	public void setType(Pipeline.Type type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type.toString();
	}

}
