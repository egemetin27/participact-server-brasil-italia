package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.ActionFlat;

import javax.persistence.Entity;

@Entity
public class ActionActivityDetection extends Action {

	private static final long serialVersionUID = -5522200864243497619L;

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}
}
