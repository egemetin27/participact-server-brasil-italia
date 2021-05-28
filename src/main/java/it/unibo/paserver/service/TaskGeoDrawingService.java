package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.TaskGeoDrawing;

public interface TaskGeoDrawingService {
	TaskGeoDrawing saveOrUpdate(TaskGeoDrawing t);

	TaskGeoDrawing findByTaskIdType(long TaskId, boolean isNotification);

	boolean deleteByTaskIdType(long TaskId, boolean isNotification);

	List<TaskGeoDrawing> findAllByTaskIdType(long TaskId, boolean isNotification);
}