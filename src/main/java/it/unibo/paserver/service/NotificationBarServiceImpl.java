package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.NotificationBar;
import it.unibo.paserver.repository.NotificationBarRepository;

@Service
@Transactional(readOnly = true)
public class NotificationBarServiceImpl implements NotificationBarService {
	@Autowired
	NotificationBarRepository repos;

	@Override
	@Transactional(readOnly = true)
	public List<NotificationBar> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public NotificationBar saveOrUpdate(NotificationBar n) {
		
		return repos.saveOrUpdate(n);
	}

	@Override
	@Transactional(readOnly = true)
	public NotificationBar findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean unread(long id) {
		
		return repos.unread(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(long parentId, PageRequest pagerequest) {
		
		return repos.search(parentId, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long searchTotal(long parentId) {
		
		return repos.searchTotal(parentId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean unreadAll(long parentId) {
		
		return repos.unreadAll(parentId);
	}
}