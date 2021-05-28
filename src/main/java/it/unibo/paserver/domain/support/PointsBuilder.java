package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;

import org.joda.time.DateTime;

/**
 * Build a Points object using the build pattern.
 * 
 * @author danielecampogiani
 * @see Points
 *
 */
public class PointsBuilder extends EntityBuilder<Points> {

	@Override
	void initEntity() {
		entity = new Points();

	}

	@Override
	Points assembleEntity() {
		return entity;
	}

	/**
	 * Sets the Points User.
	 * 
	 * @param user the Points User
	 * @return this builder
	 * @see User
	 */
	public PointsBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * Sets the Points Task.
	 * 
	 * @param task the Points Task
	 * @return this builder
	 * @see Task
	 */
	public PointsBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	/**
	 * Sets the Points time stamp.
	 * 
	 * @param date the Points time stamp
	 * @return this builder
	 * @see DateTime
	 */
	public PointsBuilder setDate(DateTime date) {
		entity.setDate(date);
		return this;
	}

	/**
	 * Sets the Points value.
	 * 
	 * @param value the Points value
	 * @return this builder
	 */
	public PointsBuilder setValue(Integer value) {
		entity.setValue(value);
		return this;
	}

	/**
	 * Sets the Points id.
	 * 
	 * @param id the Points id
	 * @return this builder
	 */
	public PointsBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
	
	/**
	 * Sets the Points type.
	 * 
	 * @param type Points type
	 * @return this builder
	 * @see PointsType
	 */
	public PointsBuilder setType(PointsType type) {
		entity.setType(type);
		return this;
	}
}
