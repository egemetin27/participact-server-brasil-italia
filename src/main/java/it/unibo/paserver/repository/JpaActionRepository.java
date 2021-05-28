package it.unibo.paserver.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.Action;

@Repository("actionRepository")
public class JpaActionRepository implements ActionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(JpaActionRepository.class);

	@Override
	public Action findById(long id) {
		return entityManager.find(Action.class, id);
	}
	
	@Override
	public Action findByIdAndDetach(Long id) {

		Action clone = this.findById(id);
		entityManager.detach(clone);
		clone.setId(null);
		return clone;
	}

	@Override
	public Action findByName(String name) {
		String hql = "from Action a where a.name = :actionName";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class).setParameter("actionName", name);
		return query.getSingleResult();
	}

	@Override
	public Action save(Action action) {
		if (action.getId() != null) {
			logger.trace("Merging action {}", action.toString());
			return entityManager.merge(action);
		} else {
			logger.trace("Persisting action {}", action.toString());
			entityManager.persist(action);
			return action;
		}
	}

	@Override
	public Long getActionsCount() {
		String hql = "select count(id) from Action";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getActionsCount(long taskId) {
		String hql = "select count(a.id) from Task t join t.actions a where t.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("taskId", taskId);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}

	@Override
	public Long getActionsCountAndNumeric(long taskId, int typeId) {
		@SuppressWarnings("JpaQlInspection") String hql = "SELECT COUNT(a.id) FROM Task t INNER JOIN t.actions a WHERE t.id=:taskId AND a.input_type=:typeId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("taskId", taskId)
				.setParameter("typeId", typeId);
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}

	@Override
	public List<Action> getActions() {
		String hql = "from Action";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class);
		return query.getResultList();
	}

	@Override
	public List<Action> getActions(long taskId) {
		String hql = "select t.actions from Task t where t.id = :taskId";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public Long whatIsMyTaskId(long actionId) {
		String hql = "SELECT task_id FROM task_actions WHERE action_id=:actionId LIMIT 1";
		try {
			Query query = entityManager.createNativeQuery(hql).setParameter("actionId", actionId);
			BigInteger res = (BigInteger) query.getSingleResult();
			return res.longValue();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return 0L;
	}
	
	@Override
	public Long getCountQuestionaire(long taskId) {
		String hql = "SELECT COUNT(q.action_id) AS total"+
		" FROM task_actions a INNER JOIN actionquestionaire q ON q.action_id=a.action_id "+
		" WHERE a.task_id=:taskId ";
		try {
			@SuppressWarnings("JpaQueryApiInspection") Query query = entityManager.createNativeQuery(hql).setParameter("taskId", taskId);
			BigInteger res = (BigInteger) query.getSingleResult();
			return res.longValue();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return 0L;		
	}
	
	@Override
	public List<Action> fetchAll(long taskId) {
		String hql = "SELECT a FROM Task t LEFT JOIN t.actions a WHERE t.id = :taskId";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public boolean deleteAction(long id) {
		Action action = findById(id);
		try {
			if (action != null) {
				@SuppressWarnings("JpaQueryApiInspection") int numberDeleted = entityManager.createNativeQuery("DELETE FROM task_actions WHERE action_id=:id").setParameter("id", id).executeUpdate();
				if (numberDeleted > 0) {
					System.out.println("deleteAction");
					entityManager.remove(action);
					return true;
				}
			} else {
				logger.warn("Unable to find action {}", id);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.info("Exception: {}", e);
		}
		return false;
	}
	
	@Override
	public List<Object[]> fetchClosedAnswer(long questionId){
		@SuppressWarnings("JpaQlInspection") String hql = "SELECT id, answerDescription, answerOrder FROM ClosedAnswer WHERE question_question_id=:questionId";
		//System.out.print("SELECT id, answerDescription, answerOrder FROM ClosedAnswer WHERE question_question_id="+questionId);
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class).setParameter("questionId", questionId);
		return query.getResultList();
	}	
}
