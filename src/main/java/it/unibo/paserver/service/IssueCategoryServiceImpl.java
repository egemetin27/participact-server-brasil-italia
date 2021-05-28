package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueCategory;
import it.unibo.paserver.repository.IssueCategoryRepository;

@Service
@Transactional(readOnly = true)
public class IssueCategoryServiceImpl implements IssueCategoryService {
    @Autowired
    IssueCategoryRepository repos;

    @Override
    @Transactional(readOnly = false)
    public IssueCategory saveOrUpdate(IssueCategory ic) {
        
        return repos.saveOrUpdate(ic);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueCategory findById(long id) {
        
        return repos.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueCategory> findAll() {
        
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
    public boolean removeAll() {
        
        return repos.removeAll();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean reorder(long id, int index) {
        return repos.reorder(id, index);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getTotalByCategory() {
        return repos.getTotalByCategory();
    }

    @Override
    public Long getTotalByCategoryDatePart(Long id, int year, int monthOfYear) {
        return repos.getTotalByCategoryDatePart(id, year, monthOfYear);
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
    public List<IssueCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
        
        return repos.filter(params, pagerequest);
    }


}
