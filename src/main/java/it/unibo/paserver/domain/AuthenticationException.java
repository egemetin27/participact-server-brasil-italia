package it.unibo.paserver.domain;

public class AuthenticationException extends Exception {

	private static final long serialVersionUID = -6854799811069054409L;

	public AuthenticationException(String message) {
		super(message);
	}
}
