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
import it.unibo.paserver.domain.CkanComcap;

@SuppressWarnings("Duplicates")
@Repository("ckanComcapRepository")
public class JpaCkanComcapRepository implements CkanComcapRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private String hsql;

	private Map<String, Object> hpars;

	@Override
	public List<CkanComcap> filter(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT ck FROM CkanComcap ck  WHERE ck.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, params);
		// ORDER BY
		String sort = ascending ? " DESC " : " ASC ";
		String cols = " ORDER BY ck.id ";
		if (sortField.equals("Dates")) {
			cols = " ORDER BY CK.creationDate ";
		}
		this.hsql = this.hsql + cols + sort;
		System.out.println(this.hsql.toString());
		// QUERY
		TypedQuery<CkanComcap> query = entityManager.createQuery(this.hsql, CkanComcap.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<CkanComcap> u = query.getResultList();
		return u;
	}

	@Override
	public List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT  ck.id, ck.codigoBalanca, ck.pesoLiquido, ck.codigoRoteiro, ck.roteiro, ck.codigoDestino, ck.destino, ck.codigoResiduo, ck.residuo, ck.codigo_regiao, ck.regiao, ck.dataPesagem, ck.queryAt, ck.creationDate "
				+ " FROM CkanComcap ck " + " WHERE ck.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, params);
		// ORDER BY
		String sort = ascending ? " DESC " : " ASC ";
		String cols = " ORDER BY ck.id ";
		if (sortField.equals("Dates")) {
			cols = " ORDER BY CK.creationDate ";
		}
		this.hsql = this.hsql + cols + sort;
		System.out.println(this.hsql.toString());
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
		List<Object[]> u = query.getResultList();
		return u;
	}

	@Override
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		// SQL
		String hql = "SELECT COUNT(DISTINCT ck.id) AS total FROM CkanComcap ck WHERE ck.removed=:removed ";
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
		String[] haystack = { "queryAt", "updateDate", "dateStart", "dateEnd", "regiao", "search" };
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
							hql += " ck." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);

						} else if (k.equals("queryAt")) {
							hql += " ck." + k + "=:" + k + count + " ";
							params.put(k + count, v);

						} else if (k.equals("dateStart")) {
							hql += " ck.queryAt>=:" + k + count + " ";
							params.put(k + count, v);

						} else if (k.equals("dateEnd")) {
							hql += " ck.queryAt<=:" + k + count + " ";
							params.put(k + count, v);

						} else if (k.equals("regiao")) {
							hql += " UPPER(ck." + k + ") LIKE:" + k + count + " ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");

						} else if (k.equals("search")) {
							hql += " ( UPPER(ck.regiao) LIKE:" + k + count + " OR " + " UPPER(ck.roteiro) LIKE:" + k + count + " OR " + " UPPER(ck.destino) LIKE:" + k + count + " OR " + " UPPER(ck.residuo) LIKE:" + k + count + " ) ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");

						} else {
							hql += " ck." + k + "=:" + k + count + " ";
							params.put(k + count, v.toString());
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
