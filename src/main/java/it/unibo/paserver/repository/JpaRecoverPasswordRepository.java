package it.unibo.paserver.repository;

import it.unibo.paserver.domain.RecoverPassword;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("recoverPasswordRepository")
public class JpaRecoverPasswordRepository implements RecoverPasswordRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(JpaRecoverPasswordRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RecoverPassword findById(long id) {
		return entityManager.find(RecoverPassword.class, id);
	}

	@Override
	public RecoverPassword findByToken(String token) {
		if (token == null) {
			return null;
		}
		String hql = "select r from RecoverPassword r where r.token = :token";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("token", token);
		return query.getSingleResult();
	}

	@Override
	public List<RecoverPassword> findByUser(long userId) {
		String hql = "select r from RecoverPassword r where r.user.id = :userId";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<RecoverPassword> findExpiredAt(DateTime instant) {
		if (instant == null) {
			return new ArrayList<RecoverPassword>();
		}
		String hql = "select r from RecoverPassword r where r.endValidity < :instant";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("instant", instant);
		return query.getResultList();
	}

	@Override
	public RecoverPassword save(RecoverPassword recoverPassword) {
		if (recoverPassword.getStartValidity().isAfter(
				recoverPassword.getEndValidity())) {
			logger.warn(
					"RecoverPassword start validity time ({}) is after end validity time ({})",
					recoverPassword.getStartValidity(),
					recoverPassword.getEndValidity());
		}
		return entityManager.merge(recoverPassword);
	}

	@Override
	public boolean delete(long id) {
		RecoverPassword rp = findById(id);
		if (rp == null) {
			logger.info("Unable to remove RecoverPassword id {}", id);
			return false;
		}
		logger.debug("Removed RecoverPassword id {}", id);
		entityManager.remove(rp);
		return true;
	}
}
