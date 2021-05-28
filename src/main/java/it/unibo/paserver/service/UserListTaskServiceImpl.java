package it.unibo.paserver.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.AudienceSelector;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserListTask;
import it.unibo.paserver.repository.UserListTaskRepository;

@Service
@Transactional(readOnly = true)
public class UserListTaskServiceImpl implements UserListTaskService {
	@Autowired
	UserListTaskRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserListTask saveOrUpdate(UserListTask i) {
		
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserListTask> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserListTask find(Long id) {
		
		return repos.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public UserListTask findUserListTask(Long taskId) {
		
		return repos.findUserListTask(taskId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Task findUserTask(Long taskId) {
		
		return repos.findUserTask(taskId);
	}
	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Task findLastUpdate() {
		return repos.findLastUpdate();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Task findLastUpdateRemoved() {
		
		return repos.findLastUpdateRemoved();
	}


	@Override
	@Transactional(readOnly = false)
	public UserListTask addUserListTask(UserListTask userListTask) {
		
		return repos.saveOrUpdate(userListTask);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> fetchAllAndPublic(DateTime updateDate, PageRequest pageable) {
		
		return repos.fetchAllAndPublic(updateDate, pageable);
	}
	
	@Override
	public List<Task> fetchAllAndPublicPublish(DateTime updateDate, PageRequest pagerequest) {
		
		return repos.fetchAllAndPublicPublish(updateDate, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserListTask> fetchAllSelector(AudienceSelector audienceSelector, DateTime updateDate, PageRequest pageable) {
		
		return repos.fetchAllSelector(audienceSelector, updateDate, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> fetchAllAndClosed(Long userId, DateTime updateDate, PageRequest pageable) {
		
		return repos.fetchAllAndClosed(userId, updateDate, pageable);
	}
}