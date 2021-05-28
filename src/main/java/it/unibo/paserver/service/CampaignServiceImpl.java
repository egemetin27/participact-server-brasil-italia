package it.unibo.paserver.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
public class CampaignServiceImpl implements CampaignService {

	@Autowired
	TaskRepository repos;

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findByIdAndParentId(long id, long parentId) {
		return repos.findByIdAndParentId(id, parentId);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findById(long id) {
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findByIdAndDetach(long id) {

		return repos.findByIdAndDetach(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> getTotalByState(long id) {
		return repos.getTotalByState(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Task> searchList(ListMultimap<String, Object> params, PageRequest pageable) {
		return repos.searchList(params, pageable);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> getTotalByStateAndUser(long userId, DateTime startDate, DateTime endDate) {
		return repos.getTotalByUserAndState(userId, startDate, endDate);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> getTotalByState(ListMultimap<String, Object> params) {
		return repos.getTotalByState(params);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> getTotalByStateAndTask(ListMultimap<String, Object> params) {
		return repos.getTotalByStateAndTask(params);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest) {
		return this.search(params, "Status", false, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest) {
		return repos.search(params, sortField, ascending, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long searchTotal(ListMultimap<String, Object> params) {
		return repos.searchTotal(params);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> searchWpPublishPage(String search, PageRequest pagerequest) {
		return repos.searchWpPublishPage(search, pagerequest);
	}
}