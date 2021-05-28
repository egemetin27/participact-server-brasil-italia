package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.TaskUserExcluded;
import it.unibo.paserver.repository.TaskUserExcludedRepository;

@Service
@Transactional(readOnly = true)
public class TaskUserExcludedServiceImpl implements TaskUserExcludedService {
	@Autowired
	TaskUserExcludedRepository repos;

	@Override
	@Transactional(readOnly = false)
	public TaskUserExcluded saveOrUpdate(TaskUserExcluded te) {
		return repos.saveOrUpdate(te);
	}

	@Override
	@Transactional(readOnly = true)
	public TaskUserExcluded findById(long id) {
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long taskId, long userId) {
		
		return repos.delete(taskId, userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> fetchAll(long taskId, long parentId) {
		return repos.fetchAll(taskId, parentId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExcluded(long taskId, long userId) {
		return repos.isExcluded(taskId, userId);
	}
}
