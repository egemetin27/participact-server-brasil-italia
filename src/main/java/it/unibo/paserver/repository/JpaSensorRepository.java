package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.Sensor;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.domain.SensorType;

@Repository("sensorRepository")
public class JpaSensorRepository implements SensorRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(JpaSensorRepository.class);
	
	@Override
	public Sensor findById(long id) {
		return entityManager.find(Sensor.class, id);
	}

	@Override
	public Sensor save(Sensor sensor) {
		logger.trace("Merging sensor {}", sensor.toString());
		return entityManager.merge(sensor);
	}

	@Override
	public boolean deleteSensor(long id) {
		Sensor sensor = findById(id);
		try {
			if (sensor != null) {
				entityManager.remove(sensor);
				return true;
			} else {
				logger.warn("Unable to find sensor {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public SensorTypeInfo findSensorTypeInfoById(long id) {
		return entityManager.find(SensorTypeInfo.class, id);
	}

	@Override
	public SensorTypeInfo saveSensorTypeInfo(SensorTypeInfo sensorDescription) {
		logger.trace("Merging sensorDescription {}", sensorDescription.getSensorType().toString());
		return entityManager.merge(sensorDescription);
	}

	@Override
	public boolean deleteSensorTypeInfo(long id) {
		SensorTypeInfo sensorDescription = findSensorTypeInfoById(id);
		try {
			if (sensorDescription != null) {
				entityManager.remove(sensorDescription);
				return true;
			} else {
				logger.warn("Unable to find sensorDescription {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public SensorTypeInfo fetchBySensorType(SensorType sensType) {
		String hql = "select c from SensorTypeInfo c where c.sensorType=:sensorType";
		TypedQuery<SensorTypeInfo> query = entityManager.createQuery(hql, SensorTypeInfo.class).setParameter("sensorType", sensType);
		List<SensorTypeInfo> sensDescr = query.getResultList();
		return sensDescr.size() == 1 ? sensDescr.get(0) : null;
	}
	
	@Override
	public List<Sensor> fetchSensorBySensorType(SensorType sensType) {
		String hql = "select c from Sensor c where c.sensorType=:sensorType";
		TypedQuery<Sensor> query = entityManager.createQuery(hql, Sensor.class).setParameter("sensorType", sensType);
		List<Sensor> sensDescr = query.getResultList();
		return sensDescr;
	}

}
