package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.service.ReputationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Dummy implementation, add or remove fixed quantity.
 * 
 * @author danielecampogiani
 *
 */
@Component
@Qualifier("ReputationStrategyTrivial")
public class ReputationStrategyTrivial implements ReputationStrategy {

	private static final int ACCEPTED_VALUE = 5;
	private static final int REJECTED_VALUE = -5;
	private static final int COMPLETED_WITH_SUCCESS_VALUE = 10;
	private static final int COMPLETED_WITH_FAILURE_VALUE = -10;

	@Autowired
	ReputationService reputationService;

	@Override
	public Reputation updateReputation(Reputation reputation,
			TaskState newTaskState, boolean persist) {

		int newValue = reputation.getValue();

		if (newTaskState == TaskState.ACCEPTED) {

			newValue += ACCEPTED_VALUE;

		}

		else if (newTaskState == TaskState.REJECTED) {

			newValue += REJECTED_VALUE;

		}

		else if (newTaskState == TaskState.COMPLETED_WITH_SUCCESS) {

			newValue += COMPLETED_WITH_SUCCESS_VALUE;

		}

		else if (newTaskState == TaskState.COMPLETED_WITH_FAILURE) {

			newValue += COMPLETED_WITH_FAILURE_VALUE;

		}

		if (newValue < Reputation.REPUTATION_MIN)
			newValue = Reputation.REPUTATION_MIN;
		else if (newValue > Reputation.REPUTATION_MAX)
			newValue = Reputation.REPUTATION_MAX;

		if (persist) {
			reputation.setValue(newValue);
			return reputationService.save(reputation);
		} else {
			Reputation result = new Reputation();
			result.setActionType(reputation.getActionType());
			result.setUser(reputation.getUser());
			result.setValue(newValue);
			return result;
		}

	}

}
