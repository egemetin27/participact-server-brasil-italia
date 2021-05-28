package it.unibo.paserver.service;

import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueReport;
import it.unibo.paserver.repository.IssueReportRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IssueReportServiceImpl implements IssueReportService {
    @Autowired
    private IssueReportRepository repos;

    @Override
    @Transactional(readOnly = false)
    public IssueReport saveOrUpdate(IssueReport ir) {
        
        return repos.saveOrUpdate(ir);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueReport findById(long id) {
        
        return repos.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueReport find(long id) {
        
        return repos.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueReport> findAll() {
        
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
    public void incrementNegativeScore(Long reportId) {
        
        repos.incrementNegativeScore(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest) {
        return repos.search(params, pagerequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueReport> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueReport> fetchAll(String city) {
        return repos.fetchAll(city);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueReport findByPublicProtocol(String numProtocolo) {
        return repos.findByPublicProtocol(numProtocolo);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueReport findByPrivateProtocol(Integer numProtocolo) {
        return repos.findByPrivateProtocol(numProtocolo);
    }

    @Override
    public List<Object[]> searchByNativeQuery(ListMultimap<String, Object> params, PageRequest pagerequest) {
        return repos.searchByNativeQuery(params, pagerequest);
    }

    @Override
    @Transactional(readOnly = false)
    public List<IssueReport> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
        
        return repos.filter(params, pagerequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Long searchTotal(ListMultimap<String, Object> params) {
        
        return repos.searchTotal(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> searchByStats(ListMultimap<String, Object> params, DateTime queryStart, DateTime queryEnd, PageRequest pagerequest) {
        
        return repos.searchByStats(params, queryStart, queryEnd, pagerequest);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Object[]> searchByStatsGrouped(ListMultimap<String, Object> params, DateTime queryStart, DateTime queryEnd, PageRequest pagerequest) {
        
        return repos.searchByStatsGroupd(params, queryStart, queryEnd, pagerequest);
    }
}
