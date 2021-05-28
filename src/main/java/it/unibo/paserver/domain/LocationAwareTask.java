package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.TaskFlat;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class LocationAwareTask extends Task {

	private static final long serialVersionUID = -6855620555493420665L;

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private Double radius;

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

	@Override
	public String toString() {
		String actions = "";
		for (Action act : getActions()) {
			actions += act.getName() + " ";
		}
		return String
				.format("%s Id:%s Name:%s DeadLine:%s Latitude:%s Longitude:%s Actions:%s",
						Task.class.getSimpleName(), getId(), getName(),
						getDeadline(), getLatitude(), getLongitude(), actions);
	}

	public TaskFlat convertToTaskFlat() {
		return new TaskFlat(this);
	}
}
