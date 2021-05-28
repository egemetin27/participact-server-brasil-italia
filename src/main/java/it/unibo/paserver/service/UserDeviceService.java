package it.unibo.paserver.service;

import java.security.Principal;
import java.util.List;

import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;


public interface UserDeviceService {
	
	UserDevice save(UserDevice userDevice);
	
	boolean deleteUserDevice(long id);
	
	UserDevice getUserDevice(Long id);
	
	List<UserDevice> getUserDevices();

	boolean existImei(String imei, long id);
	
	boolean existImeiUser(User user, String imei);

	boolean existPriority(long userId, long priority, long id);

	List<UserDevice> getActivePrymaryUserDevices();

	List<UserDevice> selectUserDevices(Platform platform, Long deviceId, Boolean isPrimaryUD, String user);

	List<UserDevice> selectNewUserDevices(Platform platform, Long deviceId,
			Boolean isPrimaryUD, String user, Task task);

	UserDevice getUserDevice(String imei);

	Object getUserDevicesByUser(User user);

	UserDevice getUserDeviceByPrincipal(Principal principal);

	long maxPriorityAssignable(User user);
}
