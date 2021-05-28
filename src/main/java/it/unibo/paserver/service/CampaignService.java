package it.unibo.paserver.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Task;

public interface CampaignService {
	public List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	public Long searchTotal(ListMultimap<String, Object> params);

	public boolean removed(long id);

	Task findByIdAndParentId(long id, long parentId);
	
	Task findById(long id);

	public List<Object[]> getTotalByState(long id);
	
	public List<Object[]> getTotalByState(ListMultimap<String, Object> params);

	public List<Task> searchList(ListMultimap<String, Object> params, PageRequest pagerequest);

	public List<Object[]> getTotalByStateAndUser(long id, DateTime startDate, DateTime endDate);

	public List<Object[]> getTotalByStateAndTask(ListMultimap<String, Object> params);

	public List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest);

	public Task findByIdAndDetach(long id);

	public List<Object[]> searchWpPublishPage(String search, PageRequest pagerequest);
}