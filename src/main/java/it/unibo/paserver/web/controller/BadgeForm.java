package it.unibo.paserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadgeForm {

	private static final Logger logger = LoggerFactory
			.getLogger(BadgeForm.class);

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
