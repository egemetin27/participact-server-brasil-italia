package it.unibo.paserver.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.SensorTypeInfo;

@SuppressWarnings("Duplicates")
@Repository("devicesRepository")
public class JpaDevicesRepository implements DevicesRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public Devices saveOrUpdate(Devices d) {
		
		if (d.getId() != null && d.getId() != 0) {
			d.setUpdateDate(new DateTime());
			return entityManager.merge(d);
		} else {
			d.setCreationDate(new DateTime());
			d.setUpdateDate(new DateTime());
			d.setId(null);
			entityManager.persist(d);
			return d;
		}
	}

	@Override
	public Devices findByBrand(String brand) {
		
		String hql = "SELECT i FROM Devices i WHERE i.brand=:brand";
		TypedQuery<Devices> query = entityManager.createQuery(hql, Devices.class).setParameter("brand", brand);
		List<Devices> i = query.getResultList();
		return i.size() == 1 ? i.get(0) : null;
	}

	@Override
	public Devices findById(long id) {
		
		return entityManager.find(Devices.class, id);
	}

	@Override
	public List<Devices> findAll() {
		
		String hql = "SELECT i FROM Devices i";
		TypedQuery<Devices> query = entityManager.createQuery(hql, Devices.class);
		List<Devices> i = query.getResultList();
		return i;

	}

	@Override
	public Long getCount() {
		
		String hql = "SELECT count(id) from Devices i";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {
		
		try {
			Devices i = findById(id);
			i.setRemoved(true);
			entityManager.merge(i);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object[]> search(String brand, String model, String manufacturer, String tags, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT id, brand, model, manufacturer, tags, removed  FROM Devices WHERE removed=:removed";
		// Where
		boolean q = this.searchsetParameter(hql, brand, model, manufacturer, tags);
		//ORDER BY
		this.hsql = this.hsql + " ORDER BY brand ASC ";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		//limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());		
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public long searchTotal(String brand, String model, String manufacturer, String tags) {
		
		// SQL
		String hql = "SELECT COUNT(*) AS total FROM Devices WHERE removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, brand, model, manufacturer, tags);
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(this.hsql, Long.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}
	/**
	 * Adicionar os paramentros default da buscar
	 * 
	 * @param hql
	 * @param brand
	 * @param model
	 * @param manufacturer
	 * @param tags
	 * @return
	 */
	private boolean searchsetParameter(String hql, String brand, String model, String manufacturer, String tags) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", false);
		// Brand
		if (!Validator.isEmptyString(brand)) {
			hql += " AND UPPER(brand) LIKE :brand";
			params.put("brand", "%" + brand.toString().toUpperCase() + "%");
		}
		// Userbrand
		if (!Validator.isEmptyString(model)) {
			hql += " AND UPPER(model) LIKE :model";
			params.put("model", "%" + model.toString().toUpperCase() + "%");
		}
		// Email
		if (!Validator.isEmptyString(manufacturer)) {
			hql += " AND UPPER(manufacturer) LIKE :manufacturer";
			params.put("manufacturer", "%" + manufacturer.toString().toUpperCase() + "%");
		}
		// Phone
		if (!Validator.isEmptyString(tags)) {
			hql += " AND UPPER(tags) LIKE :tags";
			params.put("tags",  "%" + tags.toString().toUpperCase() + "%");
		}
		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;//Flag
	}

	@Override
	public Devices getDevice(Platform platform, String manufacturer, String modelCode) {
		String hql = "select d from Devices d where d.platform = :platform "
				+ " and d.manufacturer = :manufacturer "
				+ " and d.model = :model";
		TypedQuery<Devices> query = entityManager.createQuery(hql, Devices.class);
		query.setParameter("platform", platform);
		query.setParameter("manufacturer", manufacturer);
		query.setParameter("model", modelCode);
		List<Devices> devices = query.getResultList();
		return devices.size() == 1 ? devices.get(0) : null;
		}
	
	@Override
	public List<Devices> getDevicesWithSensors (ArrayList<SensorTypeInfo> sensType){
		String hql = "select s.device.id from Sensor s where s.sensorTypeInfo.sensorType = :sensType0 ";
		for(int i=0; i<(sensType.size()-1); i++){
			hql = hql +"or s.sensorTypeInfo.sensorType = :sensType"+(i+1)+" ";
		}
		hql = "( "+hql+" group by s.device.id having count(s.device.id) = :count) ";
		hql = "select d from Devices d where d.id in "+hql;
		TypedQuery<Devices> query = entityManager.createQuery(hql, Devices.class);
		for(int i=0; i<(sensType.size()); i++){
			query.setParameter("sensType"+i, sensType.get(i).getSensorType());
		}
		query.setParameter("count", new Long(sensType.size()));
		List<Devices> devices = query.getResultList();
		return devices;
	}
}
