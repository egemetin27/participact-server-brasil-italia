package it.unibo.paserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.repository.DevicesRepository;

@Service
@Transactional(readOnly = true)
public class DevicesServiceImpl implements DevicesService {
	@Autowired
	DevicesRepository repos;
	
	@Override
	@Transactional(readOnly = false)
	public Devices saveOrUpdate(Devices i) {
		
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public Devices findByBrand(String brand) {
		
		return repos.findByBrand(brand);
	}

	@Override
	@Transactional(readOnly = true)
	public Devices findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Devices> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount() {
		
		return repos.getCount();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(String brand, String model, String manufacturer, String tags, PageRequest pageable) {
		
		return repos.search(brand, model, manufacturer, tags, pageable);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public long searchTotal(String brand, String model, String manufacturer, String tags) {
		
		return repos.searchTotal(brand, model, manufacturer, tags);
	}

	@Override
	public List<Devices> getDevicesWithSensors(ArrayList<SensorTypeInfo> sensType) {
		return repos.getDevicesWithSensors(sensType);
	}
}