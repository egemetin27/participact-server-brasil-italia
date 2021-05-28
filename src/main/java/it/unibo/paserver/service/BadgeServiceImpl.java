package it.unibo.paserver.service;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.BadgeRepository;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of BadgeService using BadgeRepository
 * 
 * @author danielecampogiani
 * @see BadgeService
 * @see BadgeRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	BadgeRepository badgeRepository;

	@Override
	@Transactional(readOnly = false)
	public Badge save(Badge badge) {
		return badgeRepository.save(badge);
	}

	@Override
	@Transactional(readOnly = true)
	public Badge findById(long id) {
		return badgeRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<? extends Badge> getBadgesForUser(long userId) {
		return badgeRepository.getBadgesForUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<? extends Badge> getBadges() {
		return badgeRepository.getBadges();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getBadgesCount() {
		return badgeRepository.getBadgesCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<BadgeActions> getBadgesForActionType(ActionType actionType) {
		return badgeRepository.getBadgesForActionType(actionType);
	}

	@Override
	@Transactional(readOnly = true)
	public BadgeTask getBadgeForTask(long taskId) {
		return badgeRepository.getBadgeForTask(taskId);
	}

	@Override
	@Transactional(readOnly = true)
	public BadgeActions getBadgeForActionTypeAndQuantity(ActionType actionType,
			int quantity) {
		return badgeRepository.getBadgeForActionTypeAndQuantity(actionType,
				quantity);
	}

	@Override
	@Transactional(readOnly = false)
	public Badge createBadgeTask(Task task, String title, String description) {
		return badgeRepository.createBadgeTask(task, title, description);
	}

	@Override
	@Transactional(readOnly = false)
	public Badge createBadgeAction(ActionType actionType, int quantity,
			String title, String description) {
		return createBadgeAction(actionType, quantity, title, description);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteBadge(long id) {
		Badge toDelete = badgeRepository.findById(id);
		if (toDelete == null)
			return false;
		List<User> users = toDelete.getUsers();
		for (User user : users)
			user.getBadges().remove(toDelete);
		return badgeRepository.deleteBadge(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BadgeActions> getBadgeForActionTypeAndMaxQuantity(
			ActionType actionType, int quantity) {
		return badgeRepository.getBadgeForActionTypeAndMaxQuantity(actionType, quantity);
	}

}
