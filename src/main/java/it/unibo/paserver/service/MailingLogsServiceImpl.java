package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.MailingLogs;
import it.unibo.paserver.repository.MailingLogsRepository;

@Service
@Transactional(readOnly = true)
public class MailingLogsServiceImpl implements MailingLogsService {
	@Autowired
	MailingLogsRepository repos;
	
	@Override
	@Transactional(readOnly = false)
	public MailingLogs saveOrUpdate(MailingLogs t) {
		
		return repos.saveOrUpdate(t);
	}

	@Override
	@Transactional(readOnly = true)
	public MailingLogs findByTaskId(long TaskId) {
		
		return repos.findByTaskId(TaskId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteByTaskId(long TaskId) {
		
		return repos.deleteByTaskId(TaskId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MailingLogs> findAllByTaskId(long TaskId) {
		
		return repos.findAllByTaskId(TaskId);
	}

	@Override
	public List<MailingLogs> findAllByEmailId(Long emailId, Long count, boolean isPushed) {
		
		return repos.findAllByEmailId(emailId, count, isPushed);
	}
	
	@Override
	public List<MailingLogs> findAllByEmailId(Long emailId, Long limitSending) {
		
		return repos.findAllByEmailId(emailId, limitSending);
	}

	@Override
	public List<MailingLogs> findAllResendByEmailId(Long emailId, Long limitSending) {
		
		return repos.findAllResendByEmailId(emailId, limitSending);
	}

	@Override
	public List<MailingLogs> findAllTaskIdAndUserId(long taskId, long userId) {
		
		return repos.findAllTaskIdAndUserId(taskId, userId);
	}

	@Override
	public MailingLogs findByQrCoded(String qrcode, boolean qrused) {
		return repos.findByQrCoded(qrcode, qrused);
	}
}
