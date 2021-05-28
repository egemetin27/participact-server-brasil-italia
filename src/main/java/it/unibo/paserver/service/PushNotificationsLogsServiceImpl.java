package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.PushNotificationsLogs;
import it.unibo.paserver.repository.PushNotificationsLogsRepository;

@Service
@Transactional(readOnly = true)
public class PushNotificationsLogsServiceImpl implements PushNotificationsLogsService {
	@Autowired
	PushNotificationsLogsRepository repos;
	
	@Override
	@Transactional(readOnly = true)
	public List<PushNotificationsLogs> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public PushNotificationsLogs saveOrUpdate(PushNotificationsLogs l) {
		
		return repos.saveOrUpdate(l);
	}

	@Override
	@Transactional(readOnly = true)
	public PushNotificationsLogs findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pagerequest) {
		
		return repos.search(conditions, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Long searchTotal(ListMultimap<String, Object> conditions) {
		
		return repos.searchTotal(conditions);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Long filterTotal(ListMultimap<String, Object> params) {
		
		return repos.filterTotal(params);
	}
}
