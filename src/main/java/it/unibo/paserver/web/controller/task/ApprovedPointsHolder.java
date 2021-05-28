package it.unibo.paserver.web.controller.task;

import java.io.Serializable;

public class ApprovedPointsHolder implements Serializable{

	private static final long serialVersionUID = -6390747575598218550L;
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	

}
