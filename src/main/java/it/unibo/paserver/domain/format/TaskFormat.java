package it.unibo.paserver.domain.format;

import java.util.Set;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.TaskReport;

public abstract class TaskFormat {
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	abstract Long getId();
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	abstract String getName();
	@JsonIgnore
	abstract boolean isAssign();
	@JsonIgnore
	abstract DateTime getCreationDate();
	@JsonIgnore
	abstract DateTime getUpdateDate();
	@JsonIgnore
	abstract Set<Action> getActions() ;
	@JsonIgnore
	abstract Set<TaskReport> getTaskReport();
}
