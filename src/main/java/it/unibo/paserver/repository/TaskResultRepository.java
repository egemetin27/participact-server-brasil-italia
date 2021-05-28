package it.unibo.paserver.repository;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.TaskResult;

import java.util.Collection;
import java.util.List;

public interface TaskResultRepository {

	TaskResult findById(long id);

	TaskResult findByUserAndTask(long userId, long taskId);

	TaskResult save(TaskResult taskResult);

	Long getTaskResultsCount();

	Long getTaskResultsCountByUser(long userId);

	Long getTaskResultsCountByTask(long taskId);

	List<TaskResult> getTaskResults();

	List<TaskResult> getTaskResultsByTask(long taskId);

	List<TaskResult> getTaskResultsByUser(long userId);

	boolean deleteTaskResult(long id);

	public void flush();

	public void clear();

	TaskResult addData(long taskId, long userId, Data data);

	TaskResult updateTaskResult(TaskResult taskResult, boolean force);

	TaskResult addData(long taskId, long userId, Collection<? extends Data> data);
}
