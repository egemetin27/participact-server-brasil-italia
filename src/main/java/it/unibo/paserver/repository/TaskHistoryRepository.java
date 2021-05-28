package it.unibo.paserver.repository;

import it.unibo.paserver.domain.TaskHistory;

import java.util.List;

public interface TaskHistoryRepository {

	TaskHistory findById(long id);

	TaskHistory save(TaskHistory taskHistory);

	Long getTaskHistoriesCount();

	Long getTaskHistoriesCount(long taskReportId);

	Long getTaskHistoriesCount(long userId, long taskId);

	List<TaskHistory> getTaskHistories();

	List<TaskHistory> getTaskHistories(long taskReportId);

	List<TaskHistory> getTaskHistories(long userId, long taskId);

	boolean deleteTaskHistory(long id);

	TaskHistory saveOrUpdate(TaskHistory h);
}
