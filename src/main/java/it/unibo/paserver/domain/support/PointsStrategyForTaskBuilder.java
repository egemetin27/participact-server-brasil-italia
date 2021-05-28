package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;

/**
 * Build a PointsStrategyForTask object using builder pattern.
 * 
 * @author danielecampogiani
 * @see PointsStrategyForTask
 * 
 */
public class PointsStrategyForTaskBuilder extends
		EntityBuilder<PointsStrategyForTask> {

	@Override
	void initEntity() {
		entity = new PointsStrategyForTask();

	}

	@Override
	PointsStrategyForTask assembleEntity() {
		return entity;
	}

	/**
	 * Set the PointsStrategyForTask id.
	 * 
	 * @param id
	 *            the PointsStrategyForTask id
	 * @return this builder
	 */
	public PointsStrategyForTaskBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * Sets the PointsStrategyForTask Task.
	 * 
	 * @param task
	 *            the PointsStrategyForTask Task
	 * @return this builder
	 * @see Task
	 */
	public PointsStrategyForTaskBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	/**
	 * Sets the PointsStrategyForTask strategy id.
	 * 
	 * @param strategyId
	 *            the PointsStrategyForTask strategy id
	 * @return this builder
	 */
	public PointsStrategyForTaskBuilder setStrategyId(Integer strategyId) {
		entity.setStrategyId(strategyId);
		return this;
	}

}
