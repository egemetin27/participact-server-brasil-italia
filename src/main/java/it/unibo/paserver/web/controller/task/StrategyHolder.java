package it.unibo.paserver.web.controller.task;

import java.io.Serializable;

public class StrategyHolder implements Serializable {

	private static final long serialVersionUID = -336654676104750744L;
	
	private Integer id;
	private String name;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
