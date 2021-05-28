package it.unibo.paserver.service;

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.FriendshipRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of FriendshipService using FriendshipRepository.
 * 
 * @author danielecampogiani
 * @see FriendshipService
 * @see FriendshipRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
	FriendshipRepository friendshipRepository;

	@Override
	@Transactional(readOnly = false)
	public Friendship save(Friendship friendship) {
		return friendshipRepository.save(friendship);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Friendship> getFriendshipsForUser(long userId) {
		return friendshipRepository.getFriendshipsForUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status) {
		return friendshipRepository.getFriendshipsForUserAndStatus(userId,
				status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status, boolean sender) {
		return friendshipRepository.getFriendshipsForUserAndStatus(userId,
				status, sender);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getFriendsForUserAndStatus(long userId,
			FriendshipStatus status, boolean sender) {

		List<Friendship> friendships;
		if (status == FriendshipStatus.ACCEPTED)
			friendships = getFriendshipsForUserAndStatus(userId, status);
		else
			friendships = getFriendshipsForUserAndStatus(userId, status, sender);

		List<User> resul = new ArrayList<User>(friendships.size());
		for (Friendship currentFriendship : friendships) {
			if (currentFriendship.getSender().getId() == userId)
				resul.add(currentFriendship.getReceiver());
			else
				resul.add(currentFriendship.getSender());
		}
		return resul;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Friendship> getFriendships() {
		return friendshipRepository.getFriendships();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getFriendshipsCount() {
		return friendshipRepository.getFriendshipsCount();
	}

	@Override
	@Transactional(readOnly = true)
	public Friendship getFriendshipForSenderAndReceiver(long senderId,
			long receiverId) {
		return friendshipRepository.getFriendshipForSenderAndReceiver(senderId,
				receiverId);
	}

	@Override
	@Transactional(readOnly = false)
	public Friendship create(User sender, User receiver, FriendshipStatus status) {
		return friendshipRepository.create(sender, receiver, status);
	}

}
