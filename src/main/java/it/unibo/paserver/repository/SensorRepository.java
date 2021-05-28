package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.Sensor;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.domain.SensorType;

public interface SensorRepository {

	Sensor findById(long id);
	Sensor save(Sensor sensor);
	boolean deleteSensor(long id);
	
	public List<Sensor> fetchSensorBySensorType(SensorType sensType);
	SensorTypeInfo findSensorTypeInfoById(long id);
	SensorTypeInfo saveSensorTypeInfo(SensorTypeInfo sensorDescription);
	boolean deleteSensorTypeInfo(long id);
	SensorTypeInfo fetchBySensorType(SensorType sensType);
	
}
