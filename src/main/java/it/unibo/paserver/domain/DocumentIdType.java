package it.unibo.paserver.domain;

public enum DocumentIdType {
	NATIONAL_ID, CF, DRIVING_LICENCE, PASSPORT, NONE;

	public static DocumentIdType getRandom() {
		
		return values()[(int) (Math.random() * values().length)];
	}
}
