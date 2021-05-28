package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.MailingHistory;
import it.unibo.paserver.repository.MailingHistoryRepository;

@Service
@Transactional(readOnly = true)
public class MailingHistoryServiceImpl implements MailingHistoryService {
	@Autowired
	MailingHistoryRepository repos;

	@Override
	@Transactional(readOnly = false)
	public MailingHistory saveOrUpdate(MailingHistory t) {
		
		return repos.saveOrUpdate(t);
	}

	@Override
	@Transactional(readOnly = true)
	public MailingHistory findByTaskId(long TaskId) {
		
		return repos.findByTaskId(TaskId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteByTaskId(long TaskId) {
		
		return repos.deleteByTaskId(TaskId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MailingHistory> findAllByTaskId(long TaskId) {
		
		return repos.findAllByTaskId(TaskId);
	}

	@Override
	public List<MailingHistory> findAll() {
		
		return repos.findAll();
	}

	@Override
	public MailingHistory find(long id) {
		
		return repos.find(id);
	}

}
