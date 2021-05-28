package it.unibo.paserver.repository;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.ReputationBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of ReputationRepository using JPA.
 * 
 * @author danielecampogiani
 * @see ReputationRepository
 *
 */
@Repository("reputationRepository")
public class JpaReputationRepository implements ReputationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaAccountRepository.class);

	@Override
	public Reputation save(Reputation reputation) {
		if (reputation.getId() != null) {
			logger.trace("Merging reputation {}", reputation.toString());
			return entityManager.merge(reputation);
		} else {
			logger.trace("Persisting reputation {}", reputation.toString());
			entityManager.persist(reputation);
			return reputation;
		}
	}

	@Override
	public List<Reputation> getReputationsByUser(long userId) {
		String hql = "from Reputation t where t.user.id = :userId";
		TypedQuery<Reputation> query = entityManager.createQuery(hql,
				Reputation.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public Reputation getReputationByUserAndActionType(long userId,
			ActionType actionType) {
		String hql = "from Reputation t where t.user.id = :userId and t.actionType = :actionType";
		TypedQuery<Reputation> query = entityManager
				.createQuery(hql, Reputation.class)
				.setParameter("userId", userId)
				.setParameter("actionType", actionType);
		Reputation result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			User user = entityManager.find(User.class, userId);
			result = create(user, actionType, Reputation.INIT_VALUE);
			logger.info(
					"User {} ({}) has no reputation yet for action {} , creating a new one with default value.",
					user.getId(), user.getOfficialEmail(), actionType);
		}

		return result;
	}

	@Override
	public List<Reputation> getReputations() {
		String hql = "select c from Reputation c";
		TypedQuery<Reputation> query = entityManager.createQuery(hql,
				Reputation.class);
		return query.getResultList();
	}

	@Override
	public Long getReputationsCount() {
		String hql = "select count(id) from Reputation c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Reputation create(User user, ActionType actionType, int value) {
		try {
			EntityBuilderManager.setEntityManager(entityManager);
			user = entityManager.merge(user);
			return new ReputationBuilder().setActionType(actionType).setUser(user).setValue(value).build();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
