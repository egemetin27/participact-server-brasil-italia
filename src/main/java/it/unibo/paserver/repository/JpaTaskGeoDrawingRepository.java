package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.TaskGeoDrawing;

@Repository("taskGeoDrawingRepository")
public class JpaTaskGeoDrawingRepository implements TaskGeoDrawingRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaskGeoDrawing saveOrUpdate(TaskGeoDrawing t) {
		
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
	public boolean deleteByTaskIdType(long TaskId, boolean isNotification) {
		
		TaskGeoDrawing t = findByTaskIdType(TaskId, isNotification);
		try {
			if (t != null) {
				entityManager.remove(t);
				return true;
			} else {
				System.out.println("Unable to find TaskGeoDrawing {} " + TaskId + "");
			}
		} catch (Exception e) {
			System.out.println("Exception: {} " + e.getMessage());
		}
		return false;
	}

	@Override
	public TaskGeoDrawing findByTaskIdType(long TaskId, boolean isNotification) {
		// HQL
		String hql = "SELECT t FROM TaskGeoDrawing t WHERE isNotification=:isNotification AND TaskId=:TaskId";
		TypedQuery<TaskGeoDrawing> query = entityManager.createQuery(hql, TaskGeoDrawing.class)
				.setParameter("isNotification", isNotification).setParameter("TaskId", TaskId);
		// limit
		query.setMaxResults(1);
		// return
		List<TaskGeoDrawing> t = query.getResultList();
		for (int i = 0; i < t.size(); i++) {
			Hibernate.initialize(t.get(i).getCenter());
			Hibernate.initialize(t.get(i).getSpherical());
		}		
		return t.size() == 1 ? t.get(0) : null;
	}

	@Override
	public List<TaskGeoDrawing> findAllByTaskIdType(long taskId, boolean isNotification) {
		
		// HQL
		String hql = "SELECT t FROM TaskGeoDrawing t WHERE isNotification=:isNotification AND TaskId=:TaskId";
		TypedQuery<TaskGeoDrawing> query = entityManager.createQuery(hql, TaskGeoDrawing.class)
				.setParameter("isNotification", isNotification).setParameter("TaskId", taskId);
		// return
		List<TaskGeoDrawing> list = query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			Hibernate.initialize(list.get(i).getCenter());
			Hibernate.initialize(list.get(i).getSpherical());
		}
		return list;
	}
}
