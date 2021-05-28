package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.participact.domain.PANotification.Type;
import it.unibo.paserver.domain.PushNotifications;

@Repository("pushNotificationsRepository")
public class JpaPushNotificationsRepository implements PushNotificationsRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PushNotifications findByType(Type t) {
		
		// SQL
		String hql = "SELECT p FROM PushNotifications p WHERE removed=:removed AND type=:typeId";
		// QUERY
		TypedQuery<PushNotifications> query = entityManager.createQuery(hql, PushNotifications.class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("typeId", t);
		query.setMaxResults(1);
		// Execute
		List<PushNotifications> p = query.getResultList();
		return p.size() == 1 ? p.get(0) : null;
	}

	@Override
	public List<PushNotifications> findAll() {
		
		return null;
	}

	@Override
	public PushNotifications saveOrUpdate(PushNotifications p) {
		
		if (p.getId() != 0) {
			p.setUpdateDate(new DateTime());
			return entityManager.merge(p);
		} else {
			p.setCreationDate(new DateTime());
			p.setUpdateDate(new DateTime());
			entityManager.persist(p);
			return p;
		}
	}

	@Override
	public boolean removed(long id) {
		
		try {
			PushNotifications p = findById(id);
			p.setRemoved(true);
			entityManager.merge(p);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public PushNotifications findById(long id) {
		
		return entityManager.find(PushNotifications.class, id);
	}

	@Override
	public List<Object[]> search(PageRequest pageable, boolean isMail) {
		
		// SQL
		String hql = "SELECT id, parentId, type, assignFilter, isQueue, totalSubmitted, totalProcessed, totalFailed, creationDate, updateDate, removed , message, isPublish " 
				+ " FROM PushNotifications " 
				+ " WHERE removed=:removed AND isMail=:isMail "
				+ " ORDER BY creationDate DESC";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// Where
		query.setParameter("isMail", isMail);
		query.setParameter("removed", false);
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public Long searchTotal(boolean isMail) {
		
		// SQL
		String hql = "SELECT COUNT(*) AS total FROM PushNotifications WHERE removed=:removed AND isMail=:isMail ";
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("isMail", isMail);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}
}
