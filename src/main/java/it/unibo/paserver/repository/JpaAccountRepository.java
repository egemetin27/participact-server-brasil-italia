package it.unibo.paserver.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Account;

@Repository("accountRepository")
public class JpaAccountRepository implements AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(JpaAccountRepository.class);
	// Query sendo executado no momento
	private String hsql;
	private Map<String, Object> hpars;

	@Override
	public Account findByUsername(String username) {
		String hql = "select c from Account c where c.username=:username";
		TypedQuery<Account> query = entityManager.createQuery(hql, Account.class).setParameter("username", username);
		List<Account> accounts = query.getResultList();
		return accounts.size() == 1 ? accounts.get(0) : null;
	}

	@Override
	public Account findById(long id) {
		return entityManager.find(Account.class, id);
	}

	@Override
	public Account findByIdAndParentId(long id, long parentId){
		//HQL
		String hql = "SELECT c FROM Account c WHERE c.removed=:removed AND c.id=:id";
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", 0);
		params.put("id", id);
		// Somente de um usuario?
		if (parentId != 0) {
			hql += " AND c.parentId=:parentId";
			params.put("parentId", parentId);
		}
		TypedQuery<Account> query = entityManager.createQuery(hql, Account.class);
		// Where
		for (Entry<String, Object> pars : params.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		query.setMaxResults(1);
		//return
		List<Account> accounts = query.getResultList();
		return accounts.size() == 1 ? accounts.get(0) : null;		
	}
	
	@Override
	public Account save(Account account) {
		// if (account.getId() != null) {
		logger.trace("Merging account {}", account.toString());
		return entityManager.merge(account);
		// } else {
		// logger.trace("Persisting account {}", account.toString());
		// entityManager.persist(account);
		// return account;
		// }
	}

	/**
	 * Salva ou atualiza um item
	 * 
	 * @param account
	 * @return
	 */
	public Account saveOrUpdate(Account account) {
		if (account.getId() != null && account.getId() != 0) {
			account.setCreationDate(new DateTime());
			return entityManager.merge(account);
		} else {
			entityManager.persist(account);
			return account;
		}
	}

	@Override
	public Long getAccountsCount() {
		String hql = "select count(id) from Account c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<Account> getAccounts() {
		String hql = "select c from Account c WHERE removed=0";
		TypedQuery<Account> query = entityManager.createQuery(hql, Account.class);
		List<Account> accounts = query.getResultList();
		return accounts;
	}

	@Override
	public boolean deleteAccount(long id, long parentId) {
		//Account account = findById(id);
		Account account = findByIdAndParentId(id, parentId);
		try {
			if (account != null) {
				entityManager.remove(account);
				return true;
			} else {
				logger.warn("Unable to find account {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	/**
	 * Consulta customizada por paginacao
	 */
	@Override
	public List<Object[]> search(boolean isResearcher, long parentId, String name, String username, String email,
			String phone, String last, PageRequest pageable) {
		// SQL
		String hql = "SELECT c.id, c.name, c.email, c.phone, c.photo, c.username, c.privilege, c.removed, i.name "
				+ "FROM Account c LEFT JOIN c.institution i WHERE c.removed=:removed";
		// Where
		boolean q = this.searchsetParameter(hql, isResearcher, parentId, name, username, email, phone, last);
		//ORDER BY
		this.hsql = this.hsql + " ORDER BY c.name ASC ";
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
		List<Object[]> accounts = query.getResultList();
		return accounts;
	}

	/**
	 * Adicionar os paramentros default da buscar
	 * 
	 * @param hql
	 * @param name
	 * @param username
	 * @param email
	 * @param phone
	 * @param last
	 * @return
	 */
	private boolean searchsetParameter(String hql, boolean isResearcher, long parentId, String name, String username,
			String email, String phone, String last) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", 0);
		// is Admin
		hql += " AND c.isAdmin=:isAdmin";
		params.put("isAdmin", isResearcher);
		// Somente de um usuario?
		if (parentId != 0) {
			hql += " AND c.parentId=:parentId";
			params.put("parentId", parentId);
		}
		// Name
		if (!Validator.isEmptyString(name)) {
			hql += " AND c.name LIKE :name";
			params.put("name", "%" + name + "%");
		}
		// Username
		if (!Validator.isEmptyString(username)) {
			hql += " AND c.username LIKE :username";
			params.put("username", "%" + username + "%");
		}
		// Email
		if (!Validator.isEmptyString(email)) {
			hql += " AND c.email LIKE :email";
			params.put("email", "%" + email + "%");
		}
		// Phone
		if (!Validator.isEmptyString(phone)) {
			hql += " AND c.phone=:phone";
			params.put("phone", phone);
		}
		// Last
		if (!Validator.isEmptyString(last)) {
			hql += " AND c.lastLogin LIKE :last";
			params.put("last", last);
		}

		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;
	}

	@Override
	public long searchTotal(boolean isResearcher, long parentId, String name, String username, String email,
			String phone, String last) {
		// SQL
		String hql = "SELECT COUNT(c.id) AS total FROM Account c WHERE c.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, isResearcher, parentId, name, username, email, phone, last);
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
}