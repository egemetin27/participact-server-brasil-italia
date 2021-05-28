package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.User;

public interface ParticipantService {

	public User findById(long id);

	public boolean removed(long id);

	public User saveOrUpdate(User u);

	public User findByEmail(String officialEmail);

	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);
	
	public List<User> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	public Long searchTotal(ListMultimap<String, Object> params);
	
	public User findParticipantById(long id);

	public Long getUserCount();
}