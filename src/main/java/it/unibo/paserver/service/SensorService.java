package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.Sensor;
import it.unibo.paserver.domain.SensorType;
import it.unibo.paserver.domain.SensorTypeInfo;

public interface SensorService {

	Sensor save(Sensor sensor);
	boolean deleteSensor(long id);
	Sensor getSensor(Long id);
	
	SensorTypeInfo getSensorTypeInfo(long id);
	SensorTypeInfo saveSensorTypeInfo(SensorTypeInfo sensorDescription);
	boolean deleteSensorTypeInfo(long id);
	SensorTypeInfo getSensorTypeInfo(SensorType sensType);
	
	List<Sensor> getSensorsBySensorType(SensorType sensType);
	
}
