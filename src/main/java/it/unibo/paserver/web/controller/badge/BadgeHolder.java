package it.unibo.paserver.web.controller.badge;

import java.io.Serializable;

public class BadgeHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5661272197065590839L;

	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
