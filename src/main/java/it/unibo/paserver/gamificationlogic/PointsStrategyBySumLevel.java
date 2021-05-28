package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.Level;
import it.unibo.paserver.domain.Level.LevelRank;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.ReputationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This implementation uses User's Level for each ActionType in the Task
 * completed. If multiples reputations are given, this implementation sums the
 * values.
 * 
 * @author danielecampogiani
 *
 */
@Component
@Qualifier("PointsStrategyBySumLevel")
public class PointsStrategyBySumLevel implements PointsStrategy {

	@Autowired
	private ReputationService reputationService;
	@Autowired
	private PointsService pointsService;

	private int id = 3;
	private String name = "By Sum Level";

	@Override
	public Points computePoints(User user, Task task, PointsType type, boolean persist) {
		List<Reputation> reputations = new ArrayList<Reputation>();
		Set<Action> actions = task.getActions();
		Long userId = user.getId();
		for (Action currentAction : actions) {
			reputations.add(reputationService.getReputationByUserAndActionType(
					userId, currentAction.getType()));
		}

		return computePoints(user, task, reputations, type, persist);
	}

	@Override
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist) {
		DateTime now = new DateTime();

		int tempSum = 0;

		for (Reputation currentReputation : reputations) {
			Level currentLevel = Level
					.getLevelFromReputation(currentReputation);
			LevelRank currentRank = currentLevel.getLevelRank();
			if (currentRank == LevelRank.LOW)
				tempSum += 60;
			else if (currentRank == LevelRank.MEDIUM_LOW)
				tempSum += 80;
			else if (currentRank == LevelRank.MEDIUM_HIGH)
				tempSum += 100;
			else if (currentRank == LevelRank.HIGH)
				tempSum += 120;

		}

		if (persist)
			return pointsService.create(user, task, now, tempSum, type);

		else {
			Points result = new Points();
			result.setDate(now);
			result.setTask(task);
			result.setUser(user);
			result.setValue(tempSum);
			result.setType(type);
			return result;
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointsStrategyBySumLevel other = (PointsStrategyBySumLevel) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
