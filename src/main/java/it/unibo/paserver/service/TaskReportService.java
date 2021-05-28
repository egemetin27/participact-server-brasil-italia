package it.unibo.paserver.service;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.UserDevice;
import it.unibo.paserver.domain.support.Pipeline;

public interface TaskReportService {

	TaskReport findById(long id);
	
	TaskReport findByUserAndTask(long userId, long taskId);
	
	Long getTaskReportsCountByUserAndTask(long userId, long taskId); 
	
	TaskReport findByUserDeviceAndTask(long userDeviceId, long taskId); 

	TaskReport save(TaskReport taskReport);

	Long getTaskReportsCount();

	Long getTaskReportsCountByUser(long userId);
	
	Long getTaskReportsCountByUserDevice(long userDeviceId);

	Long getTaskReportsCountByTask(long taskId);

	List<TaskReport> getTaskReports();

	List<TaskReport> getTaskReportsByUser(long userId);
	
	List<TaskReport> getTaskReportsByUserDevice(long userDeviceId);

	List<TaskReport> getTaskReportsByTask(long taskId);

	Set<String> getAssignedOfficialEmailByTask(long taskId);

	boolean deleteTaskReport(long id);

	List<TaskReport> getTaskReportsForData(long userId, Pipeline.Type dataType, DateTime sampletimestamp);

	List<TaskReport> getTaskReportsDeviceForData(long userDeviceId, Pipeline.Type dataType, DateTime sampleTimestamp);
	
	TaskReport getTaskReportAccptedOrRejected(Long taskId, Long userId);	
	
	List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now);

	void addDataToTaskReports(Long userId, Pipeline.Type dataType, List<Data> data);

	TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId, Long ownerId);
	
	TaskReport findByUserDeviceAndTaskAndOwner(Long userDeviceId, Long taskId, Long ownerId);

	List<TaskReport> getTaskReportsByTask(Long id, TaskState state);

	Long getTaskReportsCountByUserAndTask(Long userId, Long taskId);

	List<Object[]> search(long taskId, String officialEmail, TaskState taskState, PageRequest pageable);
	List<Object[]> search(long taskId, String search, TaskState taskState, PageRequest pageable, String orderByColumn);
	
	Long searchTotal(long id, ListMultimap<String, Object> params);

	Long searchTotal(long id, String search, TaskState taskState);

	List<Object[]> getTaskReportsByTaskUniqueUser(Long taskId);
	
	void deleteOtherUserDeviceTaskReports(UserDevice userDevice, Long taskId);
}
