package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.MailingHistory;

public interface MailingHistoryService {
	MailingHistory saveOrUpdate(MailingHistory t);

	MailingHistory findByTaskId(long TaskId);

	boolean deleteByTaskId(long TaskId);

	List<MailingHistory> findAllByTaskId(long TaskId);

	List<MailingHistory> findAll();

	MailingHistory find(long id);
}
