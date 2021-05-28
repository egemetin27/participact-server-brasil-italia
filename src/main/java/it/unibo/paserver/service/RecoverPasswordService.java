package it.unibo.paserver.service;

import it.unibo.paserver.domain.RecoverPassword;

import java.util.List;

import org.joda.time.DateTime;

public interface RecoverPasswordService {

	RecoverPassword findById(long id);

	RecoverPassword findByToken(String token);

	List<RecoverPassword> findByUser(long userId);

	List<RecoverPassword> findExpiredAt(DateTime instant);

	RecoverPassword save(RecoverPassword recoverPassword);

	boolean delete(long id);

}
