package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.participact.domain.PANotification.Type;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.repository.PushNotificationsRepository;

@Service
@Transactional(readOnly = true)
public class PushNotificationsServiceImpl implements PushNotificationsService {
	@Autowired
	PushNotificationsRepository repos;
	@Override
	@Transactional(readOnly = true)
	public PushNotifications findByType(Type t) {
		
		return repos.findByType(t);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PushNotifications> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public PushNotifications saveOrUpdate(PushNotifications p) {
		
		return repos.saveOrUpdate(p);
	}

	@Override
	@Transactional(readOnly = true)
	public PushNotifications findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(PageRequest pagerequest, boolean isMail) {
		
		return repos.search(pagerequest, isMail);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long searchTotal(boolean isMail) {
		
		return repos.searchTotal(isMail);
	}
}