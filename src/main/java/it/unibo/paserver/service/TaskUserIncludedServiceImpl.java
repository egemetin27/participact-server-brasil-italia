package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUserIncluded;
import it.unibo.paserver.repository.TaskUserIncludedRepository;

@Service
@Transactional(readOnly = true)
public class TaskUserIncludedServiceImpl implements TaskUserIncludedService {
	@Autowired
	TaskUserIncludedRepository repos;

	@Override
	@Transactional(readOnly = false)
	public TaskUserIncluded saveOrUpdate(TaskUserIncluded te) {
		return repos.saveOrUpdate(te);
	}

	@Override
	@Transactional(readOnly = true)
	public TaskUserIncluded findById(Long id) {
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(Long id) {
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(Long taskId, Long userId) {
		
		return repos.delete(taskId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isIncluded(Long taskId, Long userId) {
		return repos.isIncluded(taskId, userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Task> fetchAllByUser(Long userId, PageRequest pagerequest) {
		return repos.fetchAllByUser(userId, pagerequest);
	}
}
