package it.unibo.paserver.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserListItem;

@SuppressWarnings("Duplicates")
@Repository("userListItemRepository")
public class JpaUserListItemRepository implements UserListItemRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public UserListItem saveOrUpdate(UserListItem i) {
		
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

	@SuppressWarnings("JpaQueryApiInspection")
	@Override
	public boolean removed(Long userListId, Long userId) {
		
		try {
			Query query = entityManager.createNativeQuery("UPDATE user_list_item SET updatedate=NOW(), removed=1 WHERE userid=:userId AND listid=:userListId");
			query.setParameter("userListId", userListId).setParameter("userId", userId);
			query.setMaxResults(1);
			int numberUpdated = query.executeUpdate();
			return (numberUpdated > 0);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return false;

	}

	@Override
	public List<UserListItem> findAll() {
		
		// HQL
		String hql = "SELECT t FROM UserListItem t WHERE removed=:removed";
		TypedQuery<UserListItem> query = entityManager.createQuery(hql, UserListItem.class).setParameter("removed", false);
		// return
		List<UserListItem> list = query.getResultList();
		return list;
	}

	@Override
	public UserListItem find(Long id) {
		
		return entityManager.find(UserListItem.class, id);
	}

	@Override
	public UserListItem findByListId(Long userListId, Long userId) {
		// HQL
		String hql = "SELECT i FROM UserListItem i WHERE i.listId=:listId AND i.userId=:userId";
		// stm
		TypedQuery<UserListItem> query = entityManager.createQuery(hql, UserListItem.class);
		query.setParameter("listId", userListId);
		query.setParameter("userId", userId);
		// limit
		query.setMaxResults(1);
		// return
		List<UserListItem> rs = query.getResultList();
		return rs.size() == 1 ? rs.get(0) : null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<Object[]> searchUserListItem(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT u.id, u.name, u.surname, u.officialEmail, i.listid FROM user_list_item AS i INNER JOIN user_accounts AS u ON u.id=i.userid  WHERE i.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY i.id ASC ";
		// QUERY
		//System.out.println(this.hsql);
		Query query = entityManager.createNativeQuery(this.hsql);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		//Ajustes
		query.setParameter("removed", 0);
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> res = query.getResultList();
		return res;
	}

	@SuppressWarnings("unused")
	@Override
	public Long searchTotalUserListItem(ListMultimap<String, Object> conditions) {
		// SQL
		String hql = "SELECT COUNT(i.id) AS total FROM UserListItem i  WHERE i.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(this.hsql, Long.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}

	/**
	 * Adicionar os paramentros default da busca
	 * 
	 * @param hql
	 * @param name
	 * @param address
	 * @param email
	 * @param phone
	 * @return
	 */
	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Fix
		params.put("removed", false);
		// Where
		if (conditions != null && conditions.size() > 0) {
			for (String k : conditions.keySet()) {
				// Getter values
				Collection<Object> values = conditions.get(k);
				for (Object v : values) {
					if (k.equals("taskId")) {
						Long taskId = (Long) v;
						if (taskId > 0) {
							hql += " AND i.taskId=:taskId";
							params.put("taskId", taskId);
						}
					}
					if (k.equals("userListId")) {
						Long userListId = (Long) v;
						if (userListId > 0) {
							hql += " AND i.listId=:userListId";
							params.put("userListId", userListId);
						}
					}
				}
			}
		}
		// Set
		this.hsql = hql;
		this.hpars = params;
		return true;// Flag
	}
}