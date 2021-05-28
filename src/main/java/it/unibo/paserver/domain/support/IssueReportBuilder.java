package it.unibo.paserver.domain.support;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.IssueAbuse;
import it.unibo.paserver.domain.IssueReport;
import it.unibo.paserver.domain.IssueSubCategory;
import it.unibo.paserver.domain.StorageFile;
import it.unibo.paserver.domain.User;

@Component
public class IssueReportBuilder extends EntityBuilder<IssueReport> {

	@Override
	void initEntity() {
		
		entity = new IssueReport();
	}

	@Override
	IssueReport assembleEntity() {
		
		return entity;
	}

	public IssueReportBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public IssueReportBuilder setComment(String comment) {
		entity.setComment(comment);
		return this;
	}

	public IssueReportBuilder setNegativeScore(long score) {
		entity.setNegativeScore(score);
		return this;
	}

	public IssueReportBuilder setLongitude(double longitude) {
		entity.setLongitude(longitude);
		return this;
	}

	public IssueReportBuilder setLatitude(double latitude) {
		entity.setLatitude(latitude);
		return this;
	}

	public IssueReportBuilder setAccuracy(double accuracy) {
		entity.setAccuracy(accuracy);
		return this;
	}

	public IssueReportBuilder setProvider(String provider) {
		entity.setProvider(provider);
		return this;
	}

	public IssueReportBuilder setAltitude(double altitude) {
		entity.setAltitude(altitude);
		return this;
	}

	public IssueReportBuilder setHorizontalAccuracy(double horizontalAccuracy) {
		entity.setHorizontalAccuracy(horizontalAccuracy);
		return this;
	}

	public IssueReportBuilder setVerticalAccuracy(double verticalAccuracy) {
		entity.setVerticalAccuracy(verticalAccuracy);
		return this;
	}

	public IssueReportBuilder setCourse(double course) {
		entity.setCourse(course);
		return this;
	}

	public IssueReportBuilder setSpeed(double speed) {
		entity.setSpeed(speed);
		return this;
	}

	public IssueReportBuilder setFloor(double floor) {
		entity.setFloor(floor);
		return this;
	}

	public IssueReportBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	public IssueReportBuilder setAbuse(List<IssueAbuse> abuses) {
		entity.setAbuses(abuses);
		return this;
	}

	public IssueReportBuilder setSubcategory(IssueSubCategory subcategory) {
		entity.setSubcategory(subcategory);
		return this;
	}

	public IssueReportBuilder setFiles(List<StorageFile> files) {
		entity.setFiles(files);
		return this;
	}

	public IssueReportBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public IssueReportBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public IssueReportBuilder setEditDate(DateTime editDate) {
		entity.setEditDate(editDate);
		return this;
	}

	public IssueReportBuilder setSampleTimestamp(DateTime sampleTimestamp) {
		entity.setSampleTimestamp(sampleTimestamp);
		return this;
	}

	public IssueReportBuilder setDataReceivedTimestamp(DateTime dataReceivedTimestamp) {
		entity.setDataReceivedTimestamp(dataReceivedTimestamp);
		return this;
	}

	public IssueReportBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public IssueReportBuilder setFormattedCity(String value) {
		entity.setFormattedCity(value);
		return this;
	}

	public IssueReportBuilder setFormattedAddress(String value) {
		entity.setFormattedAddress(value);
		return this;
	}

	public IssueReportBuilder setFileCounter(int value) {
		entity.setFileCounter(value);
		return this;
	}

	public IssueReportBuilder setGpsInfo(String gpsInfo) {
		entity.setGpsInfo(gpsInfo);
		return this;
	}

	public IssueReportBuilder setOptionalUserEmail(String optionalUserEmail) {
		entity.setOptionalUserEmail(optionalUserEmail);
		return this;
	}

	public IssueReportBuilder setOptionalUserName(String optionalUserName) {
		entity.setOptionalUserName(optionalUserName);
		return this;
	}
}