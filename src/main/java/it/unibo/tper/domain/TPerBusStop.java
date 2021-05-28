package it.unibo.tper.domain;



import java.util.ArrayList;
import java.util.List;

import it.unibo.tper.opendata.domain.response.BusStopResponse;
import it.unibo.tper.opendata.domain.response.TPerBusStopResponse;
import it.unibo.tper.ws.domain.extensions.FermateResponse;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(name="tper_bus_stop")
public class TPerBusStop extends BusStop {
	
	private static final long serialVersionUID = 4344890749260020232L;
	private String site;
	@NotNull
	private Integer coordinateX;
	@NotNull
	private Integer coordinateY;
	@NotNull
	private Integer zoneCode;	
	@ElementCollection(fetch=FetchType.EAGER)
	@NotNull
	private List<String> lines;
	
	
	
	

	public TPerBusStop() {
		
	}
	
	public TPerBusStop(FermateResponse.Table tperResponseBusStop)
	{
				
		this.setCode(tperResponseBusStop.getCodiceFermata());
		this.setName(tperResponseBusStop.getDenominazione());
		this.setMunicipality(tperResponseBusStop.getComune());
		this.setCoordinateX(tperResponseBusStop.getCoordinataX());
		this.setCoordinateY(tperResponseBusStop.getCoordinataY());
		this.setSite(tperResponseBusStop.getUbicazione());
		this.setZoneCode(tperResponseBusStop.getCodiceZona());
		this.setLatitude(tperResponseBusStop.getLatitudine());
		this.setLongitude(tperResponseBusStop.getLongitudine());
		this.setCreationTime(new DateTime());
		this.setLines(new ArrayList<String>());
	}
	

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getCoordinateX() {
		return coordinateX;
	}
	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}
	public Integer getCoordinateY() {
		return coordinateY;
	}
	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}
	public Integer getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(Integer zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Override
	public BusStopResponse converToBusStopResponse() {
		return new TPerBusStopResponse(this);
	}
	
	
	


}
