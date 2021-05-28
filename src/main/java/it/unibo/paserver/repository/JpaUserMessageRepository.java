package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserMessage;

@Repository("userMessageRepository")
public class JpaUserMessageRepository implements UserMessageRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserMessage saveOrUpdate(UserMessage m) {
		
		if (m.getId() != null && m.getId() != 0) {
			m.setUpdateDate(new DateTime());
			return entityManager.merge(m);
		} else {
			m.setId(null);
			m.setCreationDate(new DateTime());
			m.setUpdateDate(new DateTime());
			m.setReadingDate(null);
			entityManager.persist(m);
			return m;
		}
	}

	@Override
	public UserMessage findById(long id) {
		
		return entityManager.find(UserMessage.class, id);
	}

	@Override
	public boolean readByUserId(long userId) {
		
		try {
			int numberUpdated = entityManager.createNativeQuery("UPDATE user_messages SET readingDate=NOW(), isRead=:isRead WHERE userid=:userId").setParameter("isRead", 1).setParameter("userId", userId).executeUpdate();
			return (numberUpdated > 0);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return false;
	}

	@Override
	public boolean removed(long id) {
		
		try {
			UserMessage m = findById(id);
			m.setRemoved(true);
			entityManager.merge(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object[]> fetchAllUnread(Long userId) {
		
		// SQL
		String hql = "SELECT um.id, p.message, um.isRead, um.creationDate " 
				+ " FROM UserMessage um INNER JOIN um.messageId p "
				+ " WHERE um.removed=:removed AND um.isRead=:isRead AND um.userId.id=:userId "
				+ " ORDER BY um.creationDate DESC";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("isRead", false);
		query.setParameter("userId", userId);
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}
	
	@Override
	public List<UserMessage> fetchAllUnread(Long userId, boolean isRead) {
		
		// SQL
		String hql = "SELECT um " 
				+ " FROM UserMessage um INNER JOIN um.messageId p "
				+ " WHERE um.removed=:removed AND um.isRead=:isRead AND um.userId.id=:userId "
				+ " ORDER BY um.creationDate DESC";
		// QUERY
		TypedQuery<UserMessage> query = entityManager.createQuery(hql, UserMessage.class);
		// Where
		query.setParameter("removed", false);
		query.setParameter("isRead", isRead);
		query.setParameter("userId", userId);
		// Execute
		List<UserMessage> i = query.getResultList();
		return i;
	}


	@Override
	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pageable) {
		
		return null;
	}

	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		return null;
	}

}
