package it.unibo.paserver.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.support.Pipeline.Type;

@Repository("taskReportRepository")
public class JpaTaskReportRepository implements TaskReportRepository {
	@PersistenceContext
	private EntityManager entityManager;
	private String hsql;
	private Map<String, Object> hpars;

	private static final Logger logger = LoggerFactory.getLogger(JpaTaskReportRepository.class);

	@Override
	public TaskReport findById(long id) {
		return entityManager.find(TaskReport.class, id);
	}

	@Override
	public TaskReport findByUserAndTask(long userId, long taskId) {
		String hql = "SELECT t FROM TaskReport t WHERE t.task.id = :taskId AND t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId).setParameter("userId", userId);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public TaskReport fetchByUserAndTask(long userId, long taskId) {
		String hql = "SELECT t FROM TaskReport t WHERE t.task.id = :taskId AND t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId).setParameter("userId", userId);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public TaskReport getTaskReportAccptedOrRejected(long taskId, long userId) {
		String hql = "SELECT t FROM TaskReport t WHERE t.task.id = :taskId AND t.user.id = :userId AND (t.currentState='RUNNING' OR t.currentState='REJECTED' OR t.currentState='IGNORED')";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId).setParameter("userId", userId);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public TaskReport save(TaskReport taskReport) {
		if (taskReport.getId() != null) {
			logger.trace("Merging taskReport {}", taskReport.toString());
			return entityManager.merge(taskReport);
		} else {
			logger.trace("Persisting taskReport {}", taskReport.toString());
			entityManager.persist(taskReport);
			return taskReport;
		}
	}

	@Override
	public Long getTaskReportsCount() {
		String hql = "select count(id) from TaskReport";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByUserAndTask(long userId, long taskId) {
		String hql = "SELECT count(id) FROM TaskReport t WHERE t.task.id = :taskId AND t.user.id = :userId ";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByUser(long userId) {
		String hql = "select count(id) from TaskReport t where t.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByTask(long taskId) {
		String hql = "select count(id) from TaskReport t where t.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<TaskReport> getTaskReports() {
		String hql = "from TaskReport";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class);
		return query.getResultList();
	}

	@Override
	public List<TaskReport> getTaskReportsByUser(long userId) {
		String hql = "from TaskReport t where t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(long taskId) {
		String hql = "from TaskReport t where t.task.id = :taskId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getTaskReportsByTaskUniqueUser(long taskId) {
		String hql = "SELECT t.user.id From TaskReport t WHERE t.task.id=:taskId GROUP BY t.user.id";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class).setParameter("taskId", taskId);
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public boolean deleteTaskReport(long id) {
		TaskReport taskReport = findById(id);
		try {
			if (taskReport != null) {
				entityManager.remove(taskReport);
				return true;
			} else {
				logger.warn("Unable to find taskReport {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public List<TaskReport> getTaskReportsForData(long userId, Type dataType, DateTime sampleTimestamp) {
		List<TaskReport> result = new ArrayList<TaskReport>();
		String hql = "FROM TaskReport t WHERE t.user.id = :userId AND t.acceptedDateTime <= :sampleDateTime AND t.expirationDateTime >= :sampleDateTime";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("userId", userId).setParameter("sampleDateTime", sampleTimestamp);
		List<TaskReport> queryResult = query.getResultList();

		// filter task reports by actual acceptance and expiration datetimes
		for (TaskReport r : queryResult) {
			if (r.getTask().hasPipelineType(dataType)) {
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now) {
		String hql = "from TaskReport t where t.task.deadline < :now and t.currentState = :available";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("now", now).setParameter("available", TaskState.AVAILABLE);
		List<TaskReport> queryResult = query.getResultList();
		return queryResult;
	}

	@Override
	public Set<String> getAssignedOfficialEmailByTask(long taskId) {
		String hql = "select t.user.officialEmail from TaskReport t where t.task.id = :taskId";
		TypedQuery<String> query = entityManager.createQuery(hql, String.class).setParameter("taskId", taskId);
		List<String> listResult = query.getResultList();
		Set<String> result = new TreeSet<String>();
		result.addAll(listResult);
		return result;
	}

	@Override
	public TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId, Long ownerId) {
		String hql = "select tr from TaskUser t join t.task.taskReport tr where t.owner.id = :ownerId and t.task.id = :taskId and tr.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("ownerId", ownerId).setParameter("taskId", taskId).setParameter("userId", userId);
		TaskReport tr = query.getSingleResult();
		return tr;
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(Long id, TaskState state) {
		String hql = "from TaskReport t where t.task.id = :taskId and t.currentState = :currentState";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", id).setParameter("currentState", state);
		return query.getResultList();
	}

	/**
	 * Busca dinamica
	 */
	@Override
	public List<Object[]> search(long taskId, String officialEmail, TaskState taskState, PageRequest pageable) {
		// SQL
		String hql = "SELECT t.id, t.user.id, t.user.name, t.user.surname, t.user.officialEmail, t.currentState, t.acceptedDateTime, t.expirationDateTime, t.creationDate, "
				+ " t.details, t.sensingProgress, t.photoProgress, t.questionnaireProgress "
				+ " FROM TaskReport t" 
				+ " WHERE t.task.id=:taskId ";
		// Where
		boolean q = this.searchsetParameter(hql, taskId, officialEmail, taskState);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY t.currentState DESC, acceptedDateTime ASC ";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}
	
	@Override
	public List<Object[]> search(long taskId, String search, TaskState taskState, PageRequest pageable, String orderByColumn) {
		// SQL
		String hql = "SELECT t.id, t.user.id, t.user.name, t.user.surname, t.user.officialEmail, t.currentState, t.acceptedDateTime, t.expirationDateTime, t.creationDate, "
				+ " t.details, t.sensingProgress, t.photoProgress, t.questionnaireProgress, t.user.progenitorId, t.user.isGuest "
				+ " FROM TaskReport t" 
				+ " WHERE t.task.id=:taskId ";
		// Where
		boolean q = this.searchsetParameter(hql, taskId, search, taskState);
		// ORDER BY
		this.hsql = this.hsql + " ORDER BY "+orderByColumn+ ", acceptedDateTime ASC ";
		// QUERY
		TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// limit
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		// Execute
		List<Object[]> i = query.getResultList();
		return i;
	}

	@Override
	public Long searchTotal(long taskId, ListMultimap<String, Object> conditions) {
		
		return 0L;
	}

	@Override
	public Long searchTotal(long taskId, String officialEmail, TaskState taskState) {
		
		// SQL
		String hql = "SELECT COUNT(DISTINCT t.user.id) AS total " + " FROM TaskReport t" + " WHERE t.task.id=:taskId ";
		// Where
		boolean q = this.searchsetParameter(hql, taskId, officialEmail, taskState);
		// QUERY
		TypedQuery<Long> query = entityManager.createQuery(this.hsql, Long.class);
		// Where
		for (Entry<String, Object> pars : this.hpars.entrySet()) {
			query.setParameter(pars.getKey(), pars.getValue());
		}
		// Execute
		Long rs = query.getSingleResult();
		return (rs == null) ? 0L : rs;
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
	private boolean searchsetParameter(String hql, long taskId, String officialEmail, TaskState taskState) {
		// Dynamic
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		// Name
		if (!Validator.isEmptyString(officialEmail)) {
			hql += " AND UPPER(t.user.officialEmail) LIKE :officialEmail";
			params.put("officialEmail", "%" + officialEmail.toString().toUpperCase() + "%");
		}
		// Set
		this.hsql = hql;
		this.hpars = params;

		return true;// Flag
	}

	@Override
	public List<TaskReport> searchByUserAndTask(long userId, long taskId) {
		String hql = "from TaskReport t where t.task.id = :taskId and t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public TaskReport findByUserDeviceAndTask(long userDeviceId, long taskId) {
		// System.out.println(userDeviceId + " "+taskId);
		String hql = "from TaskReport t where t.task.id = :taskId and t.userDevice.id = :userDeviceId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("taskId", taskId).setParameter("userDeviceId", userDeviceId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByUserDevice(long userDeviceId) {
		String hql = "select count(id) from TaskReport t where t.userDevice.id = :userDeviceId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("userDeviceId", userDeviceId);
		return query.getSingleResult();
	}

	@Override
	public List<TaskReport> getTaskReportsByUserDevice(long userDeviceId) {
		String hql = "from TaskReport t where t.userDevice.id = :userDeviceId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("userDeviceId", userDeviceId);
		return query.getResultList();
	}

	@Override
	public List<TaskReport> getTaskReportsDeviceForData(long userDeviceId, Type dataType, DateTime sampleTimestamp) {
		List<TaskReport> result = new ArrayList<TaskReport>();
		String hql = "from TaskReport t where t.userDevice.id = :userDeviceId and t.acceptedDateTime <= :sampleDateTime and t.expirationDateTime >= :sampleDateTime";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("userDeviceId", userDeviceId).setParameter("sampleDateTime", sampleTimestamp);
		List<TaskReport> queryResult = query.getResultList();

		// filter task reports by actual acceptance and expiration datetimes
		for (TaskReport r : queryResult) {
			if (r.getTask().hasPipelineType(dataType)) {
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public TaskReport findByUserDeviceAndTaskAndOwner(Long userDeviceId, Long taskId, Long ownerId) {
		String hql = "select tr from TaskUser t join t.task.taskReport tr where t.owner.id = :ownerId and t.task.id = :taskId and tr.userDevice.id = :userDeviceId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql, TaskReport.class).setParameter("ownerId", ownerId).setParameter("taskId", taskId).setParameter("userDeviceId", userDeviceId);
		TaskReport tr = query.getSingleResult();
		return tr;
	}

	@Override
	public long deleteTaskReportAndRelated(long taskReportId) {
		long result = 0;
		String hql = null;
		// Cancellazione TaskResult
		hql = "delete TaskResult t where t.taskReport.id = :taskReportId";
		Query query1 = entityManager.createQuery(hql);
		query1.setParameter("taskReportId", taskReportId);
		result = result + query1.executeUpdate();
		// Cancellazione TaskHistory
		hql = "delete TaskHistory t where t.taskReport.id = :taskReportId";
		Query query2 = entityManager.createQuery(hql);
		query2.setParameter("taskReportId", taskReportId);
		result = result + query2.executeUpdate();
		// Cancellazione TaskReport
		hql = "delete TaskReport where id = :taskReportId";
		Query query3 = entityManager.createQuery(hql);
		query3.setParameter("taskReportId", taskReportId);
		result = result + query3.executeUpdate();
		return result;
	}
}
