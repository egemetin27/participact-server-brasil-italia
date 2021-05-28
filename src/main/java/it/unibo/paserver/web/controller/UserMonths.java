package it.unibo.paserver.web.controller;

import java.util.List;

public class UserMonths {
	List<UserStats> user;
	String[] months;

	public List<UserStats> getUser() {
		return user;
	}

	public void setUser(List<UserStats> user) {
		this.user = user;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public UserMonths() {

	}

}
