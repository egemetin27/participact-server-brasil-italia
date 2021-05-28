package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.TaskUserExcluded;

public interface TaskUserExcludedService {
	// Salva ou atualiza
	TaskUserExcluded saveOrUpdate(TaskUserExcluded te);

	TaskUserExcluded findById(long id);
	
	boolean delete(long taskId, long userId);
	
	boolean removed(long id);

	List<Object[]> fetchAll(long taskId, long parentId);

	boolean isExcluded(long taskId, long userId);
}