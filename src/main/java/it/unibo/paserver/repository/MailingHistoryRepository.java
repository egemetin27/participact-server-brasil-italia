package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.MailingHistory;

public interface MailingHistoryRepository {

	public MailingHistory saveOrUpdate(MailingHistory t);

	public MailingHistory findByTaskId(long taskId);

	public boolean deleteByTaskId(long taskId);

	public List<MailingHistory> findAllByTaskId(long taskId);

	public List<MailingHistory> findAll();

	public MailingHistory find(long id);

}
