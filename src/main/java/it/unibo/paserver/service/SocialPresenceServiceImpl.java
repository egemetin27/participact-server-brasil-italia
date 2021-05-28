package it.unibo.paserver.service;

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.SocialPresenceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of SocialPresenceService using SocialPresenceRepository
 * 
 * @author danielecampogiani
 * @see SocialPresenceService
 * @see SocialPresenceRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class SocialPresenceServiceImpl implements SocialPresenceService {

	@Autowired
	SocialPresenceRepository socialPresenceRepository;

	@Override
	@Transactional(readOnly = false)
	public SocialPresence save(SocialPresence socialPresence) {
		return socialPresenceRepository.save(socialPresence);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresencesForUser(long userId) {
		return socialPresenceRepository.getSocialPresencesForUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public SocialPresence getSocialPresenceForUserAndSocialNetwork(long userId,
			SocialPresenceType socialNetwork) {
		return socialPresenceRepository
				.getSocialPresenceForUserAndSocialNetwork(userId, socialNetwork);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresences() {
		return socialPresenceRepository.getSocialPresences();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getSocialPresencesCount() {
		return socialPresenceRepository.getSocialPresencesCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresencesForSocialNetwork(
			SocialPresenceType socialNetwork) {
		return socialPresenceRepository
				.getSocialPresencesForSocialNetwork(socialNetwork);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getFriendsOnSocialNetwork(
			SocialPresenceType socialNetwork, Set<String> socialIds) {

		List<SocialPresence> socialPresences = getSocialPresencesForSocialNetwork(socialNetwork);

		List<User> result = new ArrayList<User>();

		for (SocialPresence currentSocialPresence : socialPresences) {
			if (socialIds.contains(currentSocialPresence.getSocialId()))
				result.add(currentSocialPresence.getUser());
		}

		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public SocialPresence create(User user, SocialPresenceType socialNetwork,
			String socialId) {
		return socialPresenceRepository.create(user, socialNetwork, socialId);
	}

}
