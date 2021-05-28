package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.NotificationBar;

@Repository("notificationBarRepository")
public class JpaNotificationBarRepository implements NotificationBarRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<NotificationBar> findAll() {
		
		String hql = "SELECT n FROM NotificationBar n";
		TypedQuery<NotificationBar> query = entityManager.createQuery(hql, NotificationBar.class);
		List<NotificationBar> n = query.getResultList();
		return n;
	}

	@Override
	public NotificationBar saveOrUpdate(NotificationBar n) {
		
		if (n.getId() != 0) {
			n.setUpdateDate(new DateTime());
			return entityManager.merge(n);
		} else {
			n.setCreationDate(new DateTime());
			n.setUpdateDate(new DateTime());
			entityManager.persist(n);
			return n;
		}
	}

	@Override
	public NotificationBar findById(long id) {
		
		return entityManager.find(NotificationBar.class, id);
	}

	@Override
	public boolean unread(long id) {
		
		try {
			NotificationBar n = findById(id);
			n.setRead(true);
			entityManager.merge(n);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean unreadAll(long parentId) {
		
		String hql = "SELECT n FROM NotificationBar n WHERE parentId=:parentId";
		TypedQuery<NotificationBar> query = entityManager.createQuery(hql, NotificationBar.class)
				.setParameter("parentId", parentId);
		List<NotificationBar> rs = query.getResultList();
		if (rs.size() > 0) {
			for (NotificationBar item : rs) {
				unread(item.getId());
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Object[]> search(long parentId, PageRequest pageable) {
		// SQL
		String hql = "SELECT message, isRead, creationDate, alink, resultType, removed FROM NotificationBar n WHERE removed=:removed AND parentId=:parentId ORDER BY creationDate DESC";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("parentId", parentId);
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> n = query.getResultList();
		return n;
	}

	@Override
	public Long searchTotal(long parentId) {
		// SQL
		String hql = "SELECT COUNT(*) AS total FROM NotificationBar n WHERE removed=:removed AND parentId=:parentId ";
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("parentId", parentId);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}
}