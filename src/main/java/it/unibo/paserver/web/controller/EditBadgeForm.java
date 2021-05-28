package it.unibo.paserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.paserver.domain.Badge;

public class EditBadgeForm extends BadgeForm {

	private static final Logger logger = LoggerFactory
			.getLogger(EditBadgeForm.class);

	public void initFromBadge(Badge badge) {
		setTitle(badge.getTitle());
		setDescription(badge.getDescription());
	}

	public void updateBadge(Badge badge) {

		if (getTitle() != null) {
			badge.setTitle(getTitle());
		} else if (badge.getTitle() != null && getTitle() == null) {
			badge.setTitle("");
		}

		if (getDescription() != null) {
			badge.setDescription(getDescription());
		} else if (badge.getDescription() != null && getDescription() == null) {
			badge.setDescription("");
		}

	}
}
