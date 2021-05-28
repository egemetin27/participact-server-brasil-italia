package it.unibo.paserver.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe para respostas padrao das APIs
 * 
 * @author Claudio
 * @param <E>
 */
public class ResponseJsonCkanComcap extends ResponseJsonRest {
	private List<CkanComcap> items = new ArrayList<CkanComcap>();

	/**
	 * @return the items
	 */
	public List<CkanComcap> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<CkanComcap> items) {
		this.items = items;
	}

}