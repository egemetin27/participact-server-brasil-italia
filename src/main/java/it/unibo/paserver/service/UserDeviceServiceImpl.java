package it.unibo.paserver.service;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;
import it.unibo.paserver.repository.UserDeviceRepository;
import it.unibo.paserver.web.security.v1.AccountUserDeviceDetails;

@Service
@Transactional(readOnly = true)
public class UserDeviceServiceImpl implements UserDeviceService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(UserDeviceServiceImpl.class);
	
	@Autowired
	UserDeviceRepository userDeviceRepository;
	
	@Override
	@Transactional(readOnly = false)
	public UserDevice save(UserDevice userDevice) {
		return userDeviceRepository.save(userDevice);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteUserDevice(long id) {
		return userDeviceRepository.deleteUserDevice(id);
	}

	@Override
	public UserDevice getUserDevice(Long id) {
		return userDeviceRepository.findById(id);
	}
	
	@Override
	public List<UserDevice> getUserDevices() {
		return userDeviceRepository.getUserDevices();
	}

	@Override
	public boolean existImei(String imei, long id) {
		return userDeviceRepository.existImei(imei,id); 
	}
	
	@Override
	public boolean existImeiUser(User user, String imei) {
		return userDeviceRepository.existImeiUser(user, imei);
	}

	@Override
	public boolean existPriority(long userId, long priority, long id) {
		return userDeviceRepository.existPriority(userId,priority,id);
	}

	@Override
	public List<UserDevice> getActivePrymaryUserDevices() {
		return userDeviceRepository.getActivePrimaryUserDevices();
	}

	@Override
	public List<UserDevice> selectUserDevices(Platform platform, Long deviceId, Boolean isPrimaryUD, String user) {
		return userDeviceRepository.selectUserDevices(platform, deviceId, isPrimaryUD, user);
	}

	@Override
	public List<UserDevice> selectNewUserDevices(Platform platform,
			Long deviceId, Boolean isPrimaryUD, String user, Task task) {
		return userDeviceRepository.selectNewUserDevices(platform, deviceId, isPrimaryUD, user, task);
	}

	@Override
	public UserDevice getUserDevice(String imei) {
		return userDeviceRepository.findByImei(imei);
	}

	@Override
	public Object getUserDevicesByUser(User user) {
		return userDeviceRepository.getUserDevicesByUser(user);
	}

	@Override
	public UserDevice getUserDeviceByPrincipal(Principal principal) {
		UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
	    String imei = ((AccountUserDeviceDetails)currentUser).getImei();
	    UserDevice userDevice = getUserDevice(imei);
		return userDevice;
	}

	@Override
	public long maxPriorityAssignable(User user) {
        long priority = 99;
        long userid = user.getId();
        while (priority >= 0 && userDeviceRepository.existPriority(userid,priority,0)) {
        	priority--;
        }
		return priority;
	}

}
