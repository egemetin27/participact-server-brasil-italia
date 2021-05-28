package it.unibo.tper.opendata.domain.response;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BusStopResponse {
	
	@JsonProperty("stop_code")
	private Integer code;	
	@JsonProperty("name")
	private String name;
	@JsonProperty("state")
	private String municipality;
	@JsonProperty("GPSPosition")
	private GPSPosition position;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public GPSPosition getPosition() {
		return position;
	}
	public void setPosition(GPSPosition position) {
		this.position = position;
	}

}
