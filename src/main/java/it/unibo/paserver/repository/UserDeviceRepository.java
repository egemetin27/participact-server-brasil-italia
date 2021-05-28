package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;

public interface UserDeviceRepository {

	UserDevice findById(long id);
	
	UserDevice save(UserDevice userDevice);
	
	boolean deleteUserDevice(long id);

	List<UserDevice> getUserDevices();
	
	public boolean existImei(String imei, long id);
	
	boolean existImeiUser(User user, String imei);

	boolean existPriority(long userId, long priority, long id);

	List<UserDevice> getActivePrimaryUserDevices();

	List<UserDevice> selectUserDevices(Platform platform, Long deviceId, Boolean isPrimaryUD, String user);

	List<UserDevice> selectNewUserDevices(Platform platform, Long deviceId,
			Boolean isPrimaryUD, String user, Task task);

	UserDevice findByImei(String imei);

	Object getUserDevicesByUser(User user);

}
