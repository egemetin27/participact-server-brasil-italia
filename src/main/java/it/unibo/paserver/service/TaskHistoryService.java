package it.unibo.paserver.service;

import it.unibo.paserver.domain.TaskHistory;

import java.util.List;

public interface TaskHistoryService {

	TaskHistory findById(long id);

	TaskHistory save(TaskHistory taskHistory);
	
	TaskHistory saveOrUpdate(TaskHistory h);

	Long getTaskHistoriesCount();

	Long getTaskHistoriesCount(long taskReportId);

	Long getTaskHistoriesCount(long userId, long taskId);

	List<TaskHistory> getTaskHistories();

	List<TaskHistory> getTaskHistories(long taskReportId);

	List<TaskHistory> getTaskHistories(long userId, long taskId);

	boolean deleteTaskHistory(long id);

}
