package it.unibo.paserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.UserSecretKey;
import it.unibo.paserver.repository.UserSecretKeyRepository;

@Service
@Transactional(readOnly = true)
public class UserSecretKeyServiceImpl implements UserSecretKeyService {
	@Autowired
	UserSecretKeyRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserSecretKey saveOrUpdate(UserSecretKey i) {
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public UserSecretKey find(Long id) {
		return repos.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean doCheck(Long userId, UUID uuid) {

		return repos.doCheck(userId, uuid);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateProgenitorId(Long userId, Long progenitorId) {

		return repos.updateProgenitorId(userId, progenitorId);
	}
}