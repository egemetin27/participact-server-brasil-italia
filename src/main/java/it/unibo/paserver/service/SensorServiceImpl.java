package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.Sensor;
import it.unibo.paserver.domain.SensorType;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.repository.SensorRepository;

@Service
@Transactional(readOnly = true)
public class SensorServiceImpl implements SensorService {
	
	@Autowired
	SensorRepository sensorRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Sensor save(Sensor sensor) {
		return sensorRepository.save(sensor);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteSensor(long id) {
		return sensorRepository.deleteSensor(id);
	}

	@Override
	public Sensor getSensor(Long id) {
		return sensorRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public SensorTypeInfo saveSensorTypeInfo(SensorTypeInfo sensorDescription) {
		return sensorRepository.saveSensorTypeInfo(sensorDescription);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteSensorTypeInfo(long id) {
		return sensorRepository.deleteSensorTypeInfo(id);
	}

	@Override
	public SensorTypeInfo getSensorTypeInfo(long id) {
		return sensorRepository.findSensorTypeInfoById(id);
	}

	@Override
	public SensorTypeInfo getSensorTypeInfo(SensorType sensType) {
		return sensorRepository.fetchBySensorType(sensType);
	}

	@Override
	public List<Sensor> getSensorsBySensorType(SensorType sensType) {
		return sensorRepository.fetchSensorBySensorType(sensType);
	}

}
