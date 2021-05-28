package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.TaskUserExcluded;

public interface TaskUserExcludedRepository {
	TaskUserExcluded saveOrUpdate(TaskUserExcluded e);

	TaskUserExcluded findById(long id);
	
	List<Object[]> fetchAll(long taskId, long parentId);

	boolean delete(long id, long userId);

	boolean removed(long id);

	boolean isExcluded(long taskId, long userId);
}