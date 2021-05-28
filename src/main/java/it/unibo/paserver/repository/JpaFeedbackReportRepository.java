package it.unibo.paserver.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.FeedbackReport;

@Repository("feedbackReportRepository")
public class JpaFeedbackReportRepository implements FeedbackReportRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public FeedbackReport saveOrUpdate(FeedbackReport fr) {
		if (fr.getId() != null && fr.getId() != 0) {
			fr.setUpdateDate(new DateTime());
			return entityManager.merge(fr);
		} else {
			fr.setId(null);
			fr.setCreationDate(new DateTime());
			fr.setUpdateDate(new DateTime());
			fr.setEditDate(new DateTime());
			entityManager.persist(fr);
			return fr;
		}
	}

	@Override
	public FeedbackReport findById(long id) {
		FeedbackReport fr = entityManager.find(FeedbackReport.class, id);
		if (fr != null) {
			Hibernate.initialize(fr.getFiles());
		}
		return fr;
	}

	@Override
	public List<FeedbackReport> findAll() {

		String hql = "SELECT fr FROM FeedbackReport fr";
		TypedQuery<FeedbackReport> query = entityManager.createQuery(hql, FeedbackReport.class);
		List<FeedbackReport> fr = query.getResultList();
		return fr;
	}

	@Override
	public Long getCount() {

		String hql = "SELECT COUNT(id) FROM FeedbackReport fr";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {

		try {
			FeedbackReport fr = findById(id);
			fr.setRemoved(true);
			entityManager.merge(fr);
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
		String hql = "SELECT COUNT(DISTINCT fr.id) AS total FROM FeedbackReport fr WHERE fr.removed=:removed ";
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
		String hql = "SELECT fr.id, fr.user.name, fr.user.officialEmail, fr.comment, fr.type.name, fr.creationDate FROM FeedbackReport fr WHERE fr.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY fr.creationDate DESC ";
		// QUERY
		System.out.println(this.hsql);
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
	public List<FeedbackReport> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {

		// SQL
		String hql = "SELECT fr FROM FeedbackReport fr WHERE fr.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY fr.creationDate ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<FeedbackReport> query = entityManager.createQuery(this.hsql, FeedbackReport.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<FeedbackReport> res = query.getResultList();
		return res;
	}

	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Allow
		String[] haystack = { "name", "updateDate", "search", "start", "end", "feedback_type_id" };
		// Fix
		params.put("removed", false);
		int count = 0;
		System.out.println(conditions.toString());
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
							hql += " fr." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);

						} else if (k.equals("start")) {
							DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
							DateTime dateTime = DateTime.parse(v.toString(), dtf);
							hql += " fr.updateDate >=:" + k + count;
							params.put(k + count, dateTime);
							
						} else if (k.equals("end")) {
							DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
							DateTime dateTime = DateTime.parse(v.toString(), dtf);
							hql += " fr.updateDate <=:" + k + count;
							params.put(k + count, dateTime);		
							
							
						} else if (k.equals("feedback_type_id")) {
							hql += " fr.type.id =:" + k + count;
							params.put(k + count, Long.parseLong(v.toString()));								

						} else if (k.equals("name") || k.equals("search")) {
							hql += " ( UPPER(fr.user.name) LIKE:" + k + count + " OR UPPER(fr.comment) LIKE:" + k + count + "  ) ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");

						} else {
							hql += " UPPER(fr." + k + ") LIKE:" + k + count + " ";
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
