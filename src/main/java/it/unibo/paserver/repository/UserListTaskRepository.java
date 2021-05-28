package it.unibo.paserver.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.AudienceSelector;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserListTask;

public interface UserListTaskRepository {

	public UserListTask saveOrUpdate(UserListTask t);

	public List<UserListTask> findAll();

	public UserListTask find(Long id);

	public UserListTask findUserListTask(Long taskId);

	public Task findUserTask(Long taskId);

	public List<Task> fetchAllAndPublic(DateTime updateDate, PageRequest pageable);
	
	List<UserListTask> fetchAllSelector(AudienceSelector audienceSelector, DateTime updateDate, PageRequest pageable);

	List<Task> fetchAllAndClosed(Long userId, DateTime updateDate,  PageRequest pageable);

	public Task findLastUpdate();

	public Task findLastUpdateRemoved();

	public List<Task> fetchAllAndPublicPublish(DateTime updateDate, PageRequest pagerequest);
}