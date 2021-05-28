package it.unibo.paserver.web.controller;

import java.io.Serializable;

import it.unibo.participact.domain.PANotification;

public class SelectUsersGCMForm implements Serializable {

	private static final long serialVersionUID = -2298637657502035126L;
	private PANotification.Type gcmType;
	private String userList;

	public PANotification.Type getGcmType() {
		return gcmType;
	}

	public void setGcmType(PANotification.Type gcmType) {
		this.gcmType = gcmType;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

}
