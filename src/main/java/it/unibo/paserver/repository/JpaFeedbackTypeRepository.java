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
import it.unibo.paserver.domain.FeedbackType;

@Repository("feedbackTypeRepository")
public class JpaFeedbackTypeRepository implements FeedbackTypeRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public FeedbackType saveOrUpdate(FeedbackType f) {

		if (f.getId() != null && f.getId() != 0) {
			f.setUpdateDate(new DateTime());
			return entityManager.merge(f);
		} else {
			f.setId(null);
			f.setCreationDate(new DateTime());
			f.setUpdateDate(new DateTime());
			entityManager.persist(f);
			return f;
		}
	}

	@Override
	public FeedbackType findById(long id) {

		return entityManager.find(FeedbackType.class, id);
	}

	@Override
	public List<FeedbackType> findAll() {

		String hql = "SELECT f FROM FeedbackType f";
		TypedQuery<FeedbackType> query = entityManager.createQuery(hql, FeedbackType.class);
		List<FeedbackType> f = query.getResultList();
		return f;
	}

	@Override
	public Long getCount() {

		String hql = "SELECT COUNT(id) FROM FeedbackType f";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {

		try {
			FeedbackType f = findById(id);
			f.setRemoved(true);
			entityManager.merge(f);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT f.id, f.name, f.description FROM FeedbackType f WHERE f.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY f.name ASC ";
		// QUERY
		//System.out.println(this.hsql);
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> res = query.getResultList();
		return res;
	}

	@SuppressWarnings("unused")
	@Override
	public List<FeedbackType> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT f FROM FeedbackType f WHERE f.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY f.name ASC ";
		// QUERY
		//System.out.println(this.hsql);
		TypedQuery<FeedbackType> query = entityManager.createQuery(this.hsql, FeedbackType.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<FeedbackType> res = query.getResultList();
		return res;
	}	
	
	@SuppressWarnings("unused")
	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		// SQL
		String hql = "SELECT COUNT(DISTINCT f.id) AS total FROM FeedbackType f WHERE f.removed=:removed ";
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
				if (Validator.isValueinArray(haystack, k)) {
					// Getter values
					Collection<Object> values = conditions.get(k);
					int size = values.size();
					boolean parenthesis = values.size() > 1;
					hql += (parenthesis) ? " AND ( " : " AND ";
					for (Object v : values) {
						if(k.equals("updateDate")) {
							hql += " f." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);							
						}else {
							hql += " UPPER(f." + k + ") LIKE:" + k + count + " ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");
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
}
