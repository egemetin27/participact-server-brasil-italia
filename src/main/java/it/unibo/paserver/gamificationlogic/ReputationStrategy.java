package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.TaskState;

/**
 * Implementations of this interface decide how to update reputation of users
 * when they interact with a Task
 * 
 * @author danielecampogiani
 *
 */
public interface ReputationStrategy {

	/**
	 * Return the new Reputation when the user interact with a Task
	 * 
	 * @param reputation
	 *            current Reputation
	 * @param newTaskState
	 *            new Task State
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return the new Reputation
	 * @see Reputation
	 * @see TaskState
	 */
	public Reputation updateReputation(Reputation reputation,
			TaskState newTaskState, boolean persist);

}
