package it.unibo.participact.domain;

public class PANotification {

	public static final String KEY = "TYPE";

	public enum Type {
		NEW_TASK, NEW_VERSION, MESSAGE, NEWS, LOG_UPLOAD_REQUEST, NEW_BADGE, NEW_FRIEND, TASK_NEGATIVE_VALUTATION, TASK_POSITIVE_VALUTATION, ONLY_EMAIL;
	}
	
	
}
