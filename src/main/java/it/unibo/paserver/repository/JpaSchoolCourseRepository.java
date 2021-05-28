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
import it.unibo.paserver.domain.SchoolCourse;

@Repository("schoolCourseRepository")
public class JpaSchoolCourseRepository implements SchoolCourseRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public List<SchoolCourse> findAll() {
		
		String hql = "SELECT s FROM SchoolCourse s";
		TypedQuery<SchoolCourse> query = entityManager.createQuery(hql, SchoolCourse.class);
		List<SchoolCourse> s = query.getResultList();
		return s;
	}

	@Override
	public boolean removed(long id) {
		
		try {
			SchoolCourse s = findById(id);
			s.setRemoved(true);
			entityManager.merge(s);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public SchoolCourse saveOrUpdate(SchoolCourse s) {
		
		if (s.getId() != null && s.getId() != 0) {
			s.setUpdateDate(new DateTime());
			return entityManager.merge(s);
		} else {
			s.setId(null);
			s.setCreationDate(new DateTime());
			s.setUpdateDate(new DateTime());
			entityManager.persist(s);
			return s;
		}
	}

	@Override
	public SchoolCourse findById(long id) {
		return entityManager.find(SchoolCourse.class, id);
	}
	
	@Override
	public SchoolCourse findByName(String schoolCourse) {
		String hql = "SELECT s FROM SchoolCourse s WHERE name=:schoolName ";
		TypedQuery<SchoolCourse> query = entityManager.createQuery(hql, SchoolCourse.class);
		query.setParameter("schoolName", schoolCourse);
		query.setMaxResults(1);
		// return
		List<SchoolCourse> s = query.getResultList();
		return s.size() == 1 ? s.get(0) : null;
	}

	@Override
	public List<Object[]> search(String name, String description, String uniCourseId, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT id, name, updateDate, creationDate, removed, uniCourseId  FROM SchoolCourse WHERE removed=:removed";
		// Where
		boolean q = this.searchsetParameter(hql, name, description, uniCourseId);
		//ORDER BY
		this.hsql = this.hsql + " ORDER BY name ASC ";		
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public long searchTotal(String name, String description, String uniCourseId) {
		
		// SQL
		String hql = "SELECT COUNT(*) AS total FROM SchoolCourse WHERE removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, name, description, uniCourseId);
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
	 * Adicionar os paramentros default da buscar
	 * 
	 * @param hql
	 * @param name
	 * @param address
	 * @param email
	 * @param phone
	 * @return
	 */
	private boolean searchsetParameter(String hql, String name, String description, String uniCourseId) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", false);
		// Name
		if (!Validator.isEmptyString(name)) {
			hql += " AND UPPER(name) LIKE :name";
			params.put("name", "%" + name.toString().toUpperCase() + "%");
		}
		if (!Validator.isEmptyString(description)) {
			hql += " AND UPPER(description) LIKE :description";
			params.put("description", "%" + description.toString().toUpperCase() + "%");
		}
		if (!Validator.isEmptyString(uniCourseId)) {
			hql += " AND uniCourseId =:uniCourseId";
			params.put("uniCourseId", uniCourseId);
		}
		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;// Flag
	}
	
	@Override
	public List<SchoolCourse> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT s FROM SchoolCourse s WHERE s.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY s.name ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<SchoolCourse> query = entityManager.createQuery(this.hsql, SchoolCourse.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<SchoolCourse> res = query.getResultList();
		return res;
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
						if (k.equals("updateDate")) {
							hql += " s." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						} else {
							hql += " UPPER(s." + k + ") LIKE:" + k + count + " ";
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