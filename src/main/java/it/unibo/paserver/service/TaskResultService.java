package it.unibo.paserver.service;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.TaskResult;

import java.util.Collection;
import java.util.List;

public interface TaskResultService {

	TaskResult findById(long id);

	TaskResult findByUserAndTask(long userId, long taskId);

	TaskResult addData(long taskId, long userId, Data data);

	TaskResult save(TaskResult taskResult);

	TaskResult getTaskResultByTaskReport(long taskReportId);

	TaskResult getTaskResultByTaskReport(long taskReportId, boolean initData);

	Long getTaskResultsCount();

	Long getTaskResultsCountByUser(long userId);

	Long getTaskResultsCountByTask(long taskId);

	List<TaskResult> getTaskResults();

	List<TaskResult> getTaskResultsByTask(long taskId);

	List<TaskResult> getTaskResultsByUser(long userId);

	boolean deleteTaskResult(long id);

	void updateTaskResult(long id);

	TaskResult addData(long taskId, long userId, Collection<? extends Data> data);

}
