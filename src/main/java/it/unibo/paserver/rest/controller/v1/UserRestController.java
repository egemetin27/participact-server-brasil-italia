/** UserLogin.java
 *  author: Andrea Cirri
 *  mail : andreacirri@gmail.com
 */
package it.unibo.paserver.rest.controller.v1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.EnumUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.ClientSWVersion;
import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Level;
import it.unibo.paserver.domain.Level.LevelRank;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserLogFile;
import it.unibo.paserver.domain.rest.FriendshipRestStatus;
import it.unibo.paserver.domain.rest.SocialPresenceFriendsRequest;
import it.unibo.paserver.domain.rest.SocialPresenceRequest;
import it.unibo.paserver.domain.rest.UserRestResult;
import it.unibo.paserver.domain.support.FileBuilder;
import it.unibo.paserver.service.ClientSWVersionService;
import it.unibo.paserver.service.FileUploadService;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.ReputationService;
import it.unibo.paserver.service.SocialPresenceService;
import it.unibo.paserver.service.UserLogFileService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.GCMController;

@Controller
public class UserRestController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserService userService;
	@Autowired
	private ReputationService reputationService;
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private SocialPresenceService socialPresenceService;
	@Autowired
	private UserLogFileService userLogFileService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	GCMController gcmController;

	private static final String SENSING_MOST = "SENSING_MOST";
	private static final String PHOTO = "PHOTO";
	private static final String QUESTIONNAIRE = "QUESTIONNAIRE";
	private static final String ACTIVITY_DETECTION = "ACTIVITY_DETECTION";

	private static final String ACCEPTED = "ACCEPTED";
	private static final String PENDING = "PENDING";
	private static final String REJECTED = "REJECTED";
	private static final String PENDING_SENT = "PENDING_SENT";
	private static final String PENDING_RECEIVED = "PENDING_RECEIVED";
	private static final String REJECTED_SENT = "REJECTED_SENT";
	private static final String REJECTED_RECEIVED = "REJECTED_RECEIVED";

	private static final String FACEBOOK = "FACEBOOK";
	private static final String GOOGLE = "GOOGLE";
	private static final String TWITTER = "TWITTER";

	@Autowired
	private ClientSWVersionService clientSWVersionService;

	public static final String HEADER_KEY = "Request_key";

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@RequestMapping(value = "/rest/login", method = RequestMethod.GET)
	public @ResponseBody Boolean mobileLogin(Principal principal, @RequestParam("device") String device) {
		try {
			User u = userService.getUser(principal.getName());
			if (!Validator.isEmptyString(device)) {
				u.setDevice(device);
				userService.save(u);
			}
			logger.info("User {} successfully logged in", u.getOfficialEmail());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/rest/registergcm", method = RequestMethod.GET)
	public @ResponseBody Boolean gcmRegistration(ModelAndView modelAndView, Principal principal,
			@RequestParam("gcmid") String gcmid) {
		try {
			User user = userService.getUser(principal.getName());
			if (user != null) {
				user.setGcmId(gcmid);
				userService.save(user);
				logger.info("Register gcmid user {} gcmid={}", user.getOfficialEmail(), user.getGcmId());
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return false;
	}

	@RequestMapping(value = "/rest/upload/log/{fname}", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage uploadLog(HttpServletRequest request, Principal principal,
			@PathVariable String fname) {
		ResponseMessage response = new ResponseMessage();
		String key = request.getHeader(HEADER_KEY);
		response.setKey(key);
		ByteArrayOutputStream baos = null;
		GZIPInputStream gis = null;
		ByteArrayOutputStream decompressed = null;
		try {
			User u = userService.getUser(principal.getName());
			response.setResultCode(200);
			baos = new ByteArrayOutputStream();
			IOUtils.copy(request.getInputStream(), baos);
			baos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			gis = new GZIPInputStream(bais);
			decompressed = new ByteArrayOutputStream();
			IOUtils.copy(gis, decompressed);
			UserLogFile ulf = new UserLogFile();
			ulf.setUploadTime(new DateTime());
			ulf.setUser(u);
			BinaryDocument bd = new BinaryDocument();
			bd.setContent(decompressed.toByteArray());
			bd.setContentType("text");
			bd.setContentSubtype("plain");
			bd.setCreated(new DateTime());
			bd.setFilename(fname + ".log");
			bd.setOwner(u);
			ulf.setBinaryDocument(bd);
			userLogFileService.merge(ulf);
			logger.info("Received log file from {}", principal.getName());
		} catch (Exception e) {
			logger.error("Error while receiving log", e);
			response.setResultCode(500);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {

				}
			}
			if (gis != null) {
				try {
					gis.close();
				} catch (Exception e) {

				}
			}
			if (decompressed != null) {
				try {
					decompressed.close();
				} catch (Exception e) {

				}
			}
		}
		return response;
	}

	@RequestMapping(value = "/rest/clientversion", method = RequestMethod.GET)
	public @ResponseBody Integer clientVersion() {
		ClientSWVersion csv = clientSWVersionService.getLatestVersion();
		if (csv != null) {
			return csv.getVersion();
		}
		return 1;
	}

	@RequestMapping(value = "/rest/user/{id}", method = RequestMethod.GET)
	public @ResponseBody UserRestResult getUserInfo(@PathVariable long id) {
		User user = userService.getUser(id);
		if (user != null) {
			logger.info("Received request info for user {}", user.getName());
			UserRestResult result = new UserRestResult(user);
			LevelRank sensingMostLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.SENSING_MOST).getLevelRank();
			LevelRank photoLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.PHOTO).getLevelRank();
			LevelRank questionnaireLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.QUESTIONNAIRE).getLevelRank();
			LevelRank activityDetectionLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.ACTIVITY_DETECTION).getLevelRank();
			result.setSensingMostLevel(sensingMostLevelRank);
			result.setPhotoLevel(photoLevelRank);
			result.setQuestionnaireLevel(questionnaireLevelRank);
			result.setActivityDetectionLevel(activityDetectionLevelRank);
			result.setPhone(user.getContactPhoneNumber());
			result.setPhoto(user.getPhoto());
			result.setAddress(user.getCurrentAddress());
			result.setCurrentCountry(user.getCurrentCountry());
			result.setCurrentCity(user.getCurrentCity());
			result.setCurrentNumber(user.getCurrentNumber());
			result.setCurrentZipCode(user.getCurrentZipCode());
			result.setCurrentProvince(user.getCurrentProvince());
			result.setGender(user.getGender());
			
			LocalDate dt = user.getBirthdate();
			String birthdate = null;
			try {
				birthdate = dt.toString("yyyy-MM-dd");
			} catch (Exception e) {
				// TODO: handle exception
			}
			result.setBirthdate(birthdate);
			
			return result;
		}
		logger.info("Received request info for user {}, but user doesn't exist", id);
		return null;
	}

	@RequestMapping(value = "/rest/user/me", method = RequestMethod.GET)
	public @ResponseBody UserRestResult getUserInfo(Principal principal) {
		try {
			User user = userService.getUser(principal.getName());
			if (user != null) {
				return getUserInfo(user.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return null;
	}

	@RequestMapping(value = "/rest/user/{id}/level", method = RequestMethod.GET)
	public @ResponseBody LevelRank getUserLevelRank(@PathVariable long id,
			@RequestParam("actionType") String actionType) {
		User user = userService.getUser(id);
		if (user != null) {
			ActionType aType;
			if (SENSING_MOST.equalsIgnoreCase(actionType))
				aType = ActionType.SENSING_MOST;
			else if (PHOTO.equalsIgnoreCase(actionType))
				aType = ActionType.PHOTO;
			else if (QUESTIONNAIRE.equalsIgnoreCase(actionType))
				aType = ActionType.QUESTIONNAIRE;
			else if (ACTIVITY_DETECTION.equalsIgnoreCase(actionType))
				aType = ActionType.ACTIVITY_DETECTION;
			else {
				logger.info("Received request level for actionType {}, but actionType doesn't exist", actionType);
				return null;
			}

			Level level = reputationService.getLevelByUserAndActionType(id, aType);
			logger.info("Received request level for user {}", id);
			return level.getLevelRank();
		}
		logger.info("Received request level for user {}, but user doesn't exist", id);
		return null;
	}

	@RequestMapping(value = "/rest/user/friends", method = RequestMethod.GET)
	public @ResponseBody UserRestResult[] getFriends(Principal principal, @RequestParam("status") String status) {
		User user = userService.getUser(principal.getName());

		boolean sender = false;

		if (user != null) {
			FriendshipStatus fStatus;
			if (ACCEPTED.equalsIgnoreCase(status))
				fStatus = FriendshipStatus.ACCEPTED;
			else if (PENDING_SENT.equalsIgnoreCase(status)) {
				fStatus = FriendshipStatus.PENDING;
				sender = true;
			} else if (PENDING_RECEIVED.equalsIgnoreCase(status)) {
				fStatus = FriendshipStatus.PENDING;
				sender = false;
			} else if (REJECTED_SENT.equalsIgnoreCase(status)) {
				fStatus = FriendshipStatus.REJECTED;
				sender = true;
			} else if (REJECTED_RECEIVED.equalsIgnoreCase(status)) {
				fStatus = FriendshipStatus.REJECTED;
				sender = false;
			} else {
				logger.info("Received request friends for user {} and status {} , but status doesn't exist",
						principal.getName(), status);
				return new UserRestResult[0];
			}
			List<User> friends = friendshipService.getFriendsForUserAndStatus(user.getId(), fStatus, sender);
			ArrayList<UserRestResult> resultList = new ArrayList<UserRestResult>(friends.size());
			for (User friend : friends) {
				UserRestResult currentResult = new UserRestResult(friend);
				long id = currentResult.getId();
				LevelRank sensingMostLevelRank = reputationService
						.getLevelByUserAndActionType(id, ActionType.SENSING_MOST).getLevelRank();
				LevelRank photoLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.PHOTO)
						.getLevelRank();
				LevelRank questionnaireLevelRank = reputationService
						.getLevelByUserAndActionType(id, ActionType.QUESTIONNAIRE).getLevelRank();
				LevelRank activityDetectionLevelRank = reputationService
						.getLevelByUserAndActionType(id, ActionType.ACTIVITY_DETECTION).getLevelRank();
				currentResult.setSensingMostLevel(sensingMostLevelRank);
				currentResult.setPhotoLevel(photoLevelRank);
				currentResult.setQuestionnaireLevel(questionnaireLevelRank);
				currentResult.setActivityDetectionLevel(activityDetectionLevelRank);
				resultList.add(currentResult);
			}
			UserRestResult[] result = new UserRestResult[resultList.size()];
			result = resultList.toArray(result);
			Arrays.sort(result);
			logger.info("Received request friends for user {} and status {}", principal.getName(), status);
			return result;
		}

		logger.info("Received request friends but user is not authenticated");
		return new UserRestResult[0];

	}

	@RequestMapping(value = "/rest/user/friends/{id}", method = RequestMethod.GET)
	public @ResponseBody FriendshipRestStatus getFriendStatus(Principal principal, @PathVariable long id) {
		User me = userService.getUser(principal.getName());
		User friend = userService.getUser(id);
		String error = "ERROR";
		String not_setted = "NOT_SETTED";
		boolean sender = true;
		FriendshipRestStatus result = new FriendshipRestStatus();

		if (me == null) {
			logger.info("Received request get friend status with invalid logged user");
			result.setStatus(error);
			return result;
		}

		if (friend == null) {
			logger.info("Received request get friend status with invalid id {}", id);
			result.setStatus(error);
			return result;
		}

		if (me.getId() == friend.getId()) {
			logger.info("Received request get friend status with same id {}", id);
			result.setStatus(error);
			return result;
		}

		Friendship friendship = null;
		try {
			friendship = friendshipService.getFriendshipForSenderAndReceiver(me.getId(), id);
		} catch (NoResultException e) {
			try {
				friendship = friendshipService.getFriendshipForSenderAndReceiver(id, me.getId());
				sender = false;
			} catch (NoResultException e2) {

			}
		}

		if (friendship == null) {
			result.setStatus(not_setted);
			return result;
		} else {
			FriendshipStatus status = friendship.getStatus();
			if (status == FriendshipStatus.ACCEPTED) {
				result.setStatus(ACCEPTED);
				return result;
			} else if (status == FriendshipStatus.PENDING) {
				if (sender) {
					result.setStatus(PENDING_SENT);
					return result;
				} else {
					result.setStatus(PENDING_RECEIVED);
					return result;
				}
			} else if (status == FriendshipStatus.REJECTED) {
				if (sender) {
					result.setStatus(REJECTED_SENT);
					return result;
				} else {
					result.setStatus(REJECTED_RECEIVED);
					return result;
				}
			}

			else {
				result.setStatus(error);
				return result;
			}
		}

	}

	@RequestMapping(value = "/rest/user/friends/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean addFriend(Principal principal, @PathVariable long id,
			@RequestBody FriendshipRestStatus request) {

		if (request == null) {
			logger.info("Received request add new friends with invalid @RequestBody parameter");
			return false;
		}

		User user = userService.getUser(principal.getName());

		if (user == null) {
			logger.info("Received request add friends but user is not authenticated");
			return false;
		}

		if (user.getId() == id) {
			logger.info("Received request add friends but user is both the sender and the receiver");
			return false;
		}

		String status = request.getStatus();
		FriendshipStatus fStatus;
		if (ACCEPTED.equalsIgnoreCase(status))
			fStatus = FriendshipStatus.ACCEPTED;
		else if (PENDING.equalsIgnoreCase(status))
			fStatus = FriendshipStatus.PENDING;
		else if (REJECTED.equalsIgnoreCase(status))
			fStatus = FriendshipStatus.REJECTED;
		else {
			logger.info("Received request add new friends for status {}, but status doesn't exist", status);
			return false;
		}

		long idSender = user.getId();
		long idReceiver = id;
		try {
			Friendship current = friendshipService.getFriendshipForSenderAndReceiver(idSender, idReceiver);
			current.setStatus(fStatus);
			friendshipService.save(current);
			logger.info(
					"Received request add new friends for sender: {} , receiver: {} and status {}, which already exists, updating with new status",
					idSender, idReceiver, status);
			return true;

		} catch (NoResultException e1) {

			try {
				Friendship current = friendshipService.getFriendshipForSenderAndReceiver(idReceiver, idSender);
				current.setStatus(fStatus);
				friendshipService.save(current);
				logger.info(
						"Received request add new friends for sender: {} , receiver: {} and status {}, which already exists, updating with new status",
						idSender, idReceiver, status);
				return true;

			} catch (NoResultException e2) {

				User sender = userService.getUser(idSender);
				User receiver = userService.getUser(idReceiver);
				if (sender != null && receiver != null) {
					friendshipService.create(sender, receiver, fStatus);
					if (fStatus == FriendshipStatus.PENDING) {
						if (receiver != null) {
							List<String> receiverMailList = new ArrayList<String>();
							receiverMailList.add(receiver.getOfficialEmail());
							gcmController.notifyUsers(PANotification.Type.NEW_FRIEND, receiverMailList);
						}
					}
					logger.info(
							"Received request add new friends for sender: {} , receiver: {} and status {}, creating the new friendship",
							idSender, idReceiver, status);
					return true;
				} else {
					logger.info("Can't create new Friendship with parameters sender: {} receiver: {} status: {}",
							idSender, idReceiver, request.getStatus());
					return false;
				}

			}

		}
	}

	@RequestMapping(value = "/rest/user/social/{socialnetwork}", method = RequestMethod.POST)
	public @ResponseBody Boolean addSocialPresence(Principal principal, @PathVariable String socialnetwork,
			@RequestBody SocialPresenceRequest request) {

		SocialPresenceType sType;
		String socialId = request.getSocialId();

		if (socialId == null || "".equals(socialId)) {
			logger.info("Received request add social presence with invalid @RequestBody parameter");
			return false;
		}

		if (FACEBOOK.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.FACEBOOK;
		else if (GOOGLE.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.GOOGLE;
		else if (TWITTER.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.TWITTER;
		else {
			logger.info("Received request add social presence for social network {}, but social network doesn't exist",
					socialnetwork);
			return false;
		}

		User user = userService.getUser(principal.getName());

		if (user != null) {

			SocialPresence newSocialPresence;

			try {
				newSocialPresence = socialPresenceService.getSocialPresenceForUserAndSocialNetwork(user.getId(), sType);
				newSocialPresence.setSocialId(socialId);
				socialPresenceService.save(newSocialPresence);
				logger.info("Updated SocialPresence with parameters User: {} socialNewtork: {} socialId: {}",
						user.getName(), socialnetwork, socialId);
				return true;

			} catch (NoResultException e) {

				newSocialPresence = socialPresenceService.create(user, sType, socialId);
				if (newSocialPresence != null) {
					logger.info("Creted new SocialPresence with parameters User: {} socialNewtork: {} socialId: {}",
							user.getName(), socialnetwork, socialId);
					return true;
				} else {
					logger.info(
							"Can't create new SocialPresence with parameters User: {} socialNewtork: {} socialId: {}",
							user.getName(), socialnetwork, socialId);
					return false;

				}

			}

		}
		logger.info("Received request add social presence but user is not authenticated");
		return false;
	}

	@RequestMapping(value = "/rest/user/social/{socialnetwork}/friends", method = RequestMethod.POST)
	public @ResponseBody UserRestResult[] getFriendsForSocial(Principal principal, @PathVariable String socialnetwork,
			@RequestBody SocialPresenceFriendsRequest request) {

		if (request == null) {
			logger.info("Received request get friends from social network with invalid @RequestBody parameter");
			return new UserRestResult[0];
		}

		SocialPresenceType sType;

		if (FACEBOOK.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.FACEBOOK;
		else if (GOOGLE.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.GOOGLE;
		else if (TWITTER.equalsIgnoreCase(socialnetwork))
			sType = SocialPresenceType.TWITTER;
		else {
			logger.info("Received request get friends from social network {}, but social network doesn't exist",
					socialnetwork);
			return new UserRestResult[0];
		}

		List<UserRestResult> resultList = new ArrayList<UserRestResult>();
		UserRestResult[] currentAccepted = getFriends(principal, ACCEPTED);
		UserRestResult[] currentPendingSent = getFriends(principal, PENDING_SENT);
		UserRestResult[] currentPendingReceived = getFriends(principal, PENDING_RECEIVED);
		UserRestResult[] currentRejectedSent = getFriends(principal, REJECTED_SENT);
		UserRestResult[] currentRejectedReceived = getFriends(principal, REJECTED_RECEIVED);
		List<Long> toHideList = new ArrayList<Long>();
		for (UserRestResult current : currentAccepted)
			toHideList.add(current.getId());
		for (UserRestResult current : currentPendingSent)
			toHideList.add(current.getId());
		for (UserRestResult current : currentPendingReceived)
			toHideList.add(current.getId());
		for (UserRestResult current : currentRejectedSent)
			toHideList.add(current.getId());
		for (UserRestResult current : currentRejectedReceived)
			toHideList.add(current.getId());

		Set<Long> toHide = new HashSet<Long>(toHideList);

		Set<String> friendsIds = request.getIds();
		List<SocialPresence> allUserForSocial = socialPresenceService.getSocialPresencesForSocialNetwork(sType);
		for (SocialPresence current : allUserForSocial) {
			if (friendsIds.contains(current.getSocialId())) {

				if (!toHide.contains(current.getUser().getId())) {
					UserRestResult currentResult = new UserRestResult(current.getUser());
					long id = current.getUser().getId();
					LevelRank sensingMostLevelRank = reputationService
							.getLevelByUserAndActionType(id, ActionType.SENSING_MOST).getLevelRank();
					LevelRank photoLevelRank = reputationService.getLevelByUserAndActionType(id, ActionType.PHOTO)
							.getLevelRank();
					LevelRank questionnaireLevelRank = reputationService
							.getLevelByUserAndActionType(id, ActionType.QUESTIONNAIRE).getLevelRank();
					LevelRank activityDetectionLevelRank = reputationService
							.getLevelByUserAndActionType(id, ActionType.ACTIVITY_DETECTION).getLevelRank();
					currentResult.setSensingMostLevel(sensingMostLevelRank);
					currentResult.setPhotoLevel(photoLevelRank);
					currentResult.setQuestionnaireLevel(questionnaireLevelRank);
					currentResult.setActivityDetectionLevel(activityDetectionLevelRank);
					resultList.add(currentResult);
				}

			}
		}

		logger.info("Received request get friends from social network {}", socialnetwork);
		UserRestResult[] result = new UserRestResult[resultList.size()];
		result = resultList.toArray(result);
		Arrays.sort(result);
		return result;

	}

	/**
	 * Atualizando profile
	 */
	@RequestMapping(value = "/rest/profile/update", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage profileUpdate(@RequestBody String data, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		response.setResultCode(500);
		response.setProperty("status", "false");
		// User
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				JSONObject json = new JSONObject(data);
				String name = (json.has("name")) ? json.getString("name") : "";
				if (!Validator.isEmptyString(name)) {
					user.setName(name);
				}

				String surname = (json.has("surname")) ? json.getString("surname") : "";
				if (!Validator.isEmptyString(surname)) {
					user.setSurname(surname);
				}

				String phone = (json.has("phone")) ? json.getString("phone") : "";
				if (!Validator.isEmptyString(phone)) {
					user.setContactPhoneNumber(phone);
				}

				String password = (json.has("password")) ? json.getString("password") : null;
				if (!Validator.isEmptyString(password) && Validator.isValidPassword(password)) {
					user.setCredentials(principal.getName(), password);
				}

				String currentAddress = (json.has("currentAddress")) ? json.getString("currentAddress") : "";
				if (!Validator.isEmptyString(currentAddress)) {
					user.setCurrentAddress(currentAddress);
					user.setDomicileAddress(currentAddress);
				}

				String currentCity = (json.has("currentCity")) ? json.getString("currentCity") : "";
				if (!Validator.isEmptyString(currentCity)) {
					user.setCurrentCity(currentCity);
				}

				String currentProvince = (json.has("currentProvince")) ? json.getString("currentProvince") : "";
				if (!Validator.isEmptyString(currentProvince)) {
					user.setCurrentProvince(currentProvince);
				}

				String currentZipCode = (json.has("currentZipCode")) ? json.getString("currentZipCode") : "";
				if (!Validator.isEmptyString(currentZipCode)) {
					user.setCurrentZipCode(currentZipCode);
				}

				String currentNumber = (json.has("currentNumber")) ? json.getString("currentNumber") : "";
				if (!Validator.isEmptyString(currentNumber)) {
					user.setCurrentNumber(currentNumber);
				}

				String currentCountry = (json.has("currentCountry")) ? json.getString("currentCountry") : "";
				if (!Validator.isEmptyString(currentCountry)) {
					user.setCurrentCountry(currentCountry);
				}

				String mapLat = (json.has("mapLat")) ? json.getString("mapLat") : "";
				if (!Validator.isEmptyString(mapLat)) {
					user.setMapLat(mapLat);
				}

				String mapLng = (json.has("mapLng")) ? json.getString("mapLng") : "";
				if (!Validator.isEmptyString(mapLng)) {
					user.setMapLng(mapLng);
				}

				String gender = (json.has("gender")) ? json.getString("gender") : "";
				if (!Validator.isEmptyString(gender)) {
					if (EnumUtils.isValidEnum(Gender.class, gender)) {
						user.setGender(Gender.valueOf(gender));
					}
				}
				
				String birthdate = (json.has("birthdate")) ? json.getString("birthdate") : "";
				if (!Validator.isEmptyString(birthdate)) {
					LocalDate dt = new LocalDate();
					if (Validator.isValidDate(birthdate)) {
						DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
						dt = dtf.parseLocalDate(birthdate);
						user.setBirthdate(dt);
					}										
				}

				String uniDepartment = (json.has("uniDepartment")) ? json.getString("uniDepartment") : "";
				if (!Validator.isEmptyString(uniDepartment)) {
					user.setUniDepartment(uniDepartment);
				}
				
				String uniCodCourse = (json.has("uniCodCourse")) ? json.getString("uniCodCourse") : "";
				if (!Validator.isEmptyString(uniCodCourse)) {
					user.setUniCodCourse(uniCodCourse);
				}
				
				String uniPhase = (json.has("uniPhase")) ? json.getString("uniPhase") : "";
				if (!Validator.isEmptyString(uniPhase) && Validator.isValidNumeric(uniPhase)) {
					user.setUniPhase(Integer.parseInt(uniPhase));
				}
				
				// Executando
				logger.info(data.toString());
				userService.save(user);
				response.setResultCode(200);
				response.setProperty("status", "true");
			} catch (Exception e) {
				// TODO: handle exception
				response.setProperty("message", Useful.replaceNull(e.getMessage()));
			}
		}
		return response;
	}

	/**
	 * Atualizando foto de profile
	 */
	@RequestMapping(value = "/rest/profile/photo", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage photoUpdate(MultipartHttpServletRequest request, Principal principal) {
		// Response
		ResponseMessage response = new ResponseMessage();
		response.setResultCode(500);
		response.setProperty("status", "false");
		// User
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				long parentId = user.getId();
				Iterator<String> itr = request.getFileNames();
				while (itr.hasNext()) {
					String uploadedFile = itr.next();
					MultipartFile file = request.getFile(uploadedFile);
					String mimeType = file.getContentType();
					String newFilenameBase = "U" + parentId + "-" + UUID.randomUUID().toString();
					String originalFileExtension = file.getOriginalFilename()
							.substring(file.getOriginalFilename().lastIndexOf("."));
					String filename = newFilenameBase + originalFileExtension;
					String[] haystack = { ".gif", ".png", ".jpg", ".jpeg" };
					// System.out.println(originalFileExtension);
					if (Validator.isValidMimeTypeImage(mimeType)
							&& Validator.isValueinArray(haystack, originalFileExtension)) {
						if (mimeType.equals("application/octet-stream")) {
							mimeType = "image/" + originalFileExtension.replace(".", "");
						}
						byte[] bytes;
						bytes = file.getBytes();
						FileBuilder fb = new FileBuilder();
						fb.setAll(0, parentId, filename, bytes, mimeType, (byte) 0, "Profile");
						fb.setAdmin(false);
						FileUpload f = fb.build(true);
						// Save
						FileUpload rs = fileUploadService.saveOrUpdate(f);
						if (rs != null) {
							response.setProperty("data", Config.PRODUCTION_PAGE_IMG + "/" + rs.getId());
							response.setProperty("status", "true");
							response.setProperty("message",
									messageSource.getMessage("confirmation.importing", null, null));
						}
					} else {
						response.setProperty("message", messageSource.getMessage("error.file.format", null, null));
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.setProperty("message", messageSource.getMessage("error.file.format", null, null));
				e.printStackTrace();
			}
		}
		return response;
	}
}
