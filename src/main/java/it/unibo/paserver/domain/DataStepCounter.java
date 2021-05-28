package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class DataStepCounter extends Data {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8925521845488108907L;
	
	public Long getSteps() {
		return steps;
	}

	public void setSteps(Long steps) {
		this.steps = steps;
	}

	@NotNull
	private Long steps;

}
