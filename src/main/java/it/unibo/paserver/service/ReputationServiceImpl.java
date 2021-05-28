package it.unibo.paserver.service;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Level;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.ReputationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ReputationService using ReputationRepository.
 * 
 * @author danielecampogiani
 * @see ReputationService
 * @see ReputationRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class ReputationServiceImpl implements ReputationService {

	@Autowired
	ReputationRepository reputationRepository;

	@Override
	@Transactional(readOnly = false)
	public Reputation save(Reputation reputation) {
		return reputationRepository.save(reputation);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Reputation> getReputationsByUser(long userId) {
		return reputationRepository.getReputationsByUser(userId);
	}

	@Override
	@Transactional(readOnly = false)
	public Reputation getReputationByUserAndActionType(long userId,
			ActionType actionType) {
		return reputationRepository.getReputationByUserAndActionType(userId,
				actionType);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Level getLevelByUserAndActionType(long userId, ActionType actionType) {
		Reputation reputation = getReputationByUserAndActionType(userId,actionType);
		return it.unibo.paserver.domain.Level.getLevelFromReputation(reputation);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Reputation> getReputations() {
		return reputationRepository.getReputations();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getReputationsCount() {
		return reputationRepository.getReputationsCount();
	}

	@Override
	@Transactional(readOnly = false)
	public Reputation create(User user, ActionType actionType, int value) {
		return reputationRepository.create(user, actionType, value);
	}

}
