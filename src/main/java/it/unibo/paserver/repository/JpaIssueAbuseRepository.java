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
import it.unibo.paserver.domain.IssueAbuse;

@Repository("issueAbuseRepository")
public class JpaIssueAbuseRepository implements IssueAbuseRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public IssueAbuse saveOrUpdate(IssueAbuse ia) {
		if (ia.getId() != null && ia.getId() != 0) {
			ia.setUpdateDate(new DateTime());
			return entityManager.merge(ia);
		} else {
			ia.setId(null);
			ia.setCreationDate(new DateTime());
			ia.setUpdateDate(new DateTime());
			ia.setEditDate(new DateTime());
			entityManager.persist(ia);
			return ia;
		}
	}

	@Override
	public IssueAbuse findById(long id) {
		IssueAbuse ia = entityManager.find(IssueAbuse.class, id);
		return ia;
	}

	@Override
	public List<IssueAbuse> findAll() {

		String hql = "SELECT ia FROM IssueAbuse ia";
		TypedQuery<IssueAbuse> query = entityManager.createQuery(hql, IssueAbuse.class);
		List<IssueAbuse> ia = query.getResultList();
		return ia;
	}

	@Override
	public Long getCount() {

		String hql = "SELECT COUNT(id) FROM IssueAbuse ia";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {

		try {
			IssueAbuse ia = findById(id);
			ia.setRemoved(true);
			entityManager.merge(ia);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		// SQL
		String hql = "SELECT COUNT(DISTINCT ia.id) AS total FROM IssueAbuse ia WHERE ia.removed=:removed ";
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

	@SuppressWarnings("unused")
	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {

		// SQL
		String hql = "SELECT ia.id, ia.comment, ia.creationDate, " 
				+ "	ia.issue.id, ia.issue.comment," 
				+ " ia.type.id, ia.type.name, " 
				+ " ia.user.id, ia.user.name" 
				+ " FROM IssueAbuse ia WHERE ia.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY ia.creationDate DESC ";
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

	@SuppressWarnings("unused")
	@Override
	public List<IssueAbuse> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {

		// SQL
		String hql = "SELECT ia FROM IssueAbuse ia WHERE ia.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY ia.creationDate ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<IssueAbuse> query = entityManager.createQuery(this.hsql, IssueAbuse.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<IssueAbuse> res = query.getResultList();
		return res;
	}

	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Allow
		String[] haystack = { "comment", "updateDate", "issueId" };
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
						if (k.equals("updateDate")) {
							hql += " ia." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						} else if (k.equals("comment")) {
							hql += " UPPER(ia." + k + ") LIKE:" + k + count + " ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");
						} else if (k.equals("issueId")) {
							hql += " ia.issue.id=:" + k + count + " ";
							params.put(k + count, v);
						} else {
							hql += " ia." + k + "=:" + k + count + " ";
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
}
