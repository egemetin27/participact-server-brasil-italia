package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.SystemBackup;

@Repository("systemBackupRepository")
public class JpaSystemBackupRepository implements SystemBackupRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SystemBackup> findAll() {
		String hql = "SELECT b FROM SystemBackup p";
		TypedQuery<SystemBackup> query = entityManager.createQuery(hql, SystemBackup.class);
		List<SystemBackup> i = query.getResultList();
		return i;
	}

	@Override
	public boolean delete(long id) {
		SystemBackup b = findById(id);
		try {
			if (b != null) {
				entityManager.remove(b);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public SystemBackup saveOrUpdate(SystemBackup p) {
		
		if (p.getId() != null && p.getId() != 0) {
			p.setUpdateDate(new DateTime());
			return entityManager.merge(p);
		} else {
			p.setId(null);
			p.setCreationDate(new DateTime());
			p.setUpdateDate(new DateTime());
			entityManager.persist(p);
			return p;
		}
	}

	@Override
	public SystemBackup findById(long id) {
		return entityManager.find(SystemBackup.class, id);
	}

	@Override
	public List<Object[]> search(PageRequest pageable) {
		// SQL
		String hql = "SELECT id, name, username, hostname, port, updateDate, creationDate, removed " + " FROM SystemBackup WHERE removed=:removed ORDER BY id DESC";
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
		String hql = "SELECT COUNT(*) AS total FROM SystemBackup WHERE removed=:removed ";
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		// Where
		query.setParameter("removed", false);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}
}