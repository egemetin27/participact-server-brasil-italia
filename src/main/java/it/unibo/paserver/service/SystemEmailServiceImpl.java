package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.SystemEmail;
import it.unibo.paserver.repository.SystemEmailRepository;

@Service
@Transactional(readOnly = true)
public class SystemEmailServiceImpl implements SystemEmailService {
	@Autowired
	SystemEmailRepository repos;

	@Override
	@Transactional(readOnly = false)
	public SystemEmail saveOrUpdate(SystemEmail p) {
		
		return repos.saveOrUpdate(p);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemEmail> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemEmail> findAllNotProcessing() {
		
		return repos.findAllNotProcessing();
	}

	@Override
	@Transactional(readOnly = true)
	public SystemEmail findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(PageRequest pagerequest) {
		
		return repos.search(pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long searchTotal() {
		
		return repos.searchTotal();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		
		return repos.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean cleanProcessed() {
		
		return repos.cleanProcessed();
	}
}