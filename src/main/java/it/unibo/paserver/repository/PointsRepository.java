package it.unibo.paserver.repository;

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;

import java.util.List;

import org.joda.time.DateTime;

/**
 * Repository for Points entities.
 * 
 * @author danielecampogiani
 *
 */
public interface PointsRepository {

	/**
	 * Saves given Points.
	 * 
	 * @param points Points to save
	 * @return saved Points
	 * @see Points
	 */
	Points save(Points points);

	/**
	 * Create new Points with given arguments.
	 * 
	 * @param user Points User
	 * @param task Points Task
	 * @param dateTime Points time stamp
	 * @param value Points value
	 * @param type Points type
	 * @return the new Points
	 * @see Points
	 * @see User
	 * @see Task
	 * @see DateTime
	 */
	Points create(User user, Task task, DateTime dateTime, Integer value, PointsType type);

	/**
	 * Returns all Points for given User id in the interval.
	 * 
	 * @param userId User id
	 * @param from interval start
	 * @param to interval end
	 * @return List of Points for given User id in the interval
	 * @see Points
	 * @see DateTime
	 */
	List<Points> getPointsByUserAndDates(long userId, DateTime from, DateTime to);

	/**
	 * Returns Points for given User id , Task id and Points Type.
	 * 
	 * @param userId User id
	 * @param taskId Task id
	 * @param type Points type
	 * @return Points for given User id , Task id and Points Type
	 * @see Points
	 * @see PointsType
	 */
	Points getPointsByUserAndTaskAndType(long userId, long taskId, PointsType type);
	
	/**
	 * Returns Points for given User id and Task id
	 * 
	 * @param userId User id
	 * @param taskId Task id
	 * @return Points for given User id and Task id
	 * @see Points
	 */
	List<Points> getPointsByUserAndTask(long userId, long taskId);
	
	/**
	 * Returns Points for given User id  and Points Type.
	 * 
	 * @param userId User id
	 * @param type Points type
	 * @return Points for given User id  and Points Type
	 * @see Points
	 * @see PointsType
	 */
	List<Points> getPointsByUserAndType(long userId, PointsType type);
	
	/**
	 * Returns Points for given Task id and Points Type.
	 * 
	 * @param taskId Task id
	 * @param type Points type
	 * @return Points for given Task id and Points Type
	 * @see Points
	 * @see PointsType 
	 */
	List<Points> getPointsByTaskAndType(long taskId, PointsType type);

	/**
	 * Returns all Points saved in database.
	 * 
	 * @return all Points saved in database
	 * @see Points
	 */
	List<Points> getPoints();

	/**
	 * Return the amount of Points saved in database.
	 * 
	 * @return the amount of Points saved in database
	 */
	Long getPointsCount();

}
