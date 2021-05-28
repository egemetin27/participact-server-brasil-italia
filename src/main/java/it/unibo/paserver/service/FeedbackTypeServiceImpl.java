package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.FeedbackType;
import it.unibo.paserver.repository.FeedbackTypeRepository;

@Service
@Transactional(readOnly = true)
public class FeedbackTypeServiceImpl implements FeedbackTypeService {
	@Autowired
	FeedbackTypeRepository repos;

	@Override
	@Transactional(readOnly = false)
	public FeedbackType saveOrUpdate(FeedbackType f) {
		
		return repos.saveOrUpdate(f);
	}

	@Override
	@Transactional(readOnly = true)
	public FeedbackType findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FeedbackType> findAll() {
		
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
	@Transactional(readOnly = true)
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		return repos.searchTotal(params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FeedbackType> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}

}
