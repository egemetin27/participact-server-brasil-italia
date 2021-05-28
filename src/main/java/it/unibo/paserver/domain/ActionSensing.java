package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.ActionFlat;
import it.unibo.paserver.domain.support.Pipeline;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ActionSensing extends Action {

	private static final long serialVersionUID = 4946939022575803236L;

	@NotNull
	private Integer input_type;

	public Integer getInput_type() {
		return input_type;
	}

	public void setInput_type(Integer input_type) {
		this.input_type = input_type;
	}

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}

	public Pipeline.Type getPipelineType() {
		return Pipeline.Type.fromInt(getInput_type());
	}

	public Class<? extends Data> getDataClass() {
		return getPipelineType().getDataClass();
	}

	public String getPipelineName() {
		return getPipelineType().getDataClass().getSimpleName();
	}

	public void setSensorWeekDay(boolean sensorWeekSun, boolean sensorWeekMon, boolean sensorWeekTue, boolean sensorWeekWed, boolean sensorWeekThu, boolean sensorWeekFri, boolean sensorWeekSat) {
		
		this.setSensorWeekSun(sensorWeekSun);
		this.setSensorWeekMon(sensorWeekMon);
		this.setSensorWeekTue(sensorWeekTue);
		this.setSensorWeekWed(sensorWeekWed);
		this.setSensorWeekThu(sensorWeekThu);
		this.setSensorWeekFri(sensorWeekFri);
		this.setSensorWeekSat(sensorWeekSat);
	}
}