package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUserIncluded;

public interface TaskUserIncludedRepository {
	TaskUserIncluded saveOrUpdate(TaskUserIncluded i);

	TaskUserIncluded findById(long id);

	boolean delete(long id, long userId);

	boolean removed(long id);

	boolean isIncluded(long taskId, long userId);

	List<Task> fetchAllByUser(Long userId, PageRequest pageable);
}