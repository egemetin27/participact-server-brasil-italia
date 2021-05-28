package it.unibo.paserver.repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.UserSecretKey;

@Repository("userSecretKeyRepository")
public class JpaUserSecretKeyRepository implements UserSecretKeyRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserSecretKey saveOrUpdate(UserSecretKey k) {
		UserSecretKey s = find(k.getUserId());
		// New or Update
		if (s != null) {
			s.setUpdateDate(new DateTime());
			s.setUuid(k.getUuid());
			return entityManager.merge(s);
		} else {
			k.setCreationDate(new DateTime());
			k.setUpdateDate(new DateTime());
			entityManager.persist(k);
			return k;
		}
	}

	@Override
	public UserSecretKey find(Long userId) {
		
		return entityManager.find(UserSecretKey.class, userId);
	}

	@Override
	public boolean doCheck(Long userId, UUID uuid) {
		String hql = "SELECT k FROM UserSecretKey k WHERE userId=:userId AND uuid=:UUID";
		TypedQuery<UserSecretKey> query = entityManager.createQuery(hql, UserSecretKey.class);
		query.setParameter("userId", userId);
		query.setParameter("UUID", uuid);
		query.setMaxResults(1);
		List<UserSecretKey> k = query.getResultList();
		return k.size() >= 1;
	}

	@Override
	public boolean updateProgenitorId(Long userId, Long progenitorId) {
		
		try {
			int numberUser = entityManager.createNativeQuery("UPDATE user_accounts SET updatedate=NOW(), progenitorid=:progenitorId WHERE id=:userId AND isguest='1'").setParameter("userId", userId).setParameter("progenitorId", progenitorId)
					.setMaxResults(1).executeUpdate();
			int numberKey = entityManager.createNativeQuery("UPDATE user_secret_key SET updatedate=NOW(), progenitorid=:progenitorId WHERE userid=:userId").setParameter("userId", userId).setParameter("progenitorId", progenitorId)
					.setMaxResults(1).executeUpdate();
			return (numberUser > 0 || numberKey > 0);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return false;
	}
}