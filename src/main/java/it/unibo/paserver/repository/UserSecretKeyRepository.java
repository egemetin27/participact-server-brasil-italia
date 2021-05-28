package it.unibo.paserver.repository;

import java.util.UUID;

import it.unibo.paserver.domain.UserSecretKey;

public interface UserSecretKeyRepository {

	public UserSecretKey saveOrUpdate(UserSecretKey k);
	public UserSecretKey find(Long userId);
	public boolean doCheck(Long userId, UUID uuid);
	public boolean updateProgenitorId(Long userId, Long progenitorId);
}