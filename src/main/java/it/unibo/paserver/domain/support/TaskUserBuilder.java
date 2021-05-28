package it.unibo.paserver.domain.support;

import java.util.Set;

import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.User;

@Component
public class TaskUserBuilder extends EntityBuilder<TaskUser> {

	@Override
	void initEntity() {
		entity = new TaskUser();

	}

	public TaskUserBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskUserBuilder setTask(Task task)
	{
		entity.setTask(task);
		return this;
	}

	public TaskUserBuilder setApproved(TaskValutation valutation) {
		entity.setValutation(valutation);
		return this;
	}


	public TaskUserBuilder setOwner(User owner) {
		entity.setOwner(owner);
		return this;
	}
	
	public TaskUserBuilder setUsersToAssign(Set<User> users){
		entity.setUsersToAssign(users);
		return this;
	}

	@Override
	TaskUser assembleEntity() {
		return entity;
	}

}
