package it.unibo.paserver.rest.controller.v1;

import it.unibo.paserver.domain.AbstractBadge;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.BadgeService;
import it.unibo.paserver.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST controller for Badges.
 * 
 * @author danielecampogiani
 *
 */
@Controller
public class BadgeController {

	@Autowired
	private BadgeService badgeService;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(BadgeController.class);

	/**
	 * Returns all Badges.
	 * 
	 * @return array of all AbstractBadges saved in database
	 * @see AbstractBadge 
	 */
	@RequestMapping(value = "/rest/badges", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getAllbadges() {

		logger.info("Received request for all badges");

		@SuppressWarnings("unchecked")
		List<AbstractBadge> resultList = (List<AbstractBadge>) badgeService
				.getBadges();
		AbstractBadge[] result = new AbstractBadge[resultList.size()];
		result = resultList.toArray(result);
		Arrays.sort(result);
		return result;
	}

	/**
	 * Returns Badge for given id.
	 * 
	 * @param id Badge id
	 * @return AbstractBadge for given id
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/{id}", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge getBadgeById(@PathVariable long id) {
		AbstractBadge result = (AbstractBadge) badgeService.findById(id);
		logger.info("Received request for badge {}", id);
		return result;
	}

	/**
	 * Returns all unlocked Badges of user.
	 * 
	 * @param principal Spring Security user
	 * @return array of AbstractBadge unlocked by user
	 * @see Principal
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/user/me", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getBadgeByUser(Principal principal) {
		try{
			User user = userService.getUser(principal.getName());
			if (user != null) {
				return getBadgeByUser(user.getId());
			}
			logger.info("Received request for badge of user {}, but user doesn't exist",principal.getName());
			return new AbstractBadge[0];
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Returns all unlocked Badges by given User id
	 * 
	 * @param id User id
	 * @return array of AbstractBadge unlocked by given User id
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/user/{id}", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getBadgeByUser(@PathVariable long id) {
		@SuppressWarnings("unchecked")
		Set<AbstractBadge> resultSet = (Set<AbstractBadge>) badgeService
				.getBadgesForUser(id);
		AbstractBadge[] result = new AbstractBadge[resultSet.size()];
		result = resultSet.toArray(result);
		Arrays.sort(result);
		logger.info("Received request for badge of user {}", id);
		return result;
	}

}
