package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;

import java.util.List;

/**
 * Implementations of this interface decide how to assign points to users when
 * they successfully complete a task.
 * 
 * @author danielecampogiani
 *
 */
public interface PointsStrategy {

	/**
	 * Returns Points assigned to User who completed with success Task.
	 * 
	 * @param user
	 *            who completed the Task
	 * @param task
	 *            the task completed with success
	 * @param type why user has gained points
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return Points assigned to User who completed with success Task
	 * @see User
	 * @see Task
	 * @see PointsType
	 */
	public Points computePoints(User user, Task task, PointsType type, boolean persist);

	/**
	 * Returns Points assigned to User who completed with success Task using
	 * given Reputations.
	 * 
	 * @param user
	 *            who completed the Task
	 * @param task
	 *            the task completed with success
	 * @param reputations
	 * @param type why user has gained points
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return Points assigned to User who completed with success Task using
	 *         given Reputations
	 * @see User
	 * @see Task
	 * @see Reputation
	 * @see PointsType
	 */
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist);

	/**
	 * Returns the strategy id.
	 * 
	 * @return the strategy id
	 */
	public int getId();

	/**
	 * Returns the strategy name.
	 * 
	 * @return the strategy name
	 */
	public String getName();
}
