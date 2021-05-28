package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.UserMessage;
import it.unibo.paserver.repository.UserMessageRepository;

@Service
@Transactional(readOnly = true)
public class UserMessageServiceImpl implements UserMessageService {
	@Autowired
	UserMessageRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserMessage saveOrUpdate(UserMessage m) {
		return repos.saveOrUpdate(m);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public UserMessage findById(long id) {
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean readByUserId(long userId) {
		return repos.readByUserId(userId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> fetchAllUnread(Long userId) {
		return repos.fetchAllUnread(userId);
	}

	@Override
	public List<UserMessage> fetchAllUnread(long userId, boolean isRead) {

		return repos.fetchAllUnread(userId, isRead);
	}

}
