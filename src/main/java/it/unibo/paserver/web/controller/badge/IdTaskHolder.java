package it.unibo.paserver.web.controller.badge;

import java.io.Serializable;

public class IdTaskHolder implements Serializable {

	private static final long serialVersionUID = 3304734264755852826L;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
