package it.unibo.paserver.domain;

public enum ActionType {
	SENSING_MOST, PHOTO, QUESTIONNAIRE, ACTIVITY_DETECTION;

	@Override
	public String toString() {
		switch (this) {
		case SENSING_MOST:
			return "Passive sensing";
		case PHOTO:
			return "Photo";
		case QUESTIONNAIRE:
			return "Questionnaire";
		case ACTIVITY_DETECTION:
			return "Activity detection";
		default:
			return "Unknown";
		}
	}

}
