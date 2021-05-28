package it.unibo.tper.repository;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import it.unibo.tper.domain.BusStop;

public interface BusStopRepository {
	
	BusStop findById(Long id);
	BusStop findByCode(Integer code);
	boolean deleteBusStop(Long id);
	BusStop save(BusStop busStop);
	Long getBusStopCount();
	List<? extends BusStop> getBusStops();
	List<? extends BusStop> getObsoleteBusStops(DateTime threshold);
	List< ? extends BusStop> getBusStopsByRadius(Double latitude, Double longitude, Double radius);

}
