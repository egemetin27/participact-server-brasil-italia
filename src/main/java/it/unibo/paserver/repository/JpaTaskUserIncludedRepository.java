package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUserIncluded;

@SuppressWarnings("Duplicates")
@Repository("taskUserIncludedRepository")
public class JpaTaskUserIncludedRepository implements TaskUserIncludedRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaskUserIncluded saveOrUpdate(TaskUserIncluded te) {
		
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
	public TaskUserIncluded findById(long id) {
		
		return entityManager.find(TaskUserIncluded.class, id);
	}

	@SuppressWarnings("JpaQueryApiInspection")
	@Override
	public boolean delete(long taskId, long userId) {
		try {
			int numberDeleted = entityManager
					.createNativeQuery("DELETE FROM taskuserIncluded WHERE taskId=:taskId AND userId=:userId")
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
			TaskUserIncluded te = findById(id);
			te.setRemoved(true);
			entityManager.merge(te);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean isIncluded(long taskId, long userId) {
		String hql = "SELECT t.id FROM TaskUserIncluded t WHERE t.taskId.id=:taskId AND t.userId.id=:userId";
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		query.setParameter("userId", userId).setParameter("taskId", taskId);
		query.setMaxResults(1);
		return query.getResultList().size() > 0;
	}

	@Override
	public List<Task> fetchAllByUser(Long userId, PageRequest pageable) {
		// HQL
		String hql = "SELECT t.taskId FROM TaskUserIncluded t "
				+ " WHERE t.userId.id=:userId ";
		// stm
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
		//query.setParameter("removed", false);
		query.setParameter("userId", userId);
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// return
		List<Task> rs = query.getResultList();
		return rs;
	}
}