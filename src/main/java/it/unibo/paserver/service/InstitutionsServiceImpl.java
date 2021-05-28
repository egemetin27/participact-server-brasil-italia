package it.unibo.paserver.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.repository.InstitutionsRepository;

@Service
@Transactional(readOnly = true)
public class InstitutionsServiceImpl implements InstitutionsService {
	@Autowired
	InstitutionsRepository repos;
	
	@Override
	@Transactional(readOnly = false)
	public Institutions saveOrUpdate(Institutions i) {
		
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public Institutions findByName(String name) {
		
		return repos.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Institutions findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Institutions> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount() {
		
		return repos.getCount();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(String name, String address, String email, String phone, PageRequest pageable) {
		
		return repos.search(name, address, email, phone, pageable);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public long searchTotal(String name, String address, String email, String phone) {
		
		return repos.searchTotal(name, address, email, phone);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Institutions> filter(ListMultimap<String, Object> params, PageRequest pageable) {
		
		return repos.filter(params, pageable);
	}

}