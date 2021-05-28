package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.TaskUserExcluded;

@SuppressWarnings("Duplicates")
@Repository("taskUserExcludedRepository")
public class JpaTaskUserExcludedRepository implements TaskUserExcludedRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaskUserExcluded saveOrUpdate(TaskUserExcluded te) {
		
		if (te.getId() != null && te.getId() != 0) {
			te.setUpdateDate(new DateTime());
			return entityManager.merge(te);
		} else {
			te.setId(null);
			te.setCreationDate(new DateTime());
			te.setUpdateDate(new DateTime());
			entityManager.persist(te);
			return te;
		}
	}

	@Override
	public TaskUserExcluded findById(long id) {
		
		return entityManager.find(TaskUserExcluded.class, id);
	}

	@Override
	public boolean delete(long taskId, long userId) {
		try {
			int numberDeleted = entityManager
					.createNativeQuery("DELETE FROM taskuserexcluded WHERE taskId=:taskId AND userId=:userId")
					.setParameter("taskId", taskId).setParameter("userId", userId).executeUpdate();
			if (numberDeleted > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean removed(long id) {
		
		try {
			TaskUserExcluded te = findById(id);
			te.setRemoved(true);
			entityManager.merge(te);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object[]> fetchAll(long taskId, long parentId) {
		String hql = "SELECT t.id,  t.userId, t.taskId, t.parentId FROM TaskUserExcluded t WHERE t.taskId=:taskId ";
		if (parentId > 0) {
			hql += "AND t.parentId=:parentId";
		}
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		query.setParameter("taskId", taskId);
		if (parentId > 0) {
			query.setParameter("parentId", parentId);
		}
		return query.getResultList();
	}

	@Override
	public boolean isExcluded(long taskId, long userId) {
		String hql = "SELECT t.id FROM TaskUserExcluded t WHERE t.taskId=:taskId AND t.userId=:userId";
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		query.setParameter("userId", userId).setParameter("taskId", taskId);
		query.setMaxResults(1);
		return query.getResultList().size() > 0;
	}
}