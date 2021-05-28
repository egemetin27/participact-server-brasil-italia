package it.unibo.paserver.repository;

import it.unibo.paserver.domain.MonthlyTargetScore;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository("monthlyTargetScoreRepository")
public class JpaMonthlyTargetScoreRepository implements
		MonthlyTargetScoreRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MonthlyTargetScore save(MonthlyTargetScore monthlyTargetScore) {
		return entityManager.merge(monthlyTargetScore);
	}

	@Override
	public MonthlyTargetScore findById(long id) {
		return entityManager.find(MonthlyTargetScore.class, id);
	}

	@Override
	public MonthlyTargetScore findByYearMonth(int year, int month) {
		String hql = "from MonthlyTargetScore t where t.year = :year and t.month = :month";
		TypedQuery<MonthlyTargetScore> query = entityManager
				.createQuery(hql, MonthlyTargetScore.class)
				.setParameter("year", year).setParameter("month", month);
		return query.getSingleResult();
	}

	@Override
	public boolean delete(long id) {
		try {
			MonthlyTargetScore target = findById(id);
			entityManager.remove(target);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public List<MonthlyTargetScore> getAll() {
		String hql = "from MonthlyTargetScore t order by t.year desc, t.month desc";
		TypedQuery<MonthlyTargetScore> query = entityManager.createQuery(hql,
				MonthlyTargetScore.class);
		return query.getResultList();
	}

}
