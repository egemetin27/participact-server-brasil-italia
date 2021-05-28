package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TaskBuilder extends EntityBuilder<Task> {

	@Override
	void initEntity() {
		entity = new Task();

	}

	public TaskBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskBuilder setActivationArea(String activationArea) {
		entity.setActivationArea(activationArea);
		return this;
	}

	public TaskBuilder setCanBeRefused(boolean canBeRefused) {
		entity.setCanBeRefused(canBeRefused);
		return this;
	}

	public TaskBuilder setActions(Set<Action> actions) {
		entity.setActions(actions);
		return this;
	}

	public TaskBuilder setDeadline(DateTime deadline) {
		entity.setDeadline(deadline);
		return this;
	}

	public TaskBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	public TaskBuilder setDuration(Long duration) {
		entity.setDuration(duration);
		return this;
	}

	public TaskBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	public TaskBuilder setNotificationArea(String notificationArea) {
		entity.setNotificationArea(notificationArea);
		return this;
	}

	public TaskBuilder setSensingDuration(long sensingDuration) {
		entity.setSensingDuration(sensingDuration);
		return this;
	}

	public TaskBuilder setStart(DateTime start) {
		entity.setStart(start);
		return this;
	}

	public TaskBuilder setTaskReport(Set<TaskReport> taskReport) {
		entity.setTaskReport(taskReport);
		return this;
	}

	public TaskBuilder setIsDuration(boolean isDuration) {
		entity.setIsDuration(isDuration);
		return this;
	}

	public TaskBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public TaskBuilder setPublish(boolean isPublish) {
		entity.setPublish(isPublish);
		return this;
	}

	public TaskBuilder setEmailBody(String emailBody) {
		entity.setEmailBody(emailBody);
		return this;
	}

	public TaskBuilder setSendEmail(boolean isSendEmail) {
		entity.setSendEmail(isSendEmail);
		return this;
	}

	public TaskBuilder setEmailSubject(String emailSubject) {
		entity.setEmailSubject(emailSubject);
		return this;
	}

	public TaskBuilder setEmailSystemId(Long emailSystemId) {
		entity.setEmailSystemId(emailSystemId);
		return this;
	}

	public TaskBuilder setSensingWeekDay(boolean sensingWeekSun, boolean sensingWeekMon, boolean sensingWeekTue, boolean sensingWeekWed, boolean sensingWeekThu, boolean sensingWeekFri, boolean sensingWeekSat) {
		entity.setSensingWeekDay(sensingWeekSun, sensingWeekMon, sensingWeekTue, sensingWeekWed, sensingWeekThu, sensingWeekFri, sensingWeekSat);
		return this;
	}
	
	public TaskBuilder setAgreement(String agreement) {
		entity.setAgreement(agreement);
		return this;
	}
	
	public TaskBuilder setColor(String color) {
		
		entity.setColor(color);
		return this;
	}
	
	public TaskBuilder setIconUrl(String iconUrl) {
		
		entity.setIconUrl(iconUrl);
		return this;
	}

	@Override
	Task assembleEntity() {
		return entity;
	}

	public TaskBuilder setWpPublishPage(boolean wpPublishPage) {
		entity.setWpPublishPage(wpPublishPage);
		return this;		
	}

	public TaskBuilder setWpPostDescription(boolean wpPostDescription) {
		entity.setWpPostDescription(wpPostDescription);
		return this;		
	}

	public TaskBuilder setWpPostSensorList(boolean wpPostSensorList) {
		entity.setWpPostSensorList(wpPostSensorList);
		return this;		
	}

	public TaskBuilder setWpPostCampaignStats(boolean wpPostCampaignStats) {
		entity.setWpPostCampaignStats(wpPostCampaignStats);
		return this;		
	}

	public TaskBuilder setWpPublishText(String wpPublishText) {
		entity.setWpPublishText(wpPublishText);
		return this;		
	}

	public TaskBuilder setWpPostQuestionnaire(boolean wpPostQuestionnaire){
		entity.setWpPostQuestionnaire(wpPostQuestionnaire);
		return this;
	}

	public TaskBuilder setEnabled(boolean enabled){
		entity.setEnabled(enabled);
		return this;
	}
}
