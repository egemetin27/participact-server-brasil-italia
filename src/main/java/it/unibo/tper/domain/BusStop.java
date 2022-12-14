package it.unibo.tper.domain;

import it.unibo.tper.opendata.domain.response.BusStopResponse;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(name="bus_stop")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class BusStop implements IBusStop,Serializable {

	private static final long serialVersionUID = -7969857884469787908L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bus_stop_id")
	private Long id;

	@NotNull
	@Column(unique = true)
	private Integer code;	
	@NotEmpty
	private String name;	
	@NotEmpty
	private String municipality;
	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;



	@Override
	public DateTime getCreationTime() {
		return creationTime;
	}



	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}



	@Override
	public Long getId() {
		return id;
	}



	@Override
	public void setId(Long id) {
		this.id = id;		
	}



	@Override
	public Integer getCode() {
		return code;
	}



	@Override
	public String getName() {
		return name;
	}



	@Override
	public String getMunicipality() {
		return municipality;
	}


	@Override
	public Double getLatitude() {
		return latitude;
	}

	@Override
	public Double getLongitude() {
		return longitude;
	}

	@Override
	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}


	@Override
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	@Override
	public void setLatitude(Double latitude) {
		this.latitude = latitude;		
	}

	@Override
	public void setLongitude(Double longitude) {
		this.longitude = longitude;		
	}
	
	
	public abstract BusStopResponse converToBusStopResponse();
	



	







}
