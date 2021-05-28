package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.TaskGeoDrawing;

public interface TaskGeoDrawingRepository {
	TaskGeoDrawing saveOrUpdate(TaskGeoDrawing t);

	TaskGeoDrawing findByTaskIdType(long TaskId, boolean isNotification);

	boolean deleteByTaskIdType(long TaskId, boolean isNotification);

	List<TaskGeoDrawing> findAllByTaskIdType(long taskId, boolean isNotification);
}
