package it.unibo.paserver.repository;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.Task;

import java.util.List;
import java.util.Set;

/**
 * Repository for Badge entities.
 * 
 * @author danielecampogiani
 *
 */
public interface BadgeRepository {

	/**
	 * Returns the Badge having the given id.
	 * 
	 * @param id id of the Badge to found
	 * @return found Badge
	 * @see Badge 
	 */
	Badge findById(long id);

	/**
	 * Deletes the Badge having the given id.
	 * 
	 * @param id id of the Badge to be deleted
	 * @return true if success, false otherwise
	 */
	boolean deleteBadge(long id);

	/**
	 * Saves the given Badge.
	 * 
	 * @param badge Badge to persist
	 * @return saved badge
	 * @see Badge
	 */
	Badge save(Badge badge);

	/**
	 * Creates a new BadgeTask with given arguments.
	 * 
	 * @param task the Task of BadgeTask
	 * @param title Badge title
	 * @param description Badge description
	 * @return the new Badge
	 * @see BadgeTask
	 * @see Badge
	 * @see Task
	 */
	Badge createBadgeTask(Task task, String title, String description);

	/**
	 * Creates a new BadgeActions with given arguments.
	 * 
	 * @param actionType the ActionType of BadgeActions
	 * @param quantity the quantity of BadgeActions
	 * @param title Badge title
	 * @param description Badge description
	 * @return the new Badge
	 * @see BadgeActions
	 * @see Badge
	 * @see ActionType
	 */
	Badge createBadgeAction(ActionType actionType, int quantity, String title,
			String description);

	/**
	 * Returns Badges unlocked by given User id.
	 * 
	 * @param userId User's id
	 * @return Set of Badges unlocked by given User id
	 * @see Badge
	 * @see User
	 */
	Set<? extends Badge> getBadgesForUser(long userId);

	/**
	 * Returns BadgeActions for given ActionType.
	 * 
	 * @param actionType ActionType for which you are looking for
	 * @return found Badges
	 * @see BadgeActions
	 * @see ActionType
	 */
	List<BadgeActions> getBadgesForActionType(ActionType actionType);

	/**
	 * Returns BadgeActions for given ActionType and quantity.
	 * 
	 * @param actionType ActionType for which you are looking for
	 * @param quantity quantity for which you are looking for
	 * @return found Badges
	 * @see BadgeActions
	 * @see ActionType
	 */
	BadgeActions getBadgeForActionTypeAndQuantity(ActionType actionType,
			int quantity);

	/**
	 * Returns BadgeActions for given ActionType and quantity less than the given one.
	 * 
	 * @param actionType ActionType for which you are looking for
	 * @param quantity quantity for which you are looking for
	 * @return found Badges
	 * @see BadgeActions
	 * @see ActionType
	 */
	List<BadgeActions> getBadgeForActionTypeAndMaxQuantity(ActionType actionType,
			int quantity);
	
	/**
	 * Returns BadgeTask for given Task.
	 * 
	 * @param taskId Task id
	 * @return found Badge (if any)
	 * @see BadgeTask
	 */
	BadgeTask getBadgeForTask(long taskId);

	/**
	 * Returns all Badges in database.
	 * 
	 * @return all Badges in database
	 * @see Badge
	 */
	List<? extends Badge> getBadges();

	/**
	 * Returns the amount of Badges saved in database.
	 * 
	 * @return the amount of Badges saved in database
	 */
	Long getBadgesCount();

}
