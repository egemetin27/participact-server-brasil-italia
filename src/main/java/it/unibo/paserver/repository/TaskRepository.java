package it.unibo.paserver.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskValutation;

public interface TaskRepository {

	Task findById(long id);

	Task save(Task task);

	Long getTasksCount();

	Long getTasksCount(long userId);

	List<Task> getTasks();

	List<Task> getTasksByUser(long userId);

	List<Task> getTasksByUser(long userId, TaskState taskCurrentState);

	List<Task> getTasksByAction(long actionId);

	List<Task> getTasksByOwner(long ownerId);

	List<Task> getTasksByOwner(long ownerId, TaskValutation currentTaskValutation);

	boolean deleteTask(long id);

	List<Task> getAvailableTasksByUser(long userId, TaskState taskCurrentState, DateTime dateTime);

	List<Task> getTaskByAdmin();

	Task findByIdAndOwner(Long taskId, Long ownerId);

	List<Task> getAvailableAdminTasksByUser(Long id, TaskState taskState, DateTime dateTime);

	List<Task> getAvailableUserTasksByUser(Long id, TaskState taskState, DateTime dateTime);

	List<Task> getAdminTasksByUser(Long userId, TaskState taskState);

	List<Task> getUserTasksByUser(Long userId, TaskState taskState);

	List<Task> getTasksByDate(DateTime start, DateTime end);

	List<Task> getTasksUserByDate(DateTime start, DateTime end);

	List<Task> getTaskByAdmin(DateTime start, DateTime end);

	List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	boolean removed(long id);

	Task findByIdAndParentId(long id, long parentId);

	List<Object[]> getTotalByState(long id);

	List<Task> searchList(ListMultimap<String, Object> params, PageRequest pageable);
	
	List<Object[]> getTotalByUserAndState(long userId, DateTime startDate, DateTime endDate);

	List<Object[]> getTotalByState(ListMultimap<String, Object> params);

	List<Object[]> getTotalByStateAndTask(ListMultimap<String, Object> params);

	Task findByIdAndDetach(long id);

	List<Object[]> searchWpPublishPage(String search, PageRequest pagerequest);

	List<Task> fetchAllByRemoved(PageRequest pagerequest, DateTime updateDate);

    Task findByQrCode(String token);
}
