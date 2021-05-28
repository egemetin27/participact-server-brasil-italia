package it.unibo.paserver.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends RuntimeException {

	public ServerErrorException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4679873801017282291L;

}
