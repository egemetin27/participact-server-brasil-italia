package it.unibo.paserver.repository;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.Pipeline.Type;

public interface TaskReportRepository {

	TaskReport findById(long id);

	TaskReport findByUserAndTask(long userId, long taskId);
	List<TaskReport> searchByUserAndTask(long userId, long taskId);

	TaskReport save(TaskReport taskReport);

	Long getTaskReportsCount();

	Long getTaskReportsCountByUser(long userId);

	Long getTaskReportsCountByTask(long taskId);

	List<TaskReport> getTaskReports();

	List<TaskReport> getTaskReportsByUser(long userId);

	List<TaskReport> getTaskReportsByTask(long taskId);

	List<TaskReport> getTaskReportsForData(long userId, Pipeline.Type dataType,
			DateTime sampleTimestamp);

	boolean deleteTaskReport(long id);

	List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now);

	Set<String> getAssignedOfficialEmailByTask(long taskId);

	TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId, Long ownerId);

	List<TaskReport> getTaskReportsByTask(Long id, TaskState state);

	TaskReport fetchByUserAndTask(long userId, long taskId);

	Long getTaskReportsCountByUserAndTask(long userId, long taskId);

	List<Object[]> search(long taskId, String officialEmail, TaskState taskState, PageRequest pageable);
	List<Object[]> search(long taskId, String search, TaskState taskState, PageRequest pageable, String orderByColumn);

	Long searchTotal(long id, ListMultimap<String, Object> conditions);

	Long searchTotal(long id, String search, TaskState taskState);

	List<Object[]> getTaskReportsByTaskUniqueUser(long taskId);

	TaskReport getTaskReportAccptedOrRejected(long userId, long taskId);

	TaskReport findByUserDeviceAndTask(long userDeviceId, long taskId);

	Long getTaskReportsCountByUserDevice(long userDeviceId);

	List<TaskReport> getTaskReportsByUserDevice(long userDeviceId);

	List<TaskReport> getTaskReportsDeviceForData(long userDeviceId, Type dataType, DateTime sampleTimestamp);

	TaskReport findByUserDeviceAndTaskAndOwner(Long userDeviceId, Long taskId, Long ownerId);

	long deleteTaskReportAndRelated(long taskReportId);
}
