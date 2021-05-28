package it.unibo.paserver.web.controller;

import org.hibernate.validator.constraints.NotEmpty;

public class AddAccountForm {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String confirmPassword;
	private Boolean roleView;
	private Boolean roleAdmin;

	public AddAccountForm() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getRoleView() {
		return roleView;
	}

	public void setRoleView(Boolean roleView) {
		this.roleView = roleView;
	}

	public Boolean getRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(Boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	@Override
	public String toString() {
		return username;
	}
}
