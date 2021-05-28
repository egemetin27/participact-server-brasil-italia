package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUserIncluded;
import it.unibo.paserver.domain.User;

@Component
public class TaskUserIncludedBuilder extends EntityBuilder<TaskUserIncluded> {
	@Override
	void initEntity() {
		entity = new TaskUserIncluded();

	}

	@Override
	TaskUserIncluded assembleEntity() {
		return entity;
	}

	public TaskUserIncludedBuilder setAll(Long id, Task taskId, User userId) {
		this.setId(id);
		this.setTaskId(taskId);
		this.setUserId(userId);
		this.setRemoved(false);
		return this;
	}

	public TaskUserIncludedBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public TaskUserIncludedBuilder setTaskId(Task taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public TaskUserIncludedBuilder setUserId(User userId) {
		entity.setUserId(userId);
		return this;
	}

	public TaskUserIncludedBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public TaskUserIncludedBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public TaskUserIncludedBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}