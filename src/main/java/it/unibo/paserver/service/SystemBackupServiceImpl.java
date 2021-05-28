package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.SystemBackup;
import it.unibo.paserver.repository.SystemBackupRepository;

@Service
@Transactional(readOnly = true)
public class SystemBackupServiceImpl implements SystemBackupService {
	@Autowired
	SystemBackupRepository repos;

	@Override
	@Transactional(readOnly = false)
	public SystemBackup saveOrUpdate(SystemBackup b) {
		
		return repos.saveOrUpdate(b);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemBackup> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SystemBackup findById(long id) {
		
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
}