package it.unibo.tper.opendata.domain.response;

import it.unibo.tper.domain.BusStop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class BusStopResponseList {
	@JsonProperty("BusStops")
	private List<BusStopResponse> busStopsList;

	public List<BusStopResponse> getBusStopsList() {
		return busStopsList;
	}

	public void setBusStopsList(List<BusStopResponse> busStopsList) {
		this.busStopsList = busStopsList;
	}
	
	

}
