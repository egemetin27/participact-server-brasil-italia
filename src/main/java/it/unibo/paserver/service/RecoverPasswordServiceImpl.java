package it.unibo.paserver.service;

import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.repository.RecoverPasswordRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecoverPasswordServiceImpl implements RecoverPasswordService {

	@Autowired
	private RecoverPasswordRepository recoverPasswordRepository;

	@Override
	@Transactional(readOnly = true)
	public RecoverPassword findById(long id) {
		return recoverPasswordRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public RecoverPassword findByToken(String token) {
		try {
			return recoverPasswordRepository.findByToken(token);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RecoverPassword> findByUser(long userId) {
		return recoverPasswordRepository.findByUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RecoverPassword> findExpiredAt(DateTime instant) {
		return recoverPasswordRepository.findExpiredAt(instant);
	}

	@Override
	@Transactional(readOnly = false)
	public RecoverPassword save(RecoverPassword recoverPassword) {
		return recoverPasswordRepository.save(recoverPassword);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		return recoverPasswordRepository.delete(id);
	}

}
