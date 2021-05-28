package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.MailingHistory;

@Repository("mailingHistoryRepository")
public class JpaMailingHistoryRepository implements MailingHistoryRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MailingHistory saveOrUpdate(MailingHistory t) {
		
		if (t.getId() != null && t.getId() != 0) {
			t.setUpdateDate(new DateTime());
			return entityManager.merge(t);
		} else {
			t.setId(null);
			t.setCreationDate(new DateTime());
			t.setUpdateDate(new DateTime());
			entityManager.persist(t);
			return t;
		}
	}

	@Override
	public MailingHistory findByTaskId(long taskId) {
		// HQL
		String hql = "SELECT t FROM MailingHistory t WHERE TaskId=:TaskId";
		TypedQuery<MailingHistory> query = entityManager.createQuery(hql, MailingHistory.class).setParameter("TaskId", taskId);
		// limit
		query.setMaxResults(1);
		// return
		List<MailingHistory> t = query.getResultList();
		return t.size() == 1 ? t.get(0) : null;
	}

	@Override
	public boolean deleteByTaskId(long taskId) {
		
		MailingHistory t = this.findByTaskId(taskId);
		try {
			if (t != null) {
				entityManager.remove(t);
				return true;
			} else {
				System.out.println("Unable to find MailingHistory {} " + taskId + "");
			}
		} catch (Exception e) {
			System.out.println("Exception: {} " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<MailingHistory> findAllByTaskId(long taskId) {
		// HQL
		String hql = "SELECT t FROM MailingHistory t WHERE TaskId=:TaskId";
		TypedQuery<MailingHistory> query = entityManager.createQuery(hql, MailingHistory.class).setParameter("TaskId", taskId);
		// return
		List<MailingHistory> list = query.getResultList();
		return list;
	}

	@Override
	public List<MailingHistory> findAll() {
		
		// HQL
		String hql = "SELECT t FROM MailingHistory t WHERE removed=:removed";
		TypedQuery<MailingHistory> query = entityManager.createQuery(hql, MailingHistory.class).setParameter("removed", false);
		// return
		List<MailingHistory> list = query.getResultList();
		return list;
	}

	@Override
	public MailingHistory find(long id) {
		
		return entityManager.find(MailingHistory.class, id);
	}
}