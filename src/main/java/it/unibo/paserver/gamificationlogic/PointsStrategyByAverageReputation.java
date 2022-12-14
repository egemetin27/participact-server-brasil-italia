package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Action;
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
 * This implementation span the range of Reputation in the interval 60-120 and
 * use this value as Points. If multiples reputations are given, this
 * implementation uses the average value.
 * 
 * @author danielecampogiani
 *
 */
@Component
@Qualifier("PointsStrategyByAverageReputation")
public class PointsStrategyByAverageReputation implements PointsStrategy {

	@Autowired
	private ReputationService reputationService;
	@Autowired
	private PointsService pointsService;

	private int id = 2;
	private String name = "By Average Reputation";

	@Override
	public Points computePoints(User user, Task task, PointsType type, boolean persist) {
		Long userId = user.getId();
		Set<Action> actions = task.getActions();
		List<Reputation> reputations = new ArrayList<Reputation>();
		for (Action currentAction : actions) {
			reputations.add(reputationService.getReputationByUserAndActionType(
					userId, currentAction.getType()));
		}

		return computePoints(user, task, reputations, type, persist);

	}

	@Override
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist) {

		if (reputations.size() == 0) {

			Points result = new Points();
			result.setUser(user);
			result.setDate(new DateTime());
			result.setTask(task);
			result.setValue(0);

			return result;

		}

		DateTime now = new DateTime();

		int tempSum = 0;

		for (Reputation currentReputation : reputations) {
			tempSum += currentReputation.getValue();
		}

		int mean = tempSum / reputations.size();

		// double slope = (120 - 60) / (100 - 0); fixed for efficiency
		double slope = 0.6;
		// double output = output_start + slope * (input - input_start) //
		// fixed for efficiency
		double output = 60 + slope * (mean);

		Integer value = new Integer((int) output);

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
		PointsStrategyByAverageReputation other = (PointsStrategyByAverageReputation) obj;
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
