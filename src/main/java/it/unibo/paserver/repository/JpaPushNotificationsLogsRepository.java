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

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.PushNotificationsLogs;

@Repository("pushNotificationsLogsRepository")
public class JpaPushNotificationsLogsRepository implements PushNotificationsLogsRepository {
	@PersistenceContext
	private EntityManager entityManager;
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public List<PushNotificationsLogs> findAll() {
		
		// HQL
		String hql = "SELECT t FROM PushNotificationsLogs l WHERE removed=:removed";
		TypedQuery<PushNotificationsLogs> query = entityManager.createQuery(hql, PushNotificationsLogs.class).setParameter("removed", false);
		// return
		List<PushNotificationsLogs> list = query.getResultList();
		return list;
	}

	@Override
	public PushNotificationsLogs saveOrUpdate(PushNotificationsLogs l) {
		
		System.out.println("PushNotificationsLogs " +l.getParentId());
		System.out.println("PushNotificationsLogs " +l.getUpdateDate());
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
	public PushNotificationsLogs findById(long id) {
		
		return entityManager.find(PushNotificationsLogs.class, id);
	}

	@Override
	public boolean removed(long id) {
		
		try {
			PushNotificationsLogs l = findById(id);
			l.setRemoved(true);
			entityManager.merge(l);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<Object[]> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT l.id,  l.multicast_id, l.canonical_ids, l.success , l.failure, l.results_error, l.results_message_id, l.results_registration_id, u.name, u.officialemail" 
				+ " FROM pushnotifications_logs AS l INNER JOIN user_accounts AS u ON u.id = l.userid " 
				+ " WHERE l.removed=:removed ";
		// Where
		boolean q = this.searchSetParameter(hql, conditions, true);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY l.id DESC ";
		// QUERY
		//System.out.println(this.hsql);
		Query query = entityManager.createNativeQuery(this.hsql);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> u = query.getResultList();
		return u;
	}
	
	@SuppressWarnings("unused")
	@Override
	public Long filterTotal(ListMultimap<String, Object> params) {
		
		String hql = "SELECT COUNT(l.id) AS total " 
				+ " FROM pushnotifications_logs AS l " 
				+ " WHERE l.removed=:removed ";
		// Where
		boolean q = this.searchSetParameter(hql, params, true);
		// QUERY
		Query query = entityManager.createNativeQuery(this.hsql);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// Execute
		Long rs = ((Number) query.getSingleResult()).longValue();
		return (rs == null) ? 0L : rs;
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT l.id,  l.multicastId, l.canonicalIds, l.success , l.failure, l.resultsError, l.resultsMessageId, l.resultsRegistrationId" 
				+ " FROM PushNotificationsLogs l " 
				+ " WHERE l.removed=:removed ";
		// Where
		boolean q = this.searchSetParameter(hql, conditions, false);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY l.id DESC ";
		// QUERY
		//System.out.println(this.hsql);
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> u = query.getResultList();
		return u;
	}

	@SuppressWarnings("unused")
	@Override
	public Long searchTotal(ListMultimap<String, Object> conditions) {
		
		// SQL
		String hql = "SELECT COUNT(l.id) AS total " 
				+ " FROM PushNotificationsLogs l " 
				+ " WHERE l.removed=:removed ";
		// Where
		boolean q = this.searchSetParameter(hql, conditions, false);
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

	private boolean searchSetParameter(String hql, ListMultimap<String, Object> conditions, boolean isNative) {
		// Especiais
		String[] haystack = { "start", "creationDate", "taskId", "campaignId","pushNotificationsId"};
		String[] hayStart = { "start", "creationDate" };
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", (isNative)?0:false);
		// Where
		if (conditions != null && conditions.size() > 0) {
			int count = 0;
			for (String k : conditions.keySet()) {
				// Getter values
				Collection<Object> values = conditions.get(k);
				// mais de um?
				boolean operator = !Validator.isValueinArray(haystack, k);
				boolean isStart = Validator.isValueinArray(hayStart, k);
				int size = values.size();
				boolean parenthesis = values.size() > 1;
				hql += (parenthesis) ? " AND ( " : " AND ";
				for (Object v : values) {
					if(isNative && k.equals("pushnotificationsId")) {
						hql += " l.pushnotifications_id=:" + k + count + " ";
						params.put(k + count, v);
					}else if (operator) {
						hql += " UPPER(l." + k + ") LIKE:" + k + count + " ";
						params.put(k + count, "%" + v.toString().toUpperCase() + "%");
					} else {
						if (isStart) {
							hql += " l." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						} else {
							hql += " l." + k + "=:" + k + count + " ";
							params.put(k + count, v);
						}
					}
					hql += (--size > 0) ? " OR " : "";
					count++;
				}
				hql += (parenthesis) ? " ) " : " ";
			}
		}
		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;// Flag
	}
}