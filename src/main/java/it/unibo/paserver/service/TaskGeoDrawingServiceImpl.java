package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.TaskGeoDrawing;
import it.unibo.paserver.repository.TaskGeoDrawingRepository;

@Service
@Transactional(readOnly = true)
public class TaskGeoDrawingServiceImpl implements TaskGeoDrawingService {
	@Autowired
	TaskGeoDrawingRepository repos;
	
	@Override
	@Transactional(readOnly = false)
	public TaskGeoDrawing saveOrUpdate(TaskGeoDrawing t) {
		
		return repos.saveOrUpdate(t);
	}

	@Override
	@Transactional(readOnly = true)
	public TaskGeoDrawing findByTaskIdType(long TaskId, boolean isNotification) {
		
		return repos.findByTaskIdType(TaskId, isNotification);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteByTaskIdType(long TaskId, boolean isNotification) {
		
		return repos.deleteByTaskIdType(TaskId, isNotification);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<TaskGeoDrawing> findAllByTaskIdType(long TaskId, boolean isNotification) {
		
		return repos.findAllByTaskIdType(TaskId, isNotification);
	}
}
