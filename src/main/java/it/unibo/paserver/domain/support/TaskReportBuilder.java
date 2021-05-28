package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.User;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TaskReportBuilder extends EntityBuilder<TaskReport> {

	@Override
	void initEntity() {
		entity = new TaskReport();
	}

	public TaskReportBuilder setAcceptedDateTime(DateTime acceptedDateTime) {
		entity.setAcceptedDateTime(acceptedDateTime);
		return this;
	}

	public TaskReportBuilder setCurrentState(TaskState currentState) {
		entity.setCurrentState(currentState);
		return this;
	}

	public TaskReportBuilder setExpirationDateTime(DateTime expirationDateTime) {
		entity.setExpirationDateTime(expirationDateTime);
		return this;
	}

	public TaskReportBuilder setHistory(List<TaskHistory> history) {
		entity.setHistory(history);
		return this;
	}

	public TaskReportBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskReportBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	public TaskReportBuilder setTaskResult(TaskResult taskResult) {
		entity.setTaskResult(taskResult);
		return this;
	}

	public TaskReportBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	@Override
	TaskReport assembleEntity() {
		return entity;
	}

}
