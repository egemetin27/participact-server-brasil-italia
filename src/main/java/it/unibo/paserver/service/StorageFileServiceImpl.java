package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.StorageFile;
import it.unibo.paserver.repository.StorageFileRepository;

@Service
@Transactional(readOnly = true)
public class StorageFileServiceImpl implements StorageFileService {
	@Autowired
	private StorageFileRepository repos;

	@Override
	@Transactional(readOnly = false)
	public StorageFile saveOrUpdate(StorageFile s) {
		
		return repos.saveOrUpdate(s);
	}

	@Override
	@Transactional(readOnly = true)
	public StorageFile findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StorageFile> findAll() {
		
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
	@Transactional(readOnly = true)
	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.search(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = false)
	public List<StorageFile> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		return repos.searchTotal(params);
	}

	@Override
	public List<Object[]> searchAllFeedback(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.searchAllFeedback(params, pagerequest);
	}

}
