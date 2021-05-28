package it.unibo.paserver.domain.flat;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Question;

import java.io.Serializable;
import java.util.List;

public class ActionFlat implements Serializable {

	private static final long serialVersionUID = 9097140818477794937L;

	private Long id;

	private String name;

	private Integer numeric_threshold;

	private Integer duration_threshold;

	private Integer input_type;

	private List<Question> questions;

	private ActionType type;

	private String title;

	private Boolean isRepeat = false;
		
	private Long sensorDuration = 0L;
	private Boolean sensorWeekSun=true;
	private Boolean sensorWeekMon=true;
	private Boolean sensorWeekTue=true;
	private Boolean sensorWeekWed=true;
	private Boolean sensorWeekThu=true;
	private Boolean sensorWeekFri=true;
	private Boolean sensorWeekSat=true;	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	public ActionFlat() {
	}

	public ActionFlat(Action action) {
	}

	public ActionFlat(ActionActivityDetection action) {
		init(action);
		this.setType(ActionType.ACTIVITY_DETECTION);
	}

	public ActionFlat(ActionSensing action) {
		init(action);
		this.input_type = action.getInput_type();
		this.setType(ActionType.SENSING_MOST);
	}

	public ActionFlat(ActionPhoto action) {
		init(action);
		this.setType(ActionType.PHOTO);
	}

	public ActionFlat(ActionQuestionaire action) {
		init(action);
		this.questions = action.getQuestions();
		this.setTitle(action.getTitle());
		this.setDescription(action.getDescription());
		this.setType(ActionType.QUESTIONNAIRE);
		this.setRepeat(action.getRepeat());
	}

	private void init(Action action) {
		this.id = action.getId();
		this.name = action.getName();
		this.duration_threshold = action.getDuration_threshold();
		this.numeric_threshold = action.getNumeric_threshold();
		this.input_type = -1;
		
		this.sensorDuration = action.getSensorDuration();
		this.sensorWeekSun=action.getSensorWeekSun();
		this.sensorWeekMon=action.getSensorWeekMon();
		this.sensorWeekTue=action.getSensorWeekTue();
		this.sensorWeekWed=action.getSensorWeekWed();
		this.sensorWeekThu=action.getSensorWeekThu();
		this.sensorWeekFri=action.getSensorWeekFri();
		this.sensorWeekSat=action.getSensorWeekSat();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumeric_threshold() {
		return numeric_threshold;
	}

	public void setNumeric_threshold(Integer numeric_threshold) {
		this.numeric_threshold = numeric_threshold;
	}

	public Integer getDuration_threshold() {
		return duration_threshold;
	}

	public void setDuration_threshold(Integer duration_threshold) {
		this.duration_threshold = duration_threshold;
	}

	public Integer getInput_type() {
		return input_type;
	}

	public void setInput_type(Integer input_type) {
		this.input_type = input_type;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public Long getSensorDuration() {
		return sensorDuration;
	}

	public void setSensorDuration(Long sensorDuration) {
		this.sensorDuration = sensorDuration;
	}

	public Boolean getSensorWeekSun() {
		return sensorWeekSun;
	}

	public void setSensorWeekSun(Boolean sensorWeekSun) {
		this.sensorWeekSun = sensorWeekSun;
	}

	public Boolean getSensorWeekMon() {
		return sensorWeekMon;
	}

	public void setSensorWeekMon(Boolean sensorWeekMon) {
		this.sensorWeekMon = sensorWeekMon;
	}

	public Boolean getSensorWeekTue() {
		return sensorWeekTue;
	}

	public void setSensorWeekTue(Boolean sensorWeekTue) {
		this.sensorWeekTue = sensorWeekTue;
	}

	public Boolean getSensorWeekWed() {
		return sensorWeekWed;
	}

	public void setSensorWeekWed(Boolean sensorWeekWed) {
		this.sensorWeekWed = sensorWeekWed;
	}

	public Boolean getSensorWeekThu() {
		return sensorWeekThu;
	}

	public void setSensorWeekThu(Boolean sensorWeekThu) {
		this.sensorWeekThu = sensorWeekThu;
	}

	public Boolean getSensorWeekFri() {
		return sensorWeekFri;
	}

	public void setSensorWeekFri(Boolean sensorWeekFri) {
		this.sensorWeekFri = sensorWeekFri;
	}

	public Boolean getSensorWeekSat() {
		return sensorWeekSat;
	}

	public void setSensorWeekSat(Boolean sensorWeekSat) {
		this.sensorWeekSat = sensorWeekSat;
	}

	public Boolean getRepeat() {
		return isRepeat;
	}

	public void setRepeat(Boolean repeat) {
		isRepeat = repeat;
	}
}
