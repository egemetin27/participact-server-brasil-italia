package it.unibo.paserver.web.controller;

import java.io.Serializable;

public class SelectUsersForm implements Serializable {

	private static final long serialVersionUID = 886883893483778991L;
	private String userList;

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

}
