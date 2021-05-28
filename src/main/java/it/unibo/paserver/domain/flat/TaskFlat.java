package it.unibo.paserver.domain.flat;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.LocationAwareTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.JsonDateTimeStandardizationDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeStandardizationSerializer;

//@JsonIgnoreProperties({ "start" })
public class TaskFlat implements Serializable {

	private static final long serialVersionUID = 1614961604356012108L;

	private Long id;
	private String name;

	private String description;

	@JsonDeserialize(using = JsonDateTimeStandardizationDeserializer.class)
	@JsonSerialize(using = JsonDateTimeStandardizationSerializer.class)
	private DateTime deadline;

	private Integer points;

	@JsonDeserialize(using = JsonDateTimeStandardizationDeserializer.class)
	@JsonSerialize(using = JsonDateTimeStandardizationSerializer.class)
	private DateTime start;

	private Long duration;

	private Long sensingDuration;

	private Double latitude;

	private Double longitude;

	private Double radius;

	private Boolean canBeRefused;

	private Set<ActionFlat> actions;

	private String type;

	private String notificationArea;

	private String activationArea;

	@JsonDeserialize(using = JsonDateTimeStandardizationDeserializer.class)
	@JsonSerialize(using = JsonDateTimeStandardizationSerializer.class)
	private DateTime creationDate;
	@JsonDeserialize(using = JsonDateTimeStandardizationDeserializer.class)
	@JsonSerialize(using = JsonDateTimeStandardizationSerializer.class)
	private DateTime updateDate;
	private boolean isPublish = false;
	private boolean checkDetails = false;
	private boolean isDuration = false;
	private boolean removed = false;
	private String agreement;
	private Boolean hasPhotos = false;
	private Boolean hasQuestionnaire = false;
	private Boolean sensingWeekSun = true;
	private Boolean sensingWeekMon = true;
	private Boolean sensingWeekTue = true;
	private Boolean sensingWeekWed = true;
	private Boolean sensingWeekThu = true;
	private Boolean sensingWeekFri = true;
	private Boolean sensingWeekSat = true;

	private String color = "#7E986C";
	private String iconUrl = null;

	/**
	 * Setters/Getters
	 */
	public String getNotificationArea() {
		return notificationArea;
	}

	public void setNotificationArea(String notificationArea) {
		this.notificationArea = notificationArea;
	}

	public String getActivationArea() {
		return activationArea;
	}

	public void setActivationArea(String activationArea) {
		this.activationArea = activationArea;
	}

	public TaskFlat() {
	}

	public TaskFlat(Task task) {
		init(task);
	}

	public TaskFlat(LocationAwareTask task) {
		init(task);
		this.latitude = task.getLatitude();
		this.longitude = task.getLongitude();
		this.radius = task.getRadius();
	}

	private void init(Task task) {
		this.id = task.getId();
		this.name = task.getName();
		this.canBeRefused = task.getCanBeRefused();
		this.setDescription(task.getDescription());
		this.start = task.getStart();
		this.deadline = task.getDeadline();
		this.points = 0;
		this.type = task.getClass().getSimpleName();
		this.duration = task.getDuration();
		this.sensingDuration = task.getSensingDuration();
		actions = new HashSet<ActionFlat>();
		for (Action action : task.getActions()) {
			actions.add(action.convertToActionFlat());
		}
		this.activationArea = task.getActivationArea();
		this.notificationArea = task.getNotificationArea();
		// Campos Adicionais
		this.isPublish = task.isPublish();
		this.checkDetails = task.isCheckDetails();
		this.isDuration = task.isDuration();
		this.creationDate = task.getCreationDate();
		this.updateDate = task.getUpdateDate();
		this.removed = task.isRemoved();
		this.agreement = task.getAgreement();
		this.hasPhotos = task.getHasPhotos();
		this.hasQuestionnaire = task.getHasQuestionnaire();
		this.sensingWeekSun = task.getSensingWeekSun();
		this.sensingWeekMon = task.getSensingWeekMon();
		this.sensingWeekTue = task.getSensingWeekTue();
		this.sensingWeekWed = task.getSensingWeekWed();
		this.sensingWeekThu = task.getSensingWeekThu();
		this.sensingWeekFri = task.getSensingWeekFri();
		this.sensingWeekSat = task.getSensingWeekSat();

		this.color = task.getColor();
		if (Validator.isEmptyString(color) || !Validator.isValidRgbString(this.color)) {
			this.color = "#7E986C";
		}
		this.iconUrl = task.getIconUrl();
	}

	public Boolean getCanBeRefused() {
		return canBeRefused;
	}

	public void setCanBeRefused(Boolean canBeRefused) {
		this.canBeRefused = canBeRefused;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(DateTime deadline) {
		this.deadline = deadline;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getSensingDuration() {
		return sensingDuration;
	}

	public void setSensingDuration(Long sensingDuration) {
		this.sensingDuration = sensingDuration;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Set<ActionFlat> getActions() {
		return actions;
	}

	public void setActions(Set<ActionFlat> actions) {
		this.actions = actions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPublish() {
		return isPublish;
	}

	public void setPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}

	public boolean isCheckDetails() {
		return checkDetails;
	}

	public void setCheckDetails(boolean checkDetails) {
		this.checkDetails = checkDetails;
	}

	public boolean isDuration() {
		return isDuration;
	}

	public void setDuration(boolean isDuration) {
		this.isDuration = isDuration;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public Boolean getHasPhotos() {
		return hasPhotos;
	}

	public void setHasPhotos(Boolean hasPhotos) {
		this.hasPhotos = hasPhotos;
	}

	public Boolean getHasQuestionnaire() {
		return hasQuestionnaire;
	}

	public void setHasQuestionnaire(Boolean hasQuestionnaire) {
		this.hasQuestionnaire = hasQuestionnaire;
	}

	public Boolean getSensingWeekSun() {
		return sensingWeekSun;
	}

	public void setSensingWeekSun(Boolean sensingWeekSun) {
		this.sensingWeekSun = sensingWeekSun;
	}

	public Boolean getSensingWeekMon() {
		return sensingWeekMon;
	}

	public void setSensingWeekMon(Boolean sensingWeekMon) {
		this.sensingWeekMon = sensingWeekMon;
	}

	public Boolean getSensingWeekTue() {
		return sensingWeekTue;
	}

	public void setSensingWeekTue(Boolean sensingWeekTue) {
		this.sensingWeekTue = sensingWeekTue;
	}

	public Boolean getSensingWeekWed() {
		return sensingWeekWed;
	}

	public void setSensingWeekWed(Boolean sensingWeekWed) {
		this.sensingWeekWed = sensingWeekWed;
	}

	public Boolean getSensingWeekThu() {
		return sensingWeekThu;
	}

	public void setSensingWeekThu(Boolean sensingWeekThu) {
		this.sensingWeekThu = sensingWeekThu;
	}

	public Boolean getSensingWeekFri() {
		return sensingWeekFri;
	}

	public void setSensingWeekFri(Boolean sensingWeekFri) {
		this.sensingWeekFri = sensingWeekFri;
	}

	public Boolean getSensingWeekSat() {
		return sensingWeekSat;
	}

	public void setSensingWeekSat(Boolean sensingWeekSat) {
		this.sensingWeekSat = sensingWeekSat;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl
	 *            the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskFlat other = (TaskFlat) obj;
		if (this.id != other.id)
			return false;
		return true;
	}

}
