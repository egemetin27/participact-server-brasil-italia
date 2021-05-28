package it.unibo.tper.opendata.domain.response;

import it.unibo.tper.domain.TPerBusStop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TPerBusStopResponse extends BusStopResponse {
	
	@JsonProperty("location")
	private String site;
	@JsonProperty("zone_code")
	private Integer zoneCode;	
	private List<String> lines;

	public TPerBusStopResponse(TPerBusStop tPerBusStop) {
		this.setCode(tPerBusStop.getCode());
		this.setName(tPerBusStop.getName());
		this.setMunicipality(tPerBusStop.getMunicipality());
		this.setPosition(new GPSPosition(tPerBusStop.getLatitude(),tPerBusStop.getLongitude()));
		this.setSite(tPerBusStop.getSite());
		this.setZoneCode(tPerBusStop.getZoneCode());
		this.setLines(tPerBusStop.getLines());
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(Integer zoneCode) {
		this.zoneCode = zoneCode;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}

}
