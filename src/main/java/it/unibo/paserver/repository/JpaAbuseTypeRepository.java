package it.unibo.paserver.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.IssueAbuseType;

@Repository("abuseTypeRepository")
public class JpaAbuseTypeRepository implements AbuseTypeRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public IssueAbuseType saveOrUpdate(IssueAbuseType a) {
		
		if (a.getId() != null && a.getId() != 0) {
			a.setUpdateDate(new DateTime());
			return entityManager.merge(a);
		} else {
			a.setId(null);
			a.setCreationDate(new DateTime());
			a.setUpdateDate(new DateTime());
			entityManager.persist(a);
			return a;
		}
	}

	@Override
	public IssueAbuseType findById(long id) {
		
		return entityManager.find(IssueAbuseType.class, id);
	}

	@Override
	public List<IssueAbuseType> findAll() {
		
		String hql = "SELECT a FROM IssueAbuseType a";
		TypedQuery<IssueAbuseType> query = entityManager.createQuery(hql, IssueAbuseType.class);
		List<IssueAbuseType> a = query.getResultList();
		return a;
	}

	@Override
	public Long getCount() {
		
		String hql = "SELECT count(id) FROM IssueAbuseType a";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {
		
		try {
			IssueAbuseType a = findById(id);
			a.setRemoved(true);
			entityManager.merge(a);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT a.id, a.name, a.description FROM IssueAbuseType a WHERE a.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY a.name ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> res = query.getResultList();
		return res;
	}

	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		// SQL
		String hql = "SELECT COUNT(DISTINCT a.id) AS total FROM IssueAbuseType a WHERE a.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, params);
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

	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Allow
		String[] haystack = { "name", "updateDate" };
		// Fix
		params.put("removed", false);
		int count = 0;
		// Where
		if (conditions != null && conditions.size() > 0) {
			for (String k : conditions.keySet()) {
				// Allowed
				System.out.println(k);
				if (Validator.isValueinArray(haystack, k)) {
					// Getter values
					Collection<Object> values = conditions.get(k);
					int size = values.size();
					boolean parenthesis = values.size() > 1;
					hql += (parenthesis) ? " AND ( " : " AND ";
					for (Object v : values) {
						if (k.equals("name")) {
							hql += " UPPER(a." + k + ") LIKE:" + k + count + " ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");
						}else if (k.equals("updateDate")) {
							hql += " a." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						}else {
							hql += " a." + k + "=:" + k + count + " ";
							params.put(k + count, v);
						}
						hql += (--size > 0) ? " OR " : "";
						count++;
					}
					hql += (parenthesis) ? " ) " : " ";
				}
			}
		}
		// Set
		this.hsql = hql;
		this.hpars = params;
		return true;// Flag
	}

	@Override
	public List<IssueAbuseType> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT a FROM IssueAbuseType a WHERE a.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY a.name ASC ";
		// QUERY
		System.out.println(this.hsql);
		TypedQuery<IssueAbuseType> query = entityManager.createQuery(this.hsql, IssueAbuseType.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<IssueAbuseType> res = query.getResultList();
		return res;
	}

}
