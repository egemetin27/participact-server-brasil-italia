package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.Task;

/**
 * Build a BadgeTask object using the builder pattern.
 * 
 * @author danielecampogiani
 * @see BadgeTask
 *
 */
public class BadgeTaskBuilder extends EntityBuilder<BadgeTask> {

	@Override
	void initEntity() {
		entity = new BadgeTask();

	}

	@Override
	BadgeTask assembleEntity() {
		return entity;
	}

	/**
	 * Sets the BadgeTask title.
	 * 
	 * @param title the BadgeTask title
	 * @return this builder
	 */
	public BadgeTaskBuilder setTitle(String title) {
		entity.setTitle(title);
		return this;
	}

	/**
	 * Sets the BadgeTask description.
	 * 
	 * @param description the BadgeTask description
	 * @return this builder
	 */
	public BadgeTaskBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	/**
	 * Sets the BadgeTask Task.
	 * 
	 * @param task the BadgeTask Task
	 * @return this builder
	 * @see Task
	 */
	public BadgeTaskBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	/**
	 * Sets the BadgeTask id.
	 * 
	 * @param id the BadgeTask id
	 * @return this builder
	 */
	public BadgeTaskBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

}
