package it.unibo.paserver.repository;

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;

import java.util.List;

/**
 * Repository for PointsStrategyForTask entities.
 * 
 * @author danielecampogiani
 * @see PointsStrategyForTask
 *
 */
public interface PointsStrategyForTaskRepository {

	/**
	 * Save given PointsStrategyForTask.
	 * 
	 * @param pointsStrategyForTask
	 *            PointsStrategyForTask to save
	 * @return saved PointsStrategyForTask
	 * @see PointsStrategyForTask
	 */
	PointsStrategyForTask save(PointsStrategyForTask pointsStrategyForTask);

	/**
	 * Create PointsStrategyForTask with given arguments.
	 * 
	 * @param task
	 *            PointsStrategyForTask Task
	 * @param strategyId
	 *            PointsStrategyForTask strategy id
	 * @return the new PointsStrategyForTask
	 * @see PointsStrategyForTask
	 * @see Task
	 */
	PointsStrategyForTask create(Task task, Integer strategyId);

	/**
	 * Returns PointsStrategyForTask for given Task.
	 * 
	 * @param taskId
	 *            task id
	 * @return PointsStrategyForTask for given Task
	 * @see PointsStrategyForTask
	 * @see Task
	 */
	PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId);

	/**
	 * Returns all PointsStrategyForTasks
	 * 
	 * @return all PointsStrategyForTask
	 * @see PointsStrategyForTask
	 */
	List<PointsStrategyForTask> getPointsStrategyForTasks();

	/**
	 * Return the amount of PointsStrategyForTask saved in database.
	 * 
	 * @return the amount of PointsStrategyForTask saved in database
	 */
	Long getPointsStrategyForTaskCount();

}
