package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.MailingLogs;

public class MailingLogsBuilder extends EntityBuilder<MailingLogs> {
	@Override
	void initEntity() {
		
		entity = new MailingLogs();

	}

	@Override
	MailingLogs assembleEntity() {
		
		return entity;
	}

	public MailingLogsBuilder setAll(Long taskId, Long userId, String userEmail, String userDevice, String userDeviceToken, String userDeviceOS, Long emailId, String emailTitle, String emailBody) {
		
		this.setTaskId(taskId);
		this.setUserId(userId);

		this.setUserEmail(userEmail);
		this.setUserDevice(userDevice);
		this.setUserDeviceToken(userDeviceToken);
		this.setUserDeviceOS(userDeviceOS);

		this.setEmailId(emailId);
		this.setEmailTitle(emailTitle);
		this.setEmailBody(emailBody);
		return this;
	}

	/**
	 * Setter/Getter
	 */

	public MailingLogsBuilder setEmailBody(String emailBody) {
		
		entity.setEmailBody(emailBody);
		return this;
	}

	public MailingLogsBuilder setEmailTitle(String emailTitle) {
		
		entity.setEmailTitle(emailTitle);
		return this;
	}

	public MailingLogsBuilder setEmailId(Long emailId) {
		
		entity.setEmailId(emailId);
		return this;
	}

	public MailingLogsBuilder setUserDeviceOS(String userDeviceOS) {
		
		entity.setUserDeviceOS(userDeviceOS);
		return this;
	}

	public MailingLogsBuilder setUserDeviceToken(String userDeviceToken) {
		
		entity.setUserDeviceToken(userDeviceToken);
		return this;
	}

	public MailingLogsBuilder setUserEmail(String userEmail) {
		entity.setUserEmail(userEmail);
		
		return this;
	}

	public MailingLogsBuilder setUserDevice(String setUserDevice) {
		entity.setUserDevice(setUserDevice);
		
		return this;
	}

	public MailingLogsBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public MailingLogsBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public MailingLogsBuilder setUserId(Long userId) {
		entity.setUserId(userId);
		return this;
	}

	public MailingLogsBuilder setProcessed(boolean isProcessed) {
		entity.setProcessed(isProcessed);
		return this;
	}

	public MailingLogsBuilder setAccepted(boolean isAccepted) {
		entity.setAccepted(isAccepted);
		return this;
	}

	public MailingLogsBuilder setRejected(boolean isRejected) {
		entity.setRejected(isRejected);
		return this;
	}

	public MailingLogsBuilder setDelivered(boolean isDelivered) {
		entity.setDelivered(isDelivered);
		return this;
	}

	public MailingLogsBuilder setDropped(boolean isDropped) {
		entity.setDropped(isDropped);
		return this;
	}

	public MailingLogsBuilder setResend(boolean isResend) {
		entity.setResend(isResend);
		return this;
	}

	public MailingLogsBuilder setQueued(boolean isQueued) {
		entity.setQueued(isQueued);
		return this;
	}

	public MailingLogsBuilder setPushed(boolean isPushed) {
		entity.setPushed(isPushed);
		return this;
	}

	public MailingLogsBuilder setDeliveryDate(DateTime deliveryDate) {
		entity.setDeliveryDate(deliveryDate);
		return this;
	}

	public MailingLogsBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public MailingLogsBuilder setUserDevicePushTypeId(PANotification.Type userDevicePushTypeId) {
		entity.setUserDevicePushTypeId(userDevicePushTypeId);
		return this;
	}

	public MailingLogsBuilder setUserName(String userName) {
		entity.setUserName(userName);
		return this;
	}

	public MailingLogsBuilder setQrCodeToken(String qrCodeToken) {
		entity.setQrCodeToken(qrCodeToken);
		return this;
	}

	public MailingLogsBuilder setQrCodeUsed(boolean qrCodeUsed) {
		entity.setQrCodeUsed(qrCodeUsed);
		return this;
	}

	public MailingLogsBuilder setPushNotificationId(long id) {
		
		entity.setPushNotificationId(id);
		return this;
	}
}