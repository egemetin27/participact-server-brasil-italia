package it.unibo.paserver.web.controller;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.MailingHistory;
import it.unibo.paserver.domain.MailingLogs;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.PushNotificationsLogs;
import it.unibo.paserver.domain.SystemEmail;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.MailingHistoryBuilder;
import it.unibo.paserver.domain.support.MailingLogsBuilder;
import it.unibo.paserver.domain.support.PushNotificationsLogsBuilder;
import it.unibo.paserver.service.MailingHistoryService;
import it.unibo.paserver.service.MailingLogsService;
import it.unibo.paserver.service.PushNotificationsLogsService;
import it.unibo.paserver.service.PushNotificationsService;
import it.unibo.paserver.service.SystemEmailService;

@SuppressWarnings("Duplicates")
@Controller
public class MailerController {
	private static final Logger logger = LoggerFactory.getLogger(MailerController.class);
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SystemEmailService systemEmailService;
	@Autowired
	private MailingLogsService mailingQueueService;
	@Autowired
	private MailingHistoryService mailingHistoryService;
	@Autowired
	private PushNotificationsService pushNotificationsService;
	@Autowired
	private PushNotificationsLogsService pushNotificationsLogsService;

	private String fromEmail = Config.emailFrom;
	private int emailSmtpPort = Config.emailSmtpPort;
	private String emailHostName = Config.emailHostName;
	private String emailAuthenticatorUsername = Config.emailAuthenticatorUsername;
	private String emailAuthenticatorPassword = Config.emailAuthenticatorPassword;
	private boolean emailSSLOnConnect = Config.emailSSLOnConnect;

	/**
	 * Executa o envio de uma lista
	 * 
	 * @param t
	 * @param users
	 */
	@Async
	public void inviteByListUser(Task t, List<User> users) {
		if (t.getEmailSystemId() != null && t.getEmailSystemId() > 0) {
			SystemEmail se = systemEmailService.findById(t.getEmailSystemId());
			if (se != null) {
				this.fromEmail = se.getFromEmail();
				this.emailSmtpPort = se.getSmtpPort() != null ? se.getSmtpPort().intValue() : Config.emailSmtpPort;
				this.emailHostName = se.getSmtpHost();
				this.emailAuthenticatorUsername = se.getUsername();
				this.emailAuthenticatorPassword = se.getPassword();
				this.emailSSLOnConnect = Validator.isStringEquals(se.getEncryption(), "SSL");
			}
		}
		// Loop
		for (User u : users) {
			String email = u.getOfficialEmail();
			try {
				if (Validator.isValidEmail(email)) {
					Object[] args = new Object[] { u.getName() };
					String emailBody = t.getEmailBody();
					String emailSubject = t.getEmailSubject();
					if (Validator.isEmptyString(emailSubject)) {
						emailSubject = messageSource.getMessage("email.subject.invite", args, LocaleContextHolder.getLocale());
					}
					// Garantindo que sempre teremos um texto de email
					if (!Validator.isEmptyString(emailBody)) {
						logger.info("Email: " + email);
						senderHtml(emailSubject, this.fromEmail, email, emailBody, emailBody);
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage() + "");
			}
		}
	}

	@Async
	public void inviteMailSending(List<MailingLogs> userList, SystemEmail se) {
		System.out.println("inviteMailSending");
		if (se != null) {
			String fromEmail = se.getFromEmail();
			this.emailSmtpPort = se.getSmtpPort() != null ? se.getSmtpPort().intValue() : Config.emailSmtpPort;
			this.emailHostName = se.getSmtpHost();
			this.emailAuthenticatorUsername = se.getUsername();
			this.emailAuthenticatorPassword = se.getPassword();
			this.emailSSLOnConnect = Validator.isStringEquals(se.getEncryption(), "SSL");
			// Loop
			for (MailingLogs mq : userList) {
				String email = mq.getUserEmail();
				System.out.printf("MailingLogs mq %s", email);
				try {
					if (Validator.isValidEmail(email)) {
						Object[] args = new Object[] { mq.getUserName() };
						String emailBody = mq.getEmailBody();
						String emailSubject = mq.getEmailTitle();
						// Validate
						if (Validator.isEmptyString(emailSubject)) {
							emailSubject = messageSource.getMessage("email.subject.invite", args, LocaleContextHolder.getLocale());
						}
						// Garantindo que sempre teremos um texto de email
						if (!Validator.isEmptyString(emailBody)) {
							logger.info("Email: Envio >>> " + email);
							senderHtml(emailSubject, fromEmail, email, emailBody, emailBody);
						}
					}
				} catch (Exception e) {
					// Dropped
					mq.setDropped(true);
					mailingQueueService.saveOrUpdate(mq);
					Long pId = mq.getPushNotificationId();
					if (pId != null && pId.longValue() > 0) {
						// Push
						PushNotifications push = pushNotificationsService.findById(pId);
						// Save Response
						PushNotificationsLogsBuilder pnlm = new PushNotificationsLogsBuilder();
						pnlm.setAll(0L, push.getParentId(), push.getTaskId(), push, false, 0L, 0L, 1L, 0L, "", "", e.getMessage().toString());
						pnlm.setUserId(mq.getUserId());
						PushNotificationsLogs pnl = pnlm.build(true);
						pushNotificationsLogsService.saveOrUpdate(pnl);
					}
					// Set
					System.out.printf("Exception: %s", e.getMessage());
					e.printStackTrace(System.out);
				}
			}
		}
	}

	/**
	 * Adicionando na fila para processamento
	 * 
	 * @param t
	 * @param users
	 */
	@Async
	public void inviteScheduleMailSending(Task t, List<User> users, Boolean isSaveHistory, Boolean isOnlyEmail, PushNotifications p) {
		System.out.println("inviteScheduleMailSending");
		// Email
		Long emailId = 0L;
		String emailTitle = null;
		String emailBody = null;
		boolean hasEmail = false;
		// Limites
		Long limitSending = 0L;
		Long limitCount = 0L;
		Long limitTime = 1L;
		Long totalSubmitted = 0L;
		Long totalFailed = 0L;
		DateTime DeliveryDate = new DateTime().minusHours(3);
		// Verificando se campanha contem email
		if (Config.emailEnable && t.getIsSendEmail()) {
			// System.out.println("emailEnable");
			if (t.getEmailSystemId() != null && t.getEmailSystemId() > 0) {
				// System.out.println("getEmailSystemId");
				SystemEmail se = systemEmailService.findById(t.getEmailSystemId());
				if (se != null) {
					limitSending = se.getLimitSending();
					limitSending = limitSending != null && limitSending > 0 && limitSending <= Config.defaultLimitSendingMax ? limitSending : Config.defaultLimitSending;
					limitTime = se.getLimitTime();
					limitTime = limitTime != null && limitTime > 0 && limitTime <= Config.defaultLimitTimeMax ? limitTime : Config.defaultLimitTime;
					emailId = t.getEmailSystemId();
					hasEmail = true;
					// Emails
					emailTitle = t.getEmailSubject();
					emailBody = t.getEmailBody();
					if (isSaveHistory) {
						// Salve Log/History Texts
						MailingHistoryBuilder mhb = new MailingHistoryBuilder();
						MailingHistory mh = mhb.setAll(0L, t.getId(), t.getParentId(), emailTitle, emailBody, t.getIsSendEmail(), emailId, false).build(true);
						mailingHistoryService.saveOrUpdate(mh);
					}
				}
			}
		}
		// QR CODE
		if (Validator.isStringContains(emailBody, Config.QRCODEIMAGE)) {
			emailBody = emailBody.replace(Config.QRCODEIMAGE, Config.emailQrCodeImage);
		}
		if (Validator.isStringContains(emailBody, Config.QRCODEURL)) {
			emailBody = emailBody.replace(Config.QRCODEURL, Config.emailQrcodeUrl);
		}
		// Processando
		// System.out.println("users " + users.size());
		for (User u : users) {
			try {
				String userEmail = u.getOfficialEmail();
				String userEmailSecondary = u.getSecondaryEmail();
				String userEmailPrimary = userEmail;
				if (Validator.isPreventEmailProject(userEmail) && Validator.isValidEmail(userEmailSecondary)) {
					userEmailPrimary = userEmailSecondary;
				}
				String userDeviceToken = u.getGcmId();
				String userDevice = u.getDevice();
				String userDeviceOS = Validator.getDeviceOS(userDevice);
				String userName = u.getName();
				String qrCodeToken = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
				boolean isGuest = u.isGuest();
				// Check
				if (!Validator.isEmptyString(emailBody)) {
					emailBody = emailBody.replace(Config.QRCODETOKEN, qrCodeToken);
				} else {
					emailBody = qrCodeToken;
				}
				// Logs
				MailingLogsBuilder mqb = new MailingLogsBuilder();
				mqb.setAll(t.getId(), u.getId(), userEmailPrimary, userDevice, userDeviceToken, userDeviceOS, emailId, emailTitle, emailBody);
				mqb.setUserDevicePushTypeId(PANotification.Type.NEW_TASK);
				mqb.setUserName(userName);
				mqb.setQrCodeToken(qrCodeToken);
				mqb.setQrCodeUsed(false);
				if (p != null) {
					mqb.setPushNotificationId(p.getId());
					System.out.println(String.format("%s %s %s", " setPushNotificationId ", userEmail, p.getId()));
				}
				System.out.println(String.format("%s %s %s", u.getId(), userEmail, userDeviceToken));
				// Emails
				if (!Validator.isEmptyString(userDeviceToken) && !isOnlyEmail) {
					System.out.println("userDeviceToken");
					mqb.setAccepted(true);
					mqb.setRejected(false);
					mqb.setProcessed(false);
					mqb.setDelivered(false);
					mqb.setDropped(false);
					mqb.setResend(false);
					mqb.setQueued(true);
					mqb.setPushed(true);
					mqb.setDeliveryDate(new DateTime().minusHours(3));
				} else if (isGuest) {
					System.out.println("isGuest");
					if (Validator.isPreventEmailProject(userEmailPrimary)) {
						System.out.println("isGuest userEmailPrimary");
						mqb.setAccepted(false);
						mqb.setQueued(false);
						mqb.setRejected(true);
						mqb.setDropped(true);
						mqb.setDeliveryDate(new DateTime().minusHours(3));
						totalFailed++;
					} else if (hasEmail) {
						System.out.println("isGuest Else");
						mqb.setAccepted(true);
						mqb.setQueued(true);
						mqb.setRejected(false);
						mqb.setDropped(false);
						mqb.setDeliveryDate(DeliveryDate);
						totalSubmitted++;
						// Increment
						limitCount++;
						if (limitCount >= limitSending) {
							// DeliveryDate =
							// DeliveryDate.plusSeconds(limitTime.intValue()).minusMinutes(isOnlyEmail?170:180);
							DeliveryDate = DeliveryDate.plusSeconds(limitTime.intValue()).minusMinutes(180);
							limitCount = 1L;
						}
					}

					mqb.setProcessed(false);
					mqb.setDelivered(false);
					mqb.setResend(false);
					mqb.setPushed(false);
				} else if (hasEmail) {
					System.out.println("hasEmail");
					if (Validator.isPreventEmailProject(userEmailPrimary)) {
						System.out.println("hasEmail isPreventEmailProject");
						mqb.setAccepted(false);
						mqb.setQueued(false);
						mqb.setRejected(true);
						mqb.setDropped(true);
						mqb.setDeliveryDate(new DateTime().minusHours(3));
						totalFailed++;
					} else {
						mqb.setAccepted(true);
						mqb.setQueued(true);
						mqb.setRejected(false);
						mqb.setDropped(false);

						mqb.setProcessed(false);
						mqb.setDelivered(false);
						mqb.setResend(false);
						mqb.setPushed(false);
						mqb.setDeliveryDate(DeliveryDate);
						totalSubmitted++;
						// Increment
						limitCount++;
						if (limitCount >= limitSending) {
							// DeliveryDate =
							// DeliveryDate.plusSeconds(limitTime.intValue()).minusMinutes(isOnlyEmail?170:180);
							DeliveryDate = DeliveryDate.plusSeconds(limitTime.intValue()).minusMinutes(180);
							limitCount = 1L;
						}
					}
				} else {
					System.out.println("Else");
					mqb.setAccepted(false);
					mqb.setRejected(true);
					mqb.setProcessed(false);
					mqb.setDelivered(false);
					mqb.setDropped(true);
					mqb.setResend(false);
					mqb.setQueued(false);
					mqb.setPushed(false);
					mqb.setDeliveryDate(new DateTime().minusHours(3));
					totalFailed++;
				}
				// Build
				MailingLogs m = mqb.build(true);
				mailingQueueService.saveOrUpdate(m);
				System.out.println("inviteScheduleMailSending " + m.getUserEmail() + " " + m.getUserId());
				// Stats
				if (p != null) {
					// Stats
					p.setTotalProcessed(Long.valueOf(users.size()));
					p.setTotalSubmitted(totalSubmitted);
					p.setTotalFailed(totalFailed);
					p.setQueue(true);
					pushNotificationsService.saveOrUpdate(p);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(System.out);
			}
		}
	}

	/**
	 * Envio de email
	 * 
	 * @param Subject,
	 *            Assunto do email
	 * @param setFrom,
	 *            Remetente
	 * @param addAddress,
	 *            Destinatario
	 * @param body,
	 *            This is the HTML message body <b>in bold!</b>
	 * @param altBody,
	 *            This is the body in plain text for non-HTML mail clients
	 * @return
	 * @throws EmailException
	 */
	@Async
	public void senderHtml(String subject, String setFrom, String addAddress, String body, String altBody) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setCharset(EmailConstants.UTF_8);
		email.setFrom(setFrom);
		email.setSmtpPort(this.emailSmtpPort);
		email.setHostName(this.emailHostName);
		email.setAuthenticator(new DefaultAuthenticator(this.emailAuthenticatorUsername, this.emailAuthenticatorPassword));
		email.setSSLOnConnect(this.emailSSLOnConnect);
		email.setSubject(subject);
		email.setHtmlMsg(body);
		email.addTo(addAddress);
		// email.setTextMsg(altBody);
		String messageId = email.send();
		System.out.printf("senderHtml %s >> %s", setFrom, addAddress);
	}

	/**
	 * Envio de plaintext
	 * 
	 * @param subject
	 * @param setFrom
	 * @param addAddress
	 * @param body
	 * @throws MessagingException
	 */
	@Async
	public void senderPlainText(String subject, String setFrom, String addAddress, String body) throws MessagingException {
		// Setup
		Properties prop = new Properties();
		prop.put("mail.smtp.host", Config.emailHostName);
		prop.put("mail.smtp.port", Config.emailSmtpPort);
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.ssl.enable", Config.emailSSLOnConnect);
		prop.put("mail.debug", Config.emailDebug);
		prop.setProperty("mail.user", Config.emailAuthenticatorUsername);
		prop.setProperty("mail.password", Config.emailAuthenticatorPassword);
		Session session = Session.getDefaultInstance(prop);
		// Maailer
		InternetAddress fromAddress = new InternetAddress(setFrom);
		InternetAddress toAddress = new InternetAddress(addAddress);

		Message message = new MimeMessage(session);
		message.setFrom(fromAddress);
		message.setRecipient(Message.RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setText(body);
		Transport.send(message);
	}
}
