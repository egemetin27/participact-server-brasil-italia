package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUserIncluded;

public interface TaskUserIncludedService {

	TaskUserIncluded saveOrUpdate(TaskUserIncluded t);

	TaskUserIncluded findById(Long id);

	boolean delete(Long taskId, Long userId);

	boolean removed(Long id);

	boolean isIncluded(Long taskId, Long userId);

	List<Task> fetchAllByUser(Long userId, PageRequest pageable);
}