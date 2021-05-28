package it.unibo.paserver.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.User;

@SuppressWarnings("Duplicates")
@Repository("userRepository")
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntityManagerFactory emf;
	private String hsql;
	private Map<String, Object> hpars;

	private static final Logger logger = LoggerFactory.getLogger(JpaUserRepository.class);

	@Override
	public User findByEmail(String email) {
		String hql = "select c from User c where c.officialEmail=:officialEmail";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class).setParameter("officialEmail", email);
		List<User> users = query.getResultList();
		return users.size() == 1 ? users.get(0) : null;
	}

	@Override
	public User findById(long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User save(User user) {
		// if (user.getId() != null) {
		logger.trace("Merging user {}", user.toString());
		user.setCreationDate(new DateTime());
		user.setUpdateDate(new DateTime());
		return entityManager.merge(user);
		// } else {
		// logger.trace("Persisting user {}", user.toString());
		// entityManager.persist(user);
		// return user;
		// }
	}

	@Override
	/**
	 * Retorna um item pelo email e senha
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password) {
		String hql = "SELECT u FROM User u WHERE u.officialEmail=:userEmail AND u.password=:userPassword";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class).setParameter("userEmail", email).setParameter("userPassword", password).setMaxResults(1);
		System.out.println(hql);
		System.out.println(email);
		System.out.println(password);
		List<User> users = query.getResultList();
		return users.size() == 1 ? users.get(0) : null;
	}

	@Override
	public User created(User user) {
		logger.info("Persisting user {}", user.toString());
		// consegue a entity manager
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(user);
			tx.commit();
			em.flush();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e; // or display error message
		} finally {
			em.close();
		}

		return user;
	}

	@Override
	public Long getUserCount() {
		String hql = "SELECT count(id) FROM User u WHERE isActive = true AND removed=false ";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<User> getUsers() {
		String hql = "select c from User c";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public boolean deleteUser(long id) {
		User user = findById(id);
		try {
			if (user != null) {
				entityManager.remove(user);
				return true;
			} else {
				logger.warn("Unable to find user {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public BinaryDocument getIdScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.ID_SCAN);
	}

	@Override
	public BinaryDocument getLastInvoiceScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.LAST_INVOICE);
	}

	@Override
	public BinaryDocument getPrivacyScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRIVACY);
	}

	@Override
	public BinaryDocument getPresaConsegnaPhone(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRESA_CONSEGNA_PHONE);
	}

	@Override
	public BinaryDocument getPresaConsegnaSIM(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRESA_CONSEGNA_SIM);
	}

	private BinaryDocument getBinaryDocument(Long id, BinaryDocumentType type) {
		User u = findById(id);
		if (u == null) {
			return null;
		}
		BinaryDocument bd = null;
		switch (type) {
		case ID_SCAN:
			bd = u.getIdScan();
			break;
		case LAST_INVOICE:
			bd = u.getLastInvoiceScan();
			break;
		case PRIVACY:
			bd = u.getPrivacyScan();
			break;
		case PRESA_CONSEGNA_PHONE:
			bd = u.getPresaConsegnaPhoneScan();
			break;
		case PRESA_CONSEGNA_SIM:
			bd = u.getPresaConsegnaSIMScan();
			break;
		default:
			bd = null;
		}
		if (bd != null) {
			// do not remove the next two line, they are needed to initialize
			// persistent
			// objects within the hibernate transactional context
			int size = bd.getContent().length;
			logger.trace("Retrieved user {} document {} {} bytes", id, type, size);
		}
		return bd;
	}

	@Override
	public boolean deleteBinaryDocument(long id) {
		BinaryDocument doc = entityManager.find(BinaryDocument.class, id);
		try {
			if (doc != null) {
				entityManager.remove(doc);
				return true;
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		logger.warn("Unable to find binary document {}", id);
		return false;
	}

	@Override
	public List<String> getActiveUsers() {
		String hql = "SELECT officialEmail FROM User c WHERE c.isActive = true AND c.removed=false";
		TypedQuery<String> query = entityManager.createQuery(hql, String.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getWorkingUsers(int inputType, DateTime start) {
		String hql = "select u.* from actionsensing a, task_actions t, taskreport r, user_accounts u where a.input_type = :inputType and r.currentstate = 'RUNNING' and a.action_id = t.action_id and r.task_id = t.task_id and r.user_id = u.id and r.accepteddatetime > :start";
		Query query = entityManager.createNativeQuery(hql, User.class).setParameter("inputType", inputType).setParameter("start", start.toDate());
		return query.getResultList();
	}

	/**
	 * Salva ou atualiza
	 */
	@Override
	public User saveOrUpdate(User u) {

		if (u.getId() != null && u.getId() != 0) {
			u.setUpdateDate(new DateTime());
			return entityManager.merge(u);
		} else {
			u.setId(null);
			u.setCreationDate(new DateTime());
			u.setUpdateDate(new DateTime());
			entityManager.persist(u);
			return u;
		}
	}

	/**
	 * Busca dinamica
	 */
	@SuppressWarnings("unused")
	@Override
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
		// SQL
		String hql = "SELECT u.id, u.name, u.surname, u.officialEmail, u.photo, u.contactPhoneNumber, u.homePhoneNumber, u.currentAddress, u.currentCity, "
				+ " u.currentProvince, u.currentZipCode, u.gender, u.removed, i.name, s.name, u.currentDistrict, u.creationDate, u.device, "
				+ " u.progenitorId, u.isGuest, u.xPlataform, u.xDeviceModel, u.xOsVersion, u.xManufacturer, u.xAppVersionName, u.xAppVersionCode, u.secondaryEmail "
				+ " FROM User u " 
				+ " LEFT JOIN u.institutionId i " 
				+ " LEFT JOIN u.schoolCourseId s "
				+ " WHERE u.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY u.isGuest, u.name ASC ";
		// QUERY
		System.out.println(this.hsql);
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

	/**
	 * Busca customizada, retorna somente os usuarios
	 * 
	 * @param conditions
	 * @param pageable
	 * @return
	 */
	@Override
	public List<User> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {

		// SQL
		String hql = "SELECT u FROM User u " + "	LEFT JOIN u.institutionId i " + "	LEFT JOIN u.schoolCourseId s " + "	WHERE u.removed=:removed ";
		// Where
		@SuppressWarnings("unused")
		boolean q = this.searchsetParameter(hql, conditions);
		//System.out.println(this.hsql.toString());
		//System.out.println(conditions.toString());
		// QUERY
		TypedQuery<User> query = entityManager.createQuery(this.hsql, User.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// LIMIT
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		// Execute
		List<User> u = query.getResultList();
		return u;
	}

	/**
	 * Total de uma consulta dinamica
	 */
	@SuppressWarnings("unused")
	@Override
	public Long searchTotal(ListMultimap<String, Object> conditions) {

		// SQL
		String hql = "SELECT COUNT(u.id) AS total " + " FROM User u " + " WHERE u.removed=:removed ";
		// Where
		boolean q = this.searchsetParameter(hql, conditions);
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
	 * Monta o HQL de acordo com os paramentros passados
	 * 
	 * @param hql
	 * @param conditions
	 * @return
	 */
	private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
		// Especiais
		String[] haystack = { "isIssueSearch", "wildcard", "gender", "documentIdType", "institutionId", "schoolCourseId", "uniCourse", "uniYear", "start", "creationDate", "taskId", "campaignId", "userListId", "userId" };
		String[] hayStart = { "start", "creationDate" };
		String[] kayId = { "taskId", "campaignId", "userListId", "userId" };
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("removed", false);
		// Where
		if (conditions != null && conditions.size() > 0) {
			int count = 0;
			for (String k : conditions.keySet()) {
				// Getter values
				Collection<Object> values = conditions.get(k);
				// mais de um?
				boolean operator = !Validator.isValueinArray(haystack, k);
				boolean isStart = Validator.isValueinArray(hayStart, k);
				boolean isId = Validator.isValueinArray(kayId, k);
				int size = values.size();
				boolean parenthesis = values.size() > 1;
				hql += (parenthesis) ? " AND ( " : " AND ";
				for (Object v : values) {
					if (operator) {
						hql += " UPPER(u." + k + ") LIKE:" + k + count + " ";
						params.put(k + count, "%" + v.toString().toUpperCase() + "%");
					} else {
						if (k.equalsIgnoreCase("isIssueSearch")) {
							hql += " ( ( u.isGuest=0 ) OR (u.isGuest=1 AND (u.progenitorId=0 OR u.progenitorId IS NULL OR u.progenitorId = u.id))) AND (SELECT COUNT(*) FROM DataLocation dl WHERE dl.user.id=u.id  ) > 0 ";					
						
						}else if (k.equalsIgnoreCase("wildcard")) {
							hql += " ( UPPER(u.name) LIKE:" + k + count + " OR UPPER(u.officialEmail) LIKE:" + k + count + " ) ";
							params.put(k + count, "%" + v.toString().toUpperCase() + "%");							
						
						} else if (isStart) {
							hql += " u." + k + ">=:" + k + count + " ";
							DateTime dateTime = DateTime.parse(v.toString());
							params.put(k + count, dateTime);
						} else if (isId) {
							if (k.equalsIgnoreCase("campaignId")) {
								hql += " (SELECT COUNT(*) FROM TaskReport t WHERE t.user.id=u.id AND t.task.id=:" + k + count + " AND t.currentState='RUNNING' ) > 0 ";
							} else if (k.equalsIgnoreCase("userId")) {
								hql += " u.id=:" + k + count + " ";
								params.put(k + count, v);
							} else if (k.equalsIgnoreCase("userListId")) {
								hql += " (SELECT COUNT(ui.id) FROM UserListItem ui WHERE ui.userId=u.id AND ui.listId=:" + k + count + " AND ui.removed=0 ) > 0 ";
							} else {
								hql += " (SELECT COUNT(*) FROM TaskReport t WHERE t.user.id=u.id AND t.task.id=:" + k + count + " ) > 0 ";
							}
							params.put(k + count, v);
						} else {
							hql += " u." + k + "=:" + k + count + " ";
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

	/**
	 * Remocao simbolica
	 */
	@Override
	public boolean removed(long id) {

		try {
			User u = findById(id);
			u.setRemoved(true);
			u.setIsActive(false);
			entityManager.merge(u);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Defaz a remocao
	 * @param id
	 * @return
	 */
	@Override
	public boolean unRemoved(long id) {

		try {
			User u = findById(id);
			u.setRemoved(false);
			u.setIsActive(true);
			entityManager.merge(u);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Busca customizada
	 */
	@Override
	public User findParticipantById(long id) {
		String hql = "SELECT u FROM User u WHERE id=:id AND removed=:removed";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		query.setParameter("id", id);
		query.setParameter("removed", false);
		query.setMaxResults(1);
		List<User> u = query.getResultList();
		return u.size() == 1 ? u.get(0) : null;
	}
}