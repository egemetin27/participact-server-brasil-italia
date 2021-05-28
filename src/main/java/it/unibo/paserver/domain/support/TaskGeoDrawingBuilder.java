package it.unibo.paserver.domain.support;

import java.util.Set;

import org.joda.time.DateTime;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskGeoDrawing;
import it.unibo.paserver.domain.TaskGeoSpherical;

public class TaskGeoDrawingBuilder extends EntityBuilder<TaskGeoDrawing> {
	@Override
	void initEntity() {

		entity = new TaskGeoDrawing();

	}

	@Override
	TaskGeoDrawing assembleEntity() {

		return entity;
	}

	/**
	 * Setter/Getter
	 */
	
	public TaskGeoDrawingBuilder setAll(long id, Double radius, String type, Set<TaskGeoSpherical> spherical,
			Set<TaskGeoSpherical> center, Long taskId, boolean isNotification, boolean isActivation) {
		this.setId(id);
		this.setRadius(radius);
		this.setType(type);
		this.setSpherical(spherical);
		this.setCenter(center);
		this.setTaskId(taskId);
		this.setNotification(isNotification);
		this.setActivation(isActivation);
		this.setUpdateDate(new DateTime());
		if (id == 0) {
			this.setCreationDate(new DateTime());
		}
		this.setRemoved(false);
		return this;
	}
	
	public TaskGeoDrawingBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public TaskGeoDrawingBuilder setRadius(Double radius) {
		entity.setRadius(radius);
		return this;
	}

	public TaskGeoDrawingBuilder setType(String type) {
		entity.setType(type);
		return this;
	}

	public TaskGeoDrawingBuilder setSpherical(Set<TaskGeoSpherical> spherical) {
		entity.setSpherical(spherical);
		return this;
	}

	public TaskGeoDrawingBuilder setCenter(Set<TaskGeoSpherical> center) {
		entity.setCenter(center);
		return this;
	}

	public TaskGeoDrawingBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public TaskGeoDrawingBuilder setNotification(boolean isNotification) {
		entity.setNotification(isNotification);
		return this;
	}

	public TaskGeoDrawingBuilder setActivation(boolean isActivation) {
		entity.setActivation(isActivation);
		return this;
	}

	public TaskGeoDrawingBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public TaskGeoDrawingBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public TaskGeoDrawingBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}