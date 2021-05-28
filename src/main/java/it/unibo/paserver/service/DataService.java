package it.unibo.paserver.service;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

public interface DataService {

	Data findById(long id);

	<T extends Data> T save(T data);

	<T extends Data> T merge(T data);

	<T extends Data> List<T> save(Iterable<T> dataSet);

	Long getDataCount();

	Long getDataCountByUser(long userId);

	Long getDataCountByTask(long taskId);

	List<Data> getData();

	<T extends Data> List<T> getData(Class<T> clazz);

	List<Data> getDataByUser(long userId);

	<T extends Data> List<T> getDataByUser(long userId, Class<T> clazz);

	List<Data> getDataByTask(long taskId);

	<T extends Data> List<T> getDataByTask(long taskId, Class<T> clazz);

	List<Data> getDataByUserAndTask(long userId, long taskId);

	<T extends Data> List<T> getDataByUserAndTask(long userId, long taskId,
			Class<T> clazz);

	List<Data> getDataByTaskReport(long taskReportId);

	<T extends Data> List<T> getDataByTaskReport(long taskReportId,
			Class<T> clazz);

	boolean deleteData(long id);

	public BinaryDocument getPhotoContent(Long id);

	public List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId);

	<T extends Data> List<T> search(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params, PageRequest pageable);

	<T extends Data> Long searchTotal(Class<T> clazz, DateTime startDate, DateTime endDate, long userId,ListMultimap<String, Object> params);

	List<DataPhoto> searchActionPhoto(long taskId, long userId, long actionId, PageRequest pagerequest);
	
	Long searchActionPhotoTotal(long taskId, long userId, long actionId);

	List<Object[]> searchActionQuestionClosed(Long id);
	
	List<Object[]> searchActionQuestionOpen(Long id);

	List<Object[]> searchToChart(Class<? extends Data> forName, DateTime start, DateTime deadline, long userId, String groupBy);
}
