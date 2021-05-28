package it.unibo.paserver.web.controller.task;

import java.io.Serializable;

public class TaskAssignedUsersForm implements Serializable {

	private static final long serialVersionUID = 8580730635435475521L;
	private String userList;

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

}
