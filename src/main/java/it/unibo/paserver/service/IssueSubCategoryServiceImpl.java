package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueSubCategory;
import it.unibo.paserver.repository.IssueSubCategoryRepository;

@Service
@Transactional(readOnly = true)
public class IssueSubCategoryServiceImpl implements IssueSubCategoryService {
	@Autowired
	IssueSubCategoryRepository repos;

	@Override
	@Transactional(readOnly = false)
	public IssueSubCategory saveOrUpdate(IssueSubCategory is) {
		
		return repos.saveOrUpdate(is);
	}

	@Override
	@Transactional(readOnly = true)
	public IssueSubCategory findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<IssueSubCategory> findAll() {
		
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
	@Transactional(readOnly = false)
	public boolean removeAll(Long categoryId) {
		return repos.removeAll(categoryId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean reorder(long id, int index) {
		return repos.reorder(id, index);
	}

	@Override
	public List<Object[]> getTotalBySubCategory() {
		return repos.getTotalBySubCategory();
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
	public List<IssueSubCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}

}
