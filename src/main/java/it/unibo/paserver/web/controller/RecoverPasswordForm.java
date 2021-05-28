package it.unibo.paserver.web.controller;

import java.io.Serializable;

public class RecoverPasswordForm implements Serializable {

	private static final long serialVersionUID = -2479424170154168663L;

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}