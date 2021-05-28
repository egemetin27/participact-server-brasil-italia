package it.unibo.paserver.service;

import java.util.UUID;

import it.unibo.paserver.domain.UserSecretKey;

public interface UserSecretKeyService {
	UserSecretKey saveOrUpdate(UserSecretKey k);
	UserSecretKey find(Long userId);
	boolean doCheck(Long userId, UUID uuid);
	boolean updateProgenitorId(Long userId, Long progenitorId);
}