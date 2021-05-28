package it.unibo.paserver.repository;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.support.DataPhotoComparator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;

@Repository("dataRepository")
public class JpaDataRepository implements DataRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private String hsql;

	private Map<String, Object> hpars;

	private static final Logger logger = LoggerFactory.getLogger(JpaDataRepository.class);

	@Override
	public Data findById(long id) {
		return entityManager.find(Data.class, id);
	}

	@Override
	public <T extends Data> T save(T data) {
		if (data.getId() != null) {
			logger.trace("Merging data {}", data.toString());
			return entityManager.merge(data);
		} else {
			logger.trace("Persisting data {}", data.toString());
			entityManager.persist(data);
			return data;
		}
	}

	@Override
	public Long getDataCount() {
		String hql = "select count(id) from Data";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getDataCountByUser(long userId) {
		String hql = "select count(d.id) from TaskResult t join t.data d where t.taskReport.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getDataCountByTask(long taskId) {
		String hql = "select count(d.id) from TaskResult t join t.data d where t.taskReport.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<Data> getData() {
		String hql = "from Data";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getData(Class<T> clazz) {
		String hql = String.format("from %s", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByUser(long userId) {
		String hql = "select r.taskResult.data from TaskReport r where r.user.id = :userId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByUser(long userId, Class<T> clazz) {
		String hql = String.format("from %s d where d.user.id = :userId)", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByTask(long taskId) {
		String hql = "select t.taskResult.data from TaskReport t where t.task.id = :taskId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByTask(long taskId, Class<T> clazz) {
		String hql = String.format("from %s d where d.id in (select distinct r.id from TaskResult t join t.data r where t.taskReport.task.id = :taskId)", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByUserAndTask(long userId, long taskId) {
		String hql = "select t.taskResult.data from TaskReport t where t.task.id = :taskId and t.user.id= :userId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class).setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByUserAndTask(long userId, long taskId, Class<T> clazz) {
		String hql = String.format("from %s d where d.id in(select distinct r.id from TaskResult t join t.data r where t.taskReport.task.id = :taskId and t.taskReport.user.id = :userId)", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz).setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByTaskReport(long taskReportId) {
		String hql = "select t.taskResult.data from TaskReport t where t.id = :taskReportId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class).setParameter("taskReportId", taskReportId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByTaskReport(long taskReportId, Class<T> clazz) {
		String hql = String.format("from %s d where d.id in (select distinct r.id from TaskResult t join t.data r where t.id = :taskReportId)", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz).setParameter("taskReportId", taskReportId);
		return query.getResultList();
	}

	@Override
	public boolean deleteData(long id) {
		Data data = findById(id);
		try {
			if (data != null) {
				entityManager.remove(data);
				return true;
			} else {
				logger.warn("Unable to find data {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public <T extends Data> T merge(T data) {
		return entityManager.merge(data);
	}

	@Override
	public BinaryDocument getPhotoContent(Long id) {
		try {
			DataPhoto dataPhoto = (DataPhoto) findById(id);
			BinaryDocument bd = dataPhoto.getFile();
			Hibernate.initialize(bd);
			return bd;
		} catch (ClassCastException e) {
			logger.error("Unable to get DataPhoto {}", id);
			throw new NoResultException();
		}
	}

	@Override
	public List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId) {
		String hql = "from DataPhoto d where d.actionId = :actionId and d.taskId = :taskId";
		TypedQuery<DataPhoto> query = entityManager.createQuery(hql, DataPhoto.class).setParameter("taskId", taskId).setParameter("actionId", actionId);
		List<DataPhoto> result = query.getResultList();
		Collections.sort(result, new DataPhotoComparator());
		return result;
	}

	/**
	 * Consulta customizada
	 */
	@Override
	public <T extends Data> List<T> search(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params, PageRequest pageable) {
		// SQL
		// String hql = String.format("SELECT d FROM %s d WHERE d.dataReceivedTimestamp
		// BETWEEN :startDate AND :endDate ", clazz.getSimpleName());
		String hql = String.format("SELECT d FROM %s d WHERE d.sampleTimestamp BETWEEN :startDate AND :endDate ", clazz.getSimpleName());
		if (userId > 0 && params != null) {
			params.put("userId", userId);
		}
		// WHERE
		boolean q = this.searchsetParameter(hql, params);
		// ORDER BY
		// this.hsql = this.hsql + " ORDER BY d.dataReceivedTimestamp DESC ";
		this.hsql = this.hsql + " ORDER BY d.sampleTimestamp DESC ";
		System.out.println(this.hsql);
		System.out.println(String.format(" %s %s %s ", params.toString(), startDate.toString(), endDate.toString()));
		// QUERY
		TypedQuery<T> query = entityManager.createQuery(this.hsql, clazz);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// LIMIT
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<T> res = query.getResultList();
		for (int i = 0; i < res.size(); i++) {
			Hibernate.initialize(res.get(i).getUser());
		}
		// Response
		return res;
	}

	/**
	 * Retorna o total de uma consulta customizada
	 */
	@Override
	public <T extends Data> Long searchTotal(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params) {
		
		this.hpars.clear();
		// SQL
		String hql = String.format("SELECT COUNT(d.id) FROM %s d WHERE d.sampleTimestamp BETWEEN :startDate AND :endDate ", clazz.getSimpleName());
		if (userId > 0 && params != null) {
			params.put("userId", userId);
		}
		boolean q = this.searchsetParameter(hql, params);
		System.out.println(this.hsql);
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(this.hsql, Long.class);
		// PARAMS
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0 : rs;
	}

	@Override
	public List<DataPhoto> searchActionPhoto(long taskId, long userId, long actionId, PageRequest pageable) {
		
		// SQL
		String hql = "SELECT p FROM DataPhoto p WHERE p.actionId=:actionId AND p.taskId=:taskId ";
		if (userId > 0) {
			hql = hql + " AND p.user.id=:userId ";
		}
		// QUERY
		TypedQuery<DataPhoto> query = entityManager.createQuery(hql, DataPhoto.class);
		// PARAMS
		if (userId > 0) {
			query.setParameter("userId", userId);
		}
		query.setParameter("actionId", actionId);
		query.setParameter("taskId", taskId);
		//System.out.println(String.format("%s %s %s %s %s  ", hql, actionId, userId,  taskId, 0));
		// LIMIT
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// Execute
		List<DataPhoto> res = query.getResultList();
		for (int i = 0; i < res.size(); i++) {
			Hibernate.initialize(res.get(i).getUser());
			Hibernate.initialize(res.get(i).getFile());
		}
		// Response
		return res;
	}

	@Override
	public Long searchActionPhotoTotal(long taskId, long userId, long actionId) {
		
		return 0L;
	}

	@Override
	public List<Object[]> searchActionQuestionClosed(long actionId) {
		String hql = "SELECT " + "ca.question_question_id, q.title, q.description, " + " ua.id, ua.name, ua.surname, ua.officialemail, " + " ca.closed_answer_id, dca.closedanswervalue, dca.received_timestamp "
				+ " FROM actionquestionaire q " + " INNER JOIN actionquestionaire_question aq ON aq.action_id=q.action_id " + " INNER JOIN closedanswer ca ON ca.question_question_id=aq.question_id "
				+ " INNER JOIN dataquestionaireclosedanswer dca ON dca.closed_answer_id=ca.closed_answer_id " + " INNER JOIN user_accounts ua ON ua.id=dca.user_id " + " WHERE q.action_id=:actionId ";
		// QUERY
		// Query query = entityManager.createNativeQuery(hql, Object[].class);
		Query query = entityManager.createNativeQuery(hql);
		// PARAMS
		query.setParameter("actionId", actionId);
		// Execute
		List<Object[]> res = query.getResultList();
		// Response
		return res;
	}

	@Override
	public List<Object[]> searchActionQuestionOpen(long actionId) {
		String hql = "SELECT aq.question_id, q.title, q.description, " + "ua.id, ua.name, ua.surname, ua.officialemail," + " doa.data_id, doa.openanswervalue, doa.received_timestamp " + "FROM actionquestionaire AS q "
				+ " INNER JOIN actionquestionaire_question AS aq ON aq.action_id=q.action_id " + " INNER JOIN dataquestionaireopenanswer AS doa ON doa.question_id=aq.question_id " + " INNER JOIN user_accounts AS ua ON ua.id=doa.user_id"
				+ " WHERE q.action_id=:actionId ";
		// QUERY
		// Query query = entityManager.createNativeQuery(hql, Object[].class);
		Query query = entityManager.createNativeQuery(hql);
		// PARAMS
		query.setParameter("actionId", actionId);
		// Execute
		List<Object[]> res = query.getResultList();
		// Response
		return res;
	}

	@Override
	public List<Object[]> searchToChart(Class<? extends Data> clazz, DateTime startDate, DateTime endDate, long userId, String groupBy) {
		
		// SQL
		String hql = String.format("SELECT d.%s, COUNT(d.id) FROM %s d WHERE d.dataReceivedTimestamp BETWEEN :startDate AND :endDate ", groupBy, clazz.getSimpleName());
		if (userId > 0) {
			hql = hql + " AND d.user.id=:userId ";
		}
		// ORDER BY
		hql = hql + String.format(" GROUP BY d.%s ", groupBy);
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
		// PARAMS
		if (userId > 0) {
			query.setParameter("userId", userId);
		}
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		// Execute
		List<Object[]> res = query.getResultList();
		// Response
		return res;
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
		String[] haystack = { "name" };
		String[] hayusers = { "userId", "xorProgenitorId", "orProgenitorId" };
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		// Where
		if (conditions != null && conditions.size() > 0) {
			int count = 0;
			boolean isOr = false;

			for (String k : conditions.keySet()) {
				// Getter values
				Collection<Object> values = conditions.get(k);
				// mais de um?
				boolean operator = Validator.isValueinArray(haystack, k);
				boolean isUser = Validator.isValueinArray(hayusers, k);
				int size = values.size();
				boolean parenthesis = values.size() > 1;
				hql += (parenthesis) ? (isOr ? " OR ( " : " AND ( ") : (isOr ? " OR " : " AND ");
				isOr = false;

				for (Object v : values) {
					if (operator) {
						hql += " UPPER(d." + k + ") LIKE:" + k + count + " ";
						params.put(k + count, "%" + v.toString().toUpperCase() + "%");
					} else if (isUser) {
						if (k.equalsIgnoreCase("xorProgenitorId")) {
							hql += " d.user.id=:" + k + count + " ";
							params.put(k + count, v);
							isOr = true;

						} else if (k.equalsIgnoreCase("orProgenitorId")) {
							hql += " d.user.progenitorId=:" + k + count + " ";
							params.put(k + count, v);
							isOr = true;

						} else {
							hql += " d.user.id=:" + k + count + " ";
							params.put(k + count, v);
						}
					} else {
						hql += " d." + k + "=:" + k + count + " ";
						params.put(k + count, v);
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
}