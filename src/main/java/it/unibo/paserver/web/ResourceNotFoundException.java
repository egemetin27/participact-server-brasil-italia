package it.unibo.paserver.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(Throwable t) {
		super(t);
	}

	public ResourceNotFoundException() {
		super();
	}

	private static final long serialVersionUID = -5912151731227302116L;

}
