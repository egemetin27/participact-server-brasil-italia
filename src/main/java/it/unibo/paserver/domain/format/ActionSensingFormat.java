package it.unibo.paserver.domain.format;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.support.Pipeline;

public abstract class ActionSensingFormat extends ActionFormat {
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public abstract Integer getInput_type();
	public abstract Pipeline.Type getPipelineType();
	@JsonIgnore
	public abstract Class<? extends Data> getDataClass();
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract String getPipelineName();
}
