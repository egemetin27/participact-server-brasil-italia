package it.unibo.paserver.service;

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.repository.PointsStrategyForTaskRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of PointsStrategyForTaskService using
 * PointsStrategyForTaskRepository
 * 
 * @author danielecampogiani
 * @see PointsStrategyForTaskService
 * @see PointsStrategyForTaskRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class PointsStrategyForTaskServiceImpl implements
		PointsStrategyForTaskService {

	@Autowired
	PointsStrategyForTaskRepository pointsStrategyForTaskRepository;

	@Override
	@Transactional(readOnly = false)
	public PointsStrategyForTask save(
			PointsStrategyForTask pointsStrategyForTask) {
		return pointsStrategyForTaskRepository.save(pointsStrategyForTask);
	}

	@Override
	@Transactional(readOnly = false)
	public PointsStrategyForTask create(Task task, Integer strategyId) {
		return pointsStrategyForTaskRepository.create(task, strategyId);
	}

	@Override
	public PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId) {
		return pointsStrategyForTaskRepository
				.getPointsStrategyForTaskByTask(taskId);
	}

	@Override
	public List<PointsStrategyForTask> getPointsStrategyForTasks() {
		return pointsStrategyForTaskRepository.getPointsStrategyForTasks();
	}

	@Override
	public Long getPointsStrategyForTaskCount() {
		return pointsStrategyForTaskRepository.getPointsStrategyForTaskCount();
	}

}
