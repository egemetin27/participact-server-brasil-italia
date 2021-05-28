package it.unibo.paserver.repository;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

public interface DataRepository {

	Data findById(long id);

	<T extends Data> T save(T data);

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

	<T extends Data> List<T> getDataByUserAndTask(long userId, long taskId, Class<T> clazz);

	List<Data> getDataByTaskReport(long taskReportId);

	<T extends Data> List<T> getDataByTaskReport(long taskReportId, Class<T> clazz);

	boolean deleteData(long id);

	BinaryDocument getPhotoContent(Long id);

	public void flush();

	public void clear();

	<T extends Data> T merge(T data);

	List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId);

	<T extends Data> List<T> search(Class<T> clazz, DateTime startDate, DateTime endDate, long userId, ListMultimap<String, Object> params, PageRequest pageable);

	<T extends Data> Long searchTotal(Class<T> clazz, DateTime startDate, DateTime endDate, long userId,ListMultimap<String, Object> params);

	List<DataPhoto> searchActionPhoto(long taskId, long userId, long actionId, PageRequest pagerequest);

	Long searchActionPhotoTotal(long taskId, long userId, long actionId);

	List<Object[]> searchActionQuestionClosed(long actionId);
	
	List<Object[]> searchActionQuestionOpen(long actionId);

	List<Object[]> searchToChart(Class<? extends Data> forName, DateTime start, DateTime deadline, long userId, String groupBy);
}
