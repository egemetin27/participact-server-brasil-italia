package it.unibo.paserver.domain;

/**
 * A Level is a user-friendly representation of Reputation.
 * The Reputation range is divided is different sub-ranges
 * each of which corresponds to a Level
 * 
 * @author danielecampogiani
 * @see Reputation
 *
 */
public class Level {

	/**
	 * The Rank of a level, could be
	 * Low, Medium_low, Medium_High or High
	 * 
	 * @author danielecampogiani
	 *
	 */
	public enum LevelRank {
		LOW, MEDIUM_LOW, MEDIUM_HIGH, HIGH
	};

	// Maybe could be better using Reputation.REPUTATION_MAX and
	// Reputation.REPUTATION_MIN
	private static final int MEDIUM_LOW_THRESHOLD = 26;
	private static final int MEDIUM_HIGH_THRESHOLD = 51;
	private static final int HIGH_THRESHOLD = 76;

	private Level() {
	} // Must use static method to get an instance

	private ActionType actionType;
	private LevelRank levelRank;

	/**
	 * Return the ActionType which is related
	 * to the Level
	 * 
	 * @return the ActionType which is related to the Level
	 * @see ActionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Sets the ActionType which is related
	 * to the Level
	 * 
	 * @param actionType the ActionType which is related to the Level
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns the rank of the level
	 * 
	 * @return the rank of the level
	 * @see LevelRank
	 */
	public LevelRank getLevelRank() {
		return levelRank;
	}

	/**
	 * Sets the rank of the level
	 * 
	 * @param levelRank
	 * @see LevelRank
	 */
	public void setLevelRank(LevelRank levelRank) {
		this.levelRank = levelRank;
	}

	/**
	 * You should use this static method to instantiate a Level
	 * object passing a Reputation
	 * 
	 * @param reputation the Reputation used to instantiate the resulting Level
	 * @return a Level built using a Reputation
	 * @see Reputation
	 */
	public static Level getLevelFromReputation(Reputation reputation) {
		Level result = new Level();
		if(reputation != null){
			int reputationValue = reputation.getValue();
			LevelRank levelRank = LevelRank.LOW;
			if (reputationValue >= HIGH_THRESHOLD)
				levelRank = LevelRank.HIGH;
			else if (reputationValue >= MEDIUM_HIGH_THRESHOLD)
				levelRank = LevelRank.MEDIUM_HIGH;
			else if (reputationValue >= MEDIUM_LOW_THRESHOLD)
				levelRank = LevelRank.MEDIUM_LOW;
			result.actionType = reputation.getActionType();
			result.levelRank = levelRank;
		}else{
			result.levelRank = LevelRank.MEDIUM_LOW;
			result.actionType = ActionType.SENSING_MOST;
		}
		return result;

	}

}
