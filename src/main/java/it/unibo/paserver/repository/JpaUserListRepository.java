package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.UserList;

@Repository("userListRepository")
public class JpaUserListRepository implements UserListRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserList saveOrUpdate(UserList l) {

		if (l.getId() != null && l.getId() != 0) {
			l.setUpdateDate(new DateTime());
			return entityManager.merge(l);
		} else {
			l.setId(null);
			l.setCreationDate(new DateTime());
			l.setUpdateDate(new DateTime());
			entityManager.persist(l);
			return l;
		}
	}

	@Override
	public boolean removed(Long userListId) {

		try {
			UserList l = find(userListId);
			l.setRemoved(true);
			entityManager.merge(l);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<UserList> findAll() {

		// HQL
		String hql = "SELECT t FROM UserList t WHERE removed=:removed";
		TypedQuery<UserList> query = entityManager.createQuery(hql, UserList.class).setParameter("removed", false);
		// return
		List<UserList> list = query.getResultList();
		return list;
	}

	@Override
	public UserList find(Long id) {

		return entityManager.find(UserList.class, id);
	}
}