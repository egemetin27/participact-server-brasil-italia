package it.unibo.paserver.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.IssueSetting;

@Repository("issueSettingRepository")
public class JpaIssueSettingRepository implements IssueSettingRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public IssueSetting saveOrUpdate(IssueSetting is) {
		
		if (is.getId() != null && is.getId() != 0) {
			is.setUpdateDate(new DateTime());
			return entityManager.merge(is);
		} else {
			is.setId(1L);
			is.setCreationDate(new DateTime());
			is.setUpdateDate(new DateTime());
			is.setEditDate(new DateTime());
			entityManager.persist(is);
			return is;
		}
	}

	@Override
	public IssueSetting findById(long id) {
		
		IssueSetting is = entityManager.find(IssueSetting.class, id);
		return is;
	}

	@Override
	public boolean inAppleReview(long l) {
		
		IssueSetting is = this.findById(l); 
		return is != null ? is.isInAppleReview() : false;
	}
}