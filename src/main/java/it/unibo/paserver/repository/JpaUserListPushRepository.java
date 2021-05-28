package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserListPush;

@Repository("userListPushRepository")
public class JpaUserListPushRepository implements UserListPushRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserListPush saveOrUpdate(UserListPush i) {
		
		if (i.getId() != null && i.getId() != 0) {
			i.setUpdateDate(new DateTime());
			return entityManager.merge(i);
		} else {
			i.setId(null);
			i.setCreationDate(new DateTime());
			i.setUpdateDate(new DateTime());
			entityManager.persist(i);
			return i;
		}
	}

	@Override
	public List<UserListPush> findAll() {
		
		// HQL
		String hql = "SELECT i FROM UserListPush i WHERE removed=:removed";
		TypedQuery<UserListPush> query = entityManager.createQuery(hql, UserListPush.class).setParameter("removed", false);
		// return
		List<UserListPush> list = query.getResultList();
		return list;
	}

	@Override
	public UserListPush find(Long id) {
		
		return entityManager.find(UserListPush.class, id);
	}

	@Override
	public UserListPush findUserListPush(Long pushId) {
		
		// HQL
		String hql = "SELECT i FROM UserListPush i WHERE i.pushId.id=:pushId AND removed=:removed";
		// stm
		TypedQuery<UserListPush> query = entityManager.createQuery(hql, UserListPush.class);
		query.setParameter("pushId", pushId);
		query.setParameter("removed", false);
		// limit
		query.setMaxResults(1);
		// return
		List<UserListPush> rs = query.getResultList();
		return rs.size() == 1 ? rs.get(0) : null;
	}

	@Override
	public PushNotifications findUserPush(Long pushId) {
		
		return entityManager.find(PushNotifications.class, pushId);
	}
}