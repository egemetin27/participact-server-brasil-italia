package it.unibo.paserver.repository;

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.PointsStrategyForTaskBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of PointsStrategyForTaskRepository using JPA.
 * 
 * @author danielecampogiani
 * @see PointsStrategyForTaskRepository
 *
 */
@Repository("pointsStrategyForTaskRepository")
public class JpaPointsStrategyForTaskRepository implements
		PointsStrategyForTaskRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaPointsStrategyForTaskRepository.class);

	@Override
	public PointsStrategyForTask save(
			PointsStrategyForTask pointsStrategyForTask) {
		if (pointsStrategyForTask.getId() != null) {
			logger.trace("Merging pointsStrategyForTask {}",
					pointsStrategyForTask.toString());
			return entityManager.merge(pointsStrategyForTask);
		} else {
			logger.trace("Persisting pointsStrategyForTask {}",
					pointsStrategyForTask.toString());
			entityManager.persist(pointsStrategyForTask);
			return pointsStrategyForTask;
		}
	}

	@Override
	public PointsStrategyForTask create(Task task, Integer strategyId) {
		EntityBuilderManager.setEntityManager(entityManager);
		task = entityManager.merge(task);
		return new PointsStrategyForTaskBuilder().setTask(task)
				.setStrategyId(strategyId).build();
	}

	@Override
	public PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId) {
		String hql = "from PointsStrategyForTask t where t.task.id = :taskId";
		TypedQuery<PointsStrategyForTask> query = entityManager.createQuery(
				hql, PointsStrategyForTask.class)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<PointsStrategyForTask> getPointsStrategyForTasks() {
		String hql = "select c from PointsStrategyForTask c";
		TypedQuery<PointsStrategyForTask> query = entityManager.createQuery(
				hql, PointsStrategyForTask.class);
		return query.getResultList();
	}

	@Override
	public Long getPointsStrategyForTaskCount() {
		String hql = "select count(id) from PointsStrategyForTask c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

}
