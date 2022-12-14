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
 * completed. If multiples reputations are given, this implementation uses the
 * average value.
 * 
 * @author danielecampogiani
 *
 */
@Component
@Qualifier("PointsStrategyByAverageLevel")
public class PointsStrategyByAverageLevel implements PointsStrategy {

	@Autowired
	private ReputationService reputationService;
	@Autowired
	private PointsService pointsService;

	private int id = 1;
	private String name = "By Average Level";

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

		int low = 0;
		int medium_low = 0;
		int medium_high = 0;
		int high = 0;

		for (Reputation currentReputation : reputations) {
			Level currentLevel = Level
					.getLevelFromReputation(currentReputation);
			LevelRank currentRank = currentLevel.getLevelRank();
			if (currentRank == LevelRank.LOW)
				low++;
			else if (currentRank == LevelRank.MEDIUM_LOW)
				medium_low++;
			else if (currentRank == LevelRank.MEDIUM_HIGH)
				medium_high++;
			else if (currentRank == LevelRank.HIGH)
				high++;

		}

		// approccio che a parit?? sceglie il livello pi?? basso
		LevelRank choosenRank = LevelRank.LOW;
		int currentMax = 0;

		if (low > currentMax)
			currentMax = low;
		if (medium_low > currentMax) {
			currentMax = medium_low;
			choosenRank = LevelRank.MEDIUM_LOW;
		}
		if (medium_high > currentMax) {
			currentMax = medium_high;
			choosenRank = LevelRank.MEDIUM_HIGH;
		}
		if (high > currentMax) {
			currentMax = high;
			choosenRank = LevelRank.HIGH;
		}

		Integer value = 60;
		if (choosenRank == LevelRank.MEDIUM_LOW)
			value = 80;
		else if (choosenRank == LevelRank.MEDIUM_HIGH)
			value = 100;
		else if (choosenRank == LevelRank.HIGH)
			value = 120;

		if (persist)
			return pointsService.create(user, task, now, value, type);

		else {
			Points result = new Points();
			result.setDate(now);
			result.setTask(task);
			result.setUser(user);
			result.setValue(value);
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
		PointsStrategyByAverageLevel other = (PointsStrategyByAverageLevel) obj;
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
