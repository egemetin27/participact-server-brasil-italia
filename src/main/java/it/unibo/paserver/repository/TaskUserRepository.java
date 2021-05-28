package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;

public interface TaskUserRepository {
	
	TaskUser findById(long id);

	TaskUser save(TaskUser task);

	Long getTaskUsersCount();
	
	List<TaskUser> getTaskUsersByOwner(long ownerId, TaskValutation currentTaskValutation);


	List<TaskUser> getTaskUser();
	
	List<TaskUser> getTaskUserByOwner(long ownerId);
	
	boolean deleteTask(long id);

	TaskUser getTaskUserByTaskId(long id);


}
