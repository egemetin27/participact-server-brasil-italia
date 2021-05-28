package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.ActionFlat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.data.annotation.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Action implements Serializable {

	private static final long serialVersionUID = 8141364234733032490L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "action_id")
	private Long id;
	@Column(columnDefinition = "TEXT")
	private String name;
	private Integer numeric_threshold;
	private Integer duration_threshold;
	@Transient
	private String translated;
	
	private Long sensorDuration = 0L;
	private Boolean sensorWeekSun=true;
	private Boolean sensorWeekMon=true;
	private Boolean sensorWeekTue=true;
	private Boolean sensorWeekWed=true;
	private Boolean sensorWeekThu=true;
	private Boolean sensorWeekFri=true;
	private Boolean sensorWeekSat=true;
	
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

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}

	
	
	public String getTranslated() {
		return translated;
	}

	public void setTranslated(String translated) {
		this.translated = translated;
	}

	public ActionType getType() {
		if (this instanceof ActionActivityDetection)
			return ActionType.ACTIVITY_DETECTION;

		else if (this instanceof ActionPhoto)
			return ActionType.PHOTO;

		else if (this instanceof ActionQuestionaire)
			return ActionType.QUESTIONNAIRE;

		else if (this instanceof ActionSensing)
			return ActionType.SENSING_MOST;

		else
			throw new IllegalArgumentException("No valid action type found");
	}
}
