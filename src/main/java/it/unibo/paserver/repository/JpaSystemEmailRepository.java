package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.SystemEmail;

@Repository("systemEmailRepository")
public class JpaSystemEmailRepository implements SystemEmailRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SystemEmail> findAll() {
		String hql = "SELECT p FROM SystemEmail p";
		TypedQuery<SystemEmail> query = entityManager.createQuery(hql, SystemEmail.class);
		List<SystemEmail> i = query.getResultList();
		return i;
	}
	
	@Override
	public List<SystemEmail> findAllNotProcessing() {
		String hql = "SELECT p FROM SystemEmail p WHERE removed=:removed AND isProcessing=:isProcessing";
		TypedQuery<SystemEmail> query = entityManager.createQuery(hql, SystemEmail.class);
		//Where
		query.setParameter("removed", false);
		query.setParameter("isProcessing", false);
		List<SystemEmail> i = query.getResultList();
		return i;
	}
	

	@Override
	public boolean delete(long id) {
		SystemEmail p = findById(id);
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
	public boolean cleanProcessed() {
		try {
			int numberDeleted = entityManager
					.createNativeQuery("UPDATE systememail SET isprocessing=0 WHERE id > 0").executeUpdate();
			if (numberDeleted > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@Override
	public SystemEmail saveOrUpdate(SystemEmail p) {
		
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
	public SystemEmail findById(long id) {
		return entityManager.find(SystemEmail.class, id);
	}

	@Override
	public List<Object[]> search(PageRequest pageable) {
		// SQL
		String hql = "SELECT id, fromEmail, updateDate, creationDate, isActive, removed, smtpHost, smtpPort, limitSending, limitPeriod, limitPer, limitTime "
				+ " FROM SystemEmail WHERE removed=:removed ORDER BY isActive DESC";
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
		String hql = "SELECT COUNT(*) AS total FROM SystemEmail WHERE removed=:removed ";
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		// Where
		query.setParameter("removed", false);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}
}