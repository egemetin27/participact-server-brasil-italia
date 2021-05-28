package it.unibo.tper.service;

import it.unibo.tper.domain.BusStop;
import it.unibo.tper.repository.BusStopRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BusStopServiceImpl implements BusStopService{

	@Autowired
	BusStopRepository busStopRepository;

	@Override
	public BusStop findById(Long id) {
		return busStopRepository.findById(id);
	}

	@Override
	public BusStop findByCode(Integer code) {
		return busStopRepository.findByCode(code);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteBusStop(Long id) {
		return busStopRepository.deleteBusStop(id);
	}

	@Override
	@Transactional(readOnly = false)
	public BusStop save(BusStop busStop) {
		return busStopRepository.save(busStop);
	}




	@Override
	public Long getBusStopCount() {
		return busStopRepository.getBusStopCount();
	}

	@Override
	public List<? extends BusStop> getBusStops() {
		return busStopRepository.getBusStops();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBusStopData(List<? extends BusStop> bs , DateTime threshold) {
		for(BusStop b : bs)
		{
			BusStop busStop = busStopRepository.findByCode(b.getCode());
			if(busStop != null)
				b.setId(busStop.getId());
			busStopRepository.save(b);
		}

		List<? extends BusStop> obsolete = busStopRepository.getObsoleteBusStops(threshold);

		for(BusStop b : obsolete)
			busStopRepository.deleteBusStop(b.getId());

	}

	@Override
	public List<? extends BusStop> getObsoleteBusStops(DateTime threshold) {

		return busStopRepository.getObsoleteBusStops(threshold);
	}

	@Override
	public List<? extends BusStop> getBusStopsByRadius(Double latitude,
			Double longitude, Double radius) {
		return busStopRepository.getBusStopsByRadius(latitude, longitude, radius);
	}

}
