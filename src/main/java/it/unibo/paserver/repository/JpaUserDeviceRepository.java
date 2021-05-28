package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;


@Repository("userDeviceRepository")
public class JpaUserDeviceRepository implements UserDeviceRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaUserDeviceRepository.class);
	
	@Override
	public UserDevice findById(long id) {
		return entityManager.find(UserDevice.class, id);
	}

	@Override
	public UserDevice save(UserDevice userDevice) {
		// if (userDevice.getId() != null) {
		logger.trace("Merging userDevice {}", userDevice.toString());
		return entityManager.merge(userDevice);
		// } else {
		// logger.trace("Persisting userDevice {}", userDevice.toString());
		// entityManager.persist(userDevice);
		// return userDevice;
		// }
	}

	@Override
	public boolean deleteUserDevice(long id) {
		UserDevice userDevice = findById(id);
		try {
			if (userDevice != null) {
//				logger.info("userDevice Imei: {}", userDevice.getImei());
//				userDevice.getUser().getUserDevices().remove(userDevice);
				entityManager.remove(userDevice);
				entityManager.flush();
				return true;
			} else {
				logger.warn("Unable to find userDevice {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public List<UserDevice> getUserDevices() {
		String hql = "select d from UserDevice d";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class);
		List<UserDevice> userDevices = query.getResultList();
		return userDevices;
	}

	@Override
	public boolean existImei(String imei, long id) {
		String hql = "select count(d) from UserDevice d " +
			     "where d.imei = :imei and d.id <> :id";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		query.setParameter("imei", imei);
		query.setParameter("id", id);
		boolean existImei;
		if (query.getSingleResult() == 0){
			existImei = false;
		}
		else {
			existImei = true;
		}
		return existImei;
	}
	
	@Override
	public boolean existImeiUser(User user, String imei) {
		String hql = "select count(d) from UserDevice d " +
			     "where d.imei = :imei and d.user = :user";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		query.setParameter("imei", imei);
		query.setParameter("user", user);
		boolean existImei;
		if (query.getSingleResult() == 0){
			existImei = false;
		}
		else {
			existImei = true;
		}
		return existImei;
	}

	@Override
	public boolean existPriority(long userId, long priority, long id) {
		String hql = "select count(d) from UserDevice d " +
		 "where d.user.id = :userId and d.priority = :priority and d.id <> :id";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		query.setParameter("userId", userId);
		query.setParameter("priority", priority);
		query.setParameter("id", id);
		boolean existPriority; 
		if (query.getSingleResult() == 0){
			existPriority = false;
		}
		else {
			existPriority = true;
		}
		return existPriority;
	}

	@Override
	public List<UserDevice> getActivePrimaryUserDevices() {
		String hql = "select d from UserDevice d where d.user.isActive = true and d.priority = 99 ";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class);
		List<UserDevice> userDevices = query.getResultList();
		return userDevices;
	}

	@Override
	public List<UserDevice> selectUserDevices(Platform platform, Long deviceId, Boolean isPrimaryUD, String user) {
		String hql = "select d from UserDevice d where d.user.isActive = true ";
		if (platform != null) {
			hql = hql + " and d.device.platform = :platform ";
		};
		if (deviceId != null) {
			hql = hql + " and d.device.id = :deviceid ";
		};
		if (isPrimaryUD) {
			hql = hql + " and d.priority = 99 ";
		};
		if (!user.isEmpty()) {
			hql = hql + " and ( "
					  + "       (upper(d.user.name || ' ' || d.user.surname) like '%' || upper(:user) || '%' )"
					  + "       or "
					  + "       (upper(d.user.officialEmail) like '%' || upper(:user) || '%' ) "
					  + "     ) ";
		}
		// ordinamento 
		hql = hql + " order by d.user.name asc, d.user.surname asc, d.priority desc ";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class);
		if (platform != null) {
		    query.setParameter("platform", platform);
		};
		if (deviceId != null) {
		    query.setParameter("deviceid", deviceId);
			};
		if (!user.isEmpty()) {
				query.setParameter("user", user);
			}	
		List<UserDevice> userDevices = query.getResultList();
		return userDevices;
	}

	@Override
	public List<UserDevice> selectNewUserDevices(Platform platform,
			Long deviceId, Boolean isPrimaryUD, String user, Task task) {
		String hql = "select d from UserDevice d where d.user.isActive = true ";
		if (platform != null) {
			hql = hql + " and d.device.platform = :platform ";
		};
		if (deviceId != null) {
			hql = hql + " and d.device.id = :deviceid ";
		};
		if (isPrimaryUD) {
			hql = hql + " and d.priority = 99 ";
		};		
		if (!user.isEmpty()) {
			hql = hql + " and ( "
					  + "       (upper(d.user.name || ' ' || d.user.surname) like '%' || upper(:user) || '%' )"
					  + "       or "
					  + "       (upper(d.user.officialEmail) like '%' || upper(:user) || '%' ) "
					  + "     ) ";
		}
		
		// non vanno estratti userDevices presenti nel Task
		hql = hql + " and not exists "
				+ "( select 'x' from TaskReport t "
				+ "   where t.task = :task "
				+ "     and d.id = t.userDevice.id "
				+ ")";
		
		// se il task è di tipo "One Device for User" non vanno estratti altri Userdevice
		// dello steso User se ne è prsente almento Uno
		if (task.getOnlyOneDeviceCanAccept()) {
			hql = hql + " and not exists "
					+ "( select 'x' from TaskReport t1 "
					+ "   where t1.task = :task "
					+ "     and d.user.id = t1.user.id "
					+ ")";
		}
		
		// ordinamento 
		hql = hql + " order by d.user.name asc, d.user.surname asc, d.priority desc ";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class);
		if (platform != null) {
		    query.setParameter("platform", platform);
		};
		if (deviceId != null) {
		    query.setParameter("deviceid", deviceId);
			};
		if (!user.isEmpty()) {
			query.setParameter("user", user);
		}
		query.setParameter("task", task);
		
		List<UserDevice> userDevices = query.getResultList();
		return userDevices;
	}

	@Override
	public UserDevice findByImei(String imei) {
		String hql = "select d from UserDevice d where d.imei=:imei";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class)
				.setParameter("imei", imei);
		List<UserDevice> userDevices = query.getResultList();
		return userDevices.size() == 1 ? userDevices.get(0) : null;
	}

	@Override
	public Object getUserDevicesByUser(User user) {
		String hql = "select d from UserDevice d where d.user = :user"
				+ " order by d.priority desc";
		TypedQuery<UserDevice> query = entityManager.createQuery(hql, UserDevice.class);
		query.setParameter("user", user);
		List<UserDevice> userDevices = query.getResultList();
		return userDevices;
	}

}
