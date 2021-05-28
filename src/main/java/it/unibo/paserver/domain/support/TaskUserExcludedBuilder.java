package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.TaskUserExcluded;

@Component
public class TaskUserExcludedBuilder extends EntityBuilder<TaskUserExcluded> {
	@Override
	void initEntity() {
		entity = new TaskUserExcluded();

	}

	@Override
	TaskUserExcluded assembleEntity() {
		return entity;
	}

	public TaskUserExcludedBuilder setAll(Long id, Long taskId, Long userId, Long parentId, boolean isSelectAll) {
		this.setId(id);
		this.setTaskId(taskId);
		this.setUserId(userId);
		this.setParentId(parentId);
		this.setSelectAll(isSelectAll);
		this.setRemoved(false);
		return this;
	}

	public TaskUserExcludedBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public TaskUserExcludedBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public TaskUserExcludedBuilder setUserId(Long userId) {
		entity.setUserId(userId);
		return this;
	}

	public TaskUserExcludedBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public TaskUserExcludedBuilder setSelectAll(boolean isSelectAll) {
		entity.setSelectAll(isSelectAll);
		return this;
	}

	public TaskUserExcludedBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public TaskUserExcludedBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public TaskUserExcludedBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}