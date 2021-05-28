package it.unibo.paserver.domain;

public enum Gender {
	MALE, FEMALE, OTHER, NONE;

	public static Gender getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}
}
