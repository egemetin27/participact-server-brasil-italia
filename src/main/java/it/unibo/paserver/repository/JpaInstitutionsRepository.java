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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.IssueCategory;

@SuppressWarnings("Duplicates")
@Repository("institutionsRepository")
public class JpaInstitutionsRepository implements InstitutionsRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public Institutions saveOrUpdate(Institutions i) {

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
	public Institutions findByName(String name) {

		String hql = "SELECT i FROM Institutions i WHERE i.name=:name";
		TypedQuery<Institutions> query = entityManager.createQuery(hql, Institutions.class).setParameter("name", name);
		List<Institutions> i = query.getResultList();
		return i.size() == 1 ? i.get(0) : null;
	}

	@Override
	public Institutions findById(long id) {

		return entityManager.find(Institutions.class, id);
	}

	@Override
	public List<Institutions> findAll() {

		String hql = "SELECT i FROM Institutions i";
		TypedQuery<Institutions> query = entityManager.createQuery(hql, Institutions.class);
		List<Institutions> i = query.getResultList();
		return i;
	}

	@Override
	public Long getCount() {

		String hql = "SELECT count(id) FROM Institutions i";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {

		try {
			Institutions i = findById(id);
			i.setRemoved(true);
			entityManager.merge(i);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object[]> search(String name, String address, String email, String phone, PageRequest pageable) {

		// SQL
		String hql = "SELECT id, name, email, phone, address, contact, removed  FROM Institutions WHERE removed=:removed";
		// Where
		boolean q = this.searchsetParameter(hql, name, address, email, phone);	
		//ORDER BY
		this.hsql = this.hsql + " ORDER BY name ASC ";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		//limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());		
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}
	

	@Override
	public List<Institutions> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT i FROM Institutions i WHERE i.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY i.name ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<Institutions> query = entityManager.createQuery(this.hsql, Institutions.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Institutions> res = query.getResultList();
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
							hql += " i." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						} else {
							hql += " UPPER(i." + k + ") LIKE:" + k + count + " ";
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

	@Override
	public long searchTotal(String name, String address, String email, String phone) {

		// SQL
		String hql = "SELECT COUNT(*) AS total FROM Institutions WHERE removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, name, address, email, phone);
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
	private boolean searchsetParameter(String hql, String name, String address, String email, String phone) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", false);
		// Name
		if (!Validator.isEmptyString(name)) {
			hql += " AND UPPER(name) LIKE :name";
			params.put("name", "%" + name.toString().toUpperCase() + "%");
		}
		// Username
		if (!Validator.isEmptyString(address)) {
			hql += " AND UPPER(address) LIKE :address";
			params.put("address", "%" + address.toString().toUpperCase() + "%");
		}
		// Email
		if (!Validator.isEmptyString(email)) {
			hql += " AND UPPER(email) LIKE :email";
			params.put("email", "%" + email.toString().toUpperCase() + "%");
		}
		// Phone
		if (!Validator.isEmptyString(phone)) {
			hql += " AND phone=:phone";
			params.put("phone", phone);
		}
		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;//Flag
	}
}
