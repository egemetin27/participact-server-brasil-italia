package it.unibo.paserver.domain;

public enum UniCourse {
	GRADUACAO, MESTRADO, DOUTORADO, TRIENNALE, MAGISTRALE, SOCIAL, CHILD_EDUCATION, ELEMENTARY_SCHOOL, HIGH_SCHOOL, TECHNICAL_EDUCATION, GRADUATION, MASTER_DEGREE, DOCTORATE_DEGREE, POSTDOCTORAL, POSTGRADUATE, NONE;

	public static UniCourse getRandom() {
		
		return values()[(int) (Math.random() * values().length)];
	}
}
