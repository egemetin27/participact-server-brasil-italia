package it.unibo.paserver.service;

import it.unibo.paserver.domain.AudienceSelector;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserListTask;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserListTaskService {
    UserListTask saveOrUpdate(UserListTask t);

    List<UserListTask> findAll();

    UserListTask find(Long id);

    UserListTask findUserListTask(Long taskId);

    Task findUserTask(Long taskId);

    UserListTask addUserListTask(UserListTask userListTask);

    List<Task> fetchAllAndPublic(DateTime updateDate, PageRequest pageable);

    List<UserListTask> fetchAllSelector(AudienceSelector audienceSelector, DateTime updateDate, PageRequest pagerequest);

    List<Task> fetchAllAndClosed(Long userId, DateTime updateDate, PageRequest pagerequest);

    Task findLastUpdate();

    Task findLastUpdateRemoved();

    List<Task> fetchAllAndPublicPublish(DateTime updateDate, PageRequest pagerequest);
}