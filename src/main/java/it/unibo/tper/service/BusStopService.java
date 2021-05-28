package it.unibo.tper.service;

import it.unibo.tper.domain.BusStop;

import java.util.List;

import org.joda.time.DateTime;

public interface BusStopService {
	
	BusStop findById(Long id);
	BusStop findByCode(Integer code);
	boolean deleteBusStop(Long id);
	BusStop save(BusStop busStop);
	Long getBusStopCount();
	List<? extends BusStop> getBusStops();
	List<? extends BusStop> getObsoleteBusStops(DateTime threshold);
	void updateBusStopData(List<? extends BusStop> bs, DateTime threshold);
	List< ? extends BusStop> getBusStopsByRadius(Double latitude, Double longitude, Double radius);

	
}
