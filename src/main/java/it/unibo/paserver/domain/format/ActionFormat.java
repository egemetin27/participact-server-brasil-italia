package it.unibo.paserver.domain.format;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.unibo.paserver.domain.ActionType;

public abstract class ActionFormat {
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public abstract Long getId();

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract String getName();

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public abstract Integer getNumeric_threshold();

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public abstract Integer getDuration_threshold();

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract String getTranslated();

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract ActionType getType();
}
