package it.unibo.paserver.rest.controller.v1;

import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.rest.ScoreRestResult;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST controller providing leaderboards.
 * 
 * @author danielecampogiani
 */
@Controller
public class LeaderboardsController {

	@Autowired
	FriendshipService friendshipService;
	@Autowired
	PointsService pointsService;
	@Autowired
	UserService userService;

	public static final String GlobalLeaderboard = "global";
	public static final String SociallLeaderboard = "social";

	private static final Logger logger = LoggerFactory
			.getLogger(LeaderboardsController.class);

	private static final int HowManyDays = 7;

	/**
	 * Returns Scores of Participact Users or friends.
	 * 
	 * @param principal Spring security user
	 * @param type "global" for all users, "social" for friends
	 * @return array of ScoreRestResult
	 * @see Principal
	 * @see ScoreRestResult
	 */
	@RequestMapping(value = "/rest/leaderboard", method = RequestMethod.GET)
	public @ResponseBody ScoreRestResult[] getLeaderBoard(Principal principal,
			@RequestParam("type") String type) {

		DateTime from = new DateTime().minusDays(HowManyDays);
		DateTime to = new DateTime();
		List<User> users = null;
		User user = userService.getUser(principal.getName());
		ArrayList<ScoreRestResult> resultList = new ArrayList<ScoreRestResult>();

		if (user != null) {

			if (GlobalLeaderboard.equalsIgnoreCase(type))
				users = userService.getUsers();

			else if (SociallLeaderboard.equalsIgnoreCase(type)) {
				users = friendshipService.getFriendsForUserAndStatus(
						user.getId(), FriendshipStatus.ACCEPTED, false);
				users.add(user);
			} else {
				logger.info(
						"Received {} leaderboard request from user {}, but {} type doesn't exist",
						type, user.getName(), type);
				return new ScoreRestResult[0];
			}

			for (User currentUser : users) {
				Score score = pointsService.getScoreByUserAndDates(
						currentUser.getId(), from, to);
				if (score.getValue() > 0) {
					ScoreRestResult currentUserResult = new ScoreRestResult(
							score);
					resultList.add(currentUserResult);
				}
			}

			// Collections.sort(resultList);
			ScoreRestResult[] result = new ScoreRestResult[resultList.size()];
			result = resultList.toArray(result);
			Arrays.sort(result);
			logger.info("Received {} leaderboard request from user {}", type,
					user.getName());
			return result;
		}

		else
			return new ScoreRestResult[0];

	}

}
