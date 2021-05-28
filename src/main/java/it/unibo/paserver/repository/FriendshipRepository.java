package it.unibo.paserver.repository;

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;

import java.util.List;
/**
 * Repository for Friendship entities.
 * 
 * @author danielecampogiani
 *
 */
public interface FriendshipRepository {

	/**
	 * Saves the given Friendship.
	 * 
	 * @param friendship Friendship to persist
	 * @return saved Friendship
	 * @see Friendship
	 */
	Friendship save(Friendship friendship);

	/**
	 * Creates a new Friendship with given arguments.
	 * 
	 * @param sender Friendship sender
	 * @param receiver Friendship receiver
	 * @param status Friendship status
	 * @return the new Friendship
	 * @see Friendship
	 * @see User
	 * @see FriendshipStatus
	 */
	Friendship create(User sender, User receiver, FriendshipStatus status);

	/**
	 * Returns all Friendships for given User id.
	 * 
	 * @param userId User id
	 * @return List of Friendship for given User id
	 * @see Friendship
	 * @see User
	 */
	List<Friendship> getFriendshipsForUser(long userId);

	/**
	 * Returns Friendships for given User id and status.
	 * 
	 * @param userId User id
	 * @param status Friendship status
	 * @return List of Friendship for given User id and status
	 * @see Friendship
	 * @see User
	 * @see FriendshipStatus
	 */
	List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status);

	/**
	 * Returns Friendships for given User id, status and role.
	 * 
	 * @param userId User id
	 * @param status Friendship status
	 * @param sender true if you want Friendships where given User is the sender, false if you want Friendships where given User is the receiver
	 * @return Friendships for given User id and status and role
	 * @see Friendship
	 * @see FriendshipStatus 
	 */
	List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status, boolean sender);

	/**
	 * Returns Friendship for given sender and receiver.
	 * 
	 * @param senderId sender id
	 * @param receiverId receiver id
	 * @return Friendship for given sender and receiver (if any)
	 * @see Friendship
	 */
	Friendship getFriendshipForSenderAndReceiver(long senderId, long receiverId);

	/**
	 * Returns all Friendships in database.
	 * 
	 * @return all Friendships in database
	 * @see Friendship 
	 */
	List<Friendship> getFriendships();

	/**
	 * Returns the amount of Friendships saved in database.
	 * 
	 * @return the amount of Friendships saved in database
	 */
	Long getFriendshipsCount();

}
