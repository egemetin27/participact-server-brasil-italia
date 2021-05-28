package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;

@Repository("systemPageRepository")
public class JpaSystemPageRepository implements SystemPageRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SystemPage findByType(SystemPageType t) {
		
		// SQL
		String hql = "SELECT p FROM SystemPage p WHERE removed=:removed AND type=:typeId";
		// QUERY
		TypedQuery<SystemPage> query = entityManager.createQuery(hql, SystemPage.class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("typeId", t);
		query.setMaxResults(1);
		// Execute
		List<SystemPage> p = query.getResultList();
		return p.size() == 1 ? p.get(0) : null;
	}

	@Override
	public List<SystemPage> findAll() {
		
		return null;
	}

	@Override
	public boolean delete(long id) {
		SystemPage p = findById(id);
		try {
			if (p != null) {
				entityManager.remove(p);
				return true;
			} 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@Override
	public SystemPage saveOrUpdate(SystemPage p) {
		
		p.setUpdateDate(new DateTime());
		return entityManager.merge(p);
	}

	@Override
	public SystemPage findById(long id) {
		return entityManager.find(SystemPage.class, id);
	}

	@Override
	public List<Object[]> search(PageRequest pageable) {
		// SQL
		String hql = "SELECT id, title, updateDate, creationDate, isActive, removed, type, type FROM SystemPage WHERE removed=:removed ORDER BY isActive DESC";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// Where
		query.setParameter("removed", false);
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public Long searchTotal() {
		
		// SQL
		String hql = "SELECT COUNT(*) AS total FROM SystemPage WHERE removed=:removed ";
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		// Where
		query.setParameter("removed", false);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}

	@Override
	public List<Object[]> fetchAllActivePages() {
		
		// SQL
		String hql = "SELECT id, title, updateDate, creationDate, isActive, removed, type "
				+ "FROM SystemPage "
				+ "WHERE removed=:removed AND isActive=:isActive AND (type=1 OR type=2) "
				+ "ORDER BY type ASC";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("isActive", true);
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}
}