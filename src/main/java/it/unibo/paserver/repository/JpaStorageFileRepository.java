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
import it.unibo.paserver.domain.StorageFile;

@Repository("storageFileRepository")
public class JpaStorageFileRepository implements StorageFileRepository {
	@PersistenceContext
	private EntityManager entityManager;
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public StorageFile saveOrUpdate(StorageFile sf) {
		if (sf.getId() != null && sf.getId() != 0) {
			sf.setUpdateDate(new DateTime());
			return entityManager.merge(sf);
		} else {
			sf.setId(null);
			sf.setCreationDate(new DateTime());
			sf.setUpdateDate(new DateTime());
			sf.setEditDate(new DateTime());
			entityManager.persist(sf);
			return sf;
		}
	}

	@Override
	public StorageFile findById(long id) {
		
		return entityManager.find(StorageFile.class, id);
	}

	@Override
	public List<StorageFile> findAll() {
		
		String hql = "SELECT sf FROM StorageFile sf";
		TypedQuery<StorageFile> query = entityManager.createQuery(hql, StorageFile.class);
		List<StorageFile> sf = query.getResultList();
		return sf;
	}

	@Override
	public Long getCount() {
		
		String hql = "SELECT COUNT(id) FROM StorageFile sf ";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public boolean removed(long id) {
		
		try {
			StorageFile sf = findById(id);
			sf.setRemoved(true);
			entityManager.merge(sf);
			//Native
			entityManager.createNativeQuery("DELETE FROM issue_report_storage_file WHERE storage_file_id=:removedId").setParameter("removedId", id).setMaxResults(1).executeUpdate();
			entityManager.createNativeQuery("DELETE FROM feedback_storage_file WHERE storage_file_id=:removedId").setParameter("removedId", id).setMaxResults(1).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	@Override
	public List<Object[]> searchAllFeedback(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = " SELECT sf.id, sf.asseturl, sf.fileextension, sf.filename, fsf.feedback_id " + 
				" FROM storage_file AS sf " + 
				" INNER JOIN feedback_storage_file AS fsf  " + 
				" ON sf.id = fsf.storage_file_id " + 
				" WHERE sf.removed=:removed ";
		// WHERE
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY sf.creationdate DESC ";
		// QUERY
		//System.out.println(this.hsql);
		Query query = entityManager.createNativeQuery(this.hsql);
		// WHERE
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		query.setParameter("removed", 0);
		// LIMIT
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<Object[]> res = (List<Object[]>) query.getResultList();
		return res;
	}

	
	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		// SQL
		String hql = "SELECT COUNT(DISTINCT sf.id) AS total FROM StorageFile sf WHERE sf.removed=:removed ";
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

	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT sf FROM StorageFile sf WHERE sf.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY sf.creationDate DESC ";
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
	public List<StorageFile> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT sf FROM StorageFile sf WHERE sf.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY sf.creationDate ASC ";
		// QUERY
		// System.out.println(this.hsql);
		TypedQuery<StorageFile> query = entityManager.createQuery(this.hsql, StorageFile.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
			// System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<StorageFile> res = query.getResultList();
		return res;
	}

	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Allow
		String[] haystack = { "originalFileName", "updateDate", "FeedbackReportId"};
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
							hql += " sf." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						}else if(k.equals("FeedbackReportId")) {
							//Funciona separado dos outros campos do where
							hql += " fsf.feedback_id=:" + k + count + " ";
							params.put(k + count, v);
						} else {
							hql += " UPPER(sf." + k + ") LIKE:" + k + count + " ";
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
