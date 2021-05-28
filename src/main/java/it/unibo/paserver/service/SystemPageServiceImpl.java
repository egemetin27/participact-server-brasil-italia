package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;
import it.unibo.paserver.repository.SystemPageRepository;

@Service
@Transactional(readOnly = true)
public class SystemPageServiceImpl implements SystemPageService {
	@Autowired
	SystemPageRepository repos;

	@Override
	@Transactional(readOnly = false)
	public SystemPage saveOrUpdate(SystemPage p) {
		
		return repos.saveOrUpdate(p);
	}

	@Override
	@Transactional(readOnly = true)
	public SystemPage findByType(SystemPageType t) {
		
		return repos.findByType(t);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemPage> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SystemPage findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(PageRequest pagerequest) {
		
		return repos.search(pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long searchTotal() {
		
		return repos.searchTotal();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		
		return repos.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> fetchAllActivePages() {
		
		return repos.fetchAllActivePages();
	}
}