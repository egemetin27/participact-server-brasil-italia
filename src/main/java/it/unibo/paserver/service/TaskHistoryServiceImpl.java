package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.repository.TaskHistoryRepository;

@Service
@Transactional(readOnly = true)
public class TaskHistoryServiceImpl implements TaskHistoryService {

	@Autowired
	TaskHistoryRepository taskHistoryRepository;

	@Override
	public TaskHistory findById(long id) {
		return taskHistoryRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskHistory save(TaskHistory taskHistory) {
		return taskHistoryRepository.save(taskHistory);
	}
	
	@Override
	@Transactional(readOnly = false)
	public TaskHistory saveOrUpdate(TaskHistory h) {
		return taskHistoryRepository.saveOrUpdate(h);
	}	

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTaskHistory(long id) {
		return taskHistoryRepository.deleteTaskHistory(id);
	}

	@Override
	public List<TaskHistory> getTaskHistories() {
		return taskHistoryRepository.getTaskHistories();
	}

	@Override
	public Long getTaskHistoriesCount() {
		return taskHistoryRepository.getTaskHistoriesCount();
	}

	@Override
	public Long getTaskHistoriesCount(long taskReportId) {
		return taskHistoryRepository.getTaskHistoriesCount(taskReportId);
	}

	@Override
	public Long getTaskHistoriesCount(long userId, long taskId) {
		return taskHistoryRepository.getTaskHistoriesCount(userId, taskId);
	}

	@Override
	public List<TaskHistory> getTaskHistories(long taskReportId) {
		return taskHistoryRepository.getTaskHistories(taskReportId);
	}

	@Override
	public List<TaskHistory> getTaskHistories(long userId, long taskId) {
		return taskHistoryRepository.getTaskHistories(userId, taskId);
	}
}