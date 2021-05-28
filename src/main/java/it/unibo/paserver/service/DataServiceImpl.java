package it.unibo.paserver.service;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

@Service
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {

	@Autowired
	DataRepository dataRepository;

	@Override
	public Data findById(long id) {
		return dataRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends Data> T save(T data) {
		return dataRepository.save(data);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteData(long id) {
		return dataRepository.deleteData(id);
	}

	@Override
	public List<Data> getData() {
		return dataRepository.getData();
	}

	@Override
	public Long getDataCount() {
		return dataRepository.getDataCount();
	}

	@Override
	public Long getDataCountByUser(long userId) {
		return dataRepository.getDataCountByUser(userId);
	}

	@Override
	public Long getDataCountByTask(long taskId) {
		return dataRepository.getDataCountByTask(taskId);
	}

	@Override
	public <T extends Data> List<T> getData(Class<T> clazz) {
		return dataRepository.getData(clazz);
	}

	@Override
	public List<Data> getDataByUser(long userId) {
		return dataRepository.getDataByUser(userId);
	}

	@Override
	public <T extends Data> List<T> getDataByUser(long userId, Class<T> clazz) {
		return dataRepository.getDataByUser(userId, clazz);
	}

	@Override
	public List<Data> getDataByTask(long taskId) {
		return dataRepository.getDataByTask(taskId);
	}

	@Override
	public <T extends Data> List<T> getDataByTask(long taskId, Class<T> clazz) {
		return dataRepository.getDataByTask(taskId, clazz);
	}

	@Override
	public List<Data> getDataByUserAndTask(long userId, long taskId) {
		return dataRepository.getDataByUserAndTask(userId, taskId);
	}

	@Override
	public <T extends Data> List<T> getDataByUserAndTask(long userId,
			long taskId, Class<T> clazz) {
		return dataRepository.getDataByUserAndTask(userId, taskId, clazz);
	}

	@Override
	public List<Data> getDataByTaskReport(long taskReportId) {
		return dataRepository.getDataByTaskReport(taskReportId);
	}

	@Override
	public <T extends Data> List<T> getDataByTaskReport(long taskReportId,
			Class<T> clazz) {
		return dataRepository.getDataByTaskReport(taskReportId, clazz);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends Data> List<T> save(Iterable<T> dataSet) {
		List<T> result = new ArrayList<T>();
		int i = 0;
		for (T data : dataSet) {
			T temp = dataRepository.save(data);
			result.add(temp);
			i++;
			if (i % 100 == 0) {
				dataRepository.flush();
				dataRepository.clear();
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends Data> T merge(T data) {
		return dataRepository.merge(data);
	}

	@Override
	public BinaryDocument getPhotoContent(Long id) {
		return dataRepository.getPhotoContent(id);
	}

	@Override
	public List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId) {
		List<DataPhoto> dataPhoto = dataRepository.getDataPhotoByTaskAction(
				taskId, actionId);
		for (DataPhoto dp : dataPhoto) {
			Hibernate.initialize(dp.getUser());
		}
		return dataPhoto;
	}

	@Override
	public <T extends Data> List<T> search(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params, PageRequest pageable) {
		
		return dataRepository.search(clazz, startDate, endDate, userId, params, pageable);
	}

	@Override
	public <T extends Data> Long searchTotal(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params) {
		
		return dataRepository.searchTotal(clazz, startDate, endDate, userId, params);
	}

	@Override
	public List<DataPhoto> searchActionPhoto(long taskId, long userId, long actionId, PageRequest pagerequest) {
		
		return dataRepository.searchActionPhoto(taskId, userId, actionId, pagerequest);
	}

	@Override
	public Long searchActionPhotoTotal(long taskId, long userId, long actionId) {
		
		return dataRepository.searchActionPhotoTotal(taskId, userId, actionId);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> searchActionQuestionClosed(Long actionId) {
		
		return dataRepository.searchActionQuestionClosed(actionId);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> searchActionQuestionOpen(Long actionId) {
		
		return dataRepository.searchActionQuestionOpen(actionId);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> searchToChart(Class<? extends Data> forName, DateTime start, DateTime deadline, long userId, String groupBy) {
		
		return dataRepository.searchToChart(forName, start, deadline, userId, groupBy);
	}
}