package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.MailingLogs;

public interface MailingLogsService {
	MailingLogs saveOrUpdate(MailingLogs m);

	MailingLogs findByTaskId(long TaskId);

	boolean deleteByTaskId(long TaskId);

	List<MailingLogs> findAllByTaskId(long TaskId);

	List<MailingLogs> findAllByEmailId(Long emailId, Long count, boolean isPushed);

	List<MailingLogs> findAllResendByEmailId(Long emailId, Long limitSending);

	List<MailingLogs> findAllTaskIdAndUserId(long taskId, long userId);

	MailingLogs findByQrCoded(String qrcode, boolean qrused);

	List<MailingLogs> findAllByEmailId(Long emailId, Long limitSending);
}
