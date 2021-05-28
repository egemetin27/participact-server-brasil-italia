package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ParticipantServiceImpl implements ParticipantService {
	@Autowired
	UserRepository repos;
	
	@Override
	@Transactional(readOnly = true)
	public User findById(long id) {
		
		return repos.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public User findParticipantById(long id) {
		
		return repos.findParticipantById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = false)
	public User saveOrUpdate(User u) {
		
		return repos.saveOrUpdate(u);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.search(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		return repos.searchTotal(params);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public User findByEmail(String officialEmail) {
		
		return repos.findByEmail(officialEmail);
	}

	@Override
	public Long getUserCount() {
		
		return repos.getUserCount();
	}

	@Override
	public List<User> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}
}