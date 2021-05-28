package it.unibo.paserver.service;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.repository.TaskRepository;
import it.unibo.paserver.repository.TaskUserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional(readOnly = true)
public class TaskUserServiceImpl implements TaskUserService {



@Autowired
TaskUserRepository taskUserRepository;
	
	
	@Override
	@Transactional(readOnly = false)
	public TaskUser valutateTaskUser(TaskUser taskUser, boolean decision) {
		if (decision)
			taskUser.setValutation(TaskValutation.APPROVED);
		else
			taskUser.setValutation(TaskValutation.REFUSED);
		TaskUser t = taskUserRepository.save(taskUser);
		return t;
	}

	@Override
	public TaskUser findById(long id) {
		return taskUserRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskUser save(TaskUser task) {
		return taskUserRepository.save(task);
	}

	@Override
	public Long getTaskUsersCount() {
		return taskUserRepository.getTaskUsersCount();
	}

	@Override
	public List<TaskUser> getTaskUsersByOwner(long ownerId,
			TaskValutation currentTaskValutation) {
		return taskUserRepository.getTaskUsersByOwner(ownerId, currentTaskValutation);
	}

	@Override
	public List<TaskUser> getTaskUser() {
		
		return taskUserRepository.getTaskUser();
	}
	
	

	@Override
	public List<TaskUser> getTaskUserByOwner(long ownerId) {
		return taskUserRepository.getTaskUserByOwner(ownerId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTask(long id) {
		return taskUserRepository.deleteTask(id);
	}

	@Override
	public TaskUser getTaskUserByTaskId(long id) {
		return taskUserRepository.getTaskUserByTaskId(id);
	}

}
