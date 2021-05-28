package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.MailingLogs;

public interface MailingLogsRepository {

	public MailingLogs saveOrUpdate(MailingLogs t);

	public MailingLogs findByTaskId(long taskId);
	
	public MailingLogs findByQrCoded(String qrcode, boolean qrused);	

	public boolean deleteByTaskId(long taskId);

	public List<MailingLogs> findAllByTaskId(long taskId);

	public List<MailingLogs> findAllByEmailId(Long emailId, Long count, boolean isPushed);

	public List<MailingLogs> findAllResendByEmailId(Long emailId, Long limitSending);

	public List<MailingLogs> findAllTaskIdAndUserId(long taskId, long userId);

	List<MailingLogs> findAllByEmailId(Long emailId, Long count);
}
