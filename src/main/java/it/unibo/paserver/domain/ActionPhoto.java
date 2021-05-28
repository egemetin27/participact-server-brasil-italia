package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.ActionFlat;

import javax.persistence.Entity;

@Entity
public class ActionPhoto extends Action {

	private static final long serialVersionUID = -7496258994411500599L;

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}
}
