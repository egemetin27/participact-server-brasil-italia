package it.unibo.paserver.service;

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.PointsRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of PointsService using PointsRepository.
 * 
 * @author danielecampogiani
 * @see PointsService
 * @see PointsRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class PointsServicesImpl implements PointsService {

	@Autowired
	PointsRepository pointsRepository;
	@Autowired
	UserService userService;

	@Override
	@Transactional(readOnly = false)
	public Points save(Points points) {
		return pointsRepository.save(points);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Points> getPointsByUserAndDates(long userId, DateTime from, DateTime to) {
		return pointsRepository.getPointsByUserAndDates(userId, from, to);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Points> getPoints() {
		return pointsRepository.getPoints();
	}

	@Override
	@Transactional(readOnly = true)
	public Score getScoreByUserAndDates(long userId, DateTime from, DateTime to) {
		List<Points> points = getPointsByUserAndDates(userId, from, to);
		int scoreValue = 0;
		for (Points currentPoints : points)
			scoreValue += currentPoints.getValue();
		Score result = new Score();
		result.setUser(userService.getUser(userId));
		result.setValue(scoreValue);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getPointsCount() {
		return pointsRepository.getPointsCount();
	}

	@Override
	@Transactional(readOnly = false)
	public Points create(User user, Task task, DateTime dateTime, Integer value, PointsType type) {
		return pointsRepository.create(user, task, dateTime, value, type);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Points> getPointsByUserAndTask(long userId, long taskId) {
		return pointsRepository.getPointsByUserAndTask(userId, taskId);
	}

	@Override
	@Transactional(readOnly = true)
	public Points getPointsByUserAndTaskAndType(long userid, long taskId, PointsType type) {
		return pointsRepository.getPointsByUserAndTaskAndType(userid, taskId, type);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Points> getPointsByUserAndType(long userId, PointsType type) {
		return pointsRepository.getPointsByUserAndType(userId, type);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Points> getPointsByTaskAndType(long taskId, PointsType type) {
		return pointsRepository.getPointsByTaskAndType(taskId, type);
	}

}
