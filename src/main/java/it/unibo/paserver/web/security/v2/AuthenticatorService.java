package it.unibo.paserver.web.security.v2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import br.com.bergmannsoft.utils.Cryptography;
import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.AuthenticationException;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.IssueSetting;
import it.unibo.paserver.domain.MailingLogs;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserSecretKey;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.domain.support.UserSecretKeyBuilder;
import it.unibo.paserver.service.IssueSettingService;
import it.unibo.paserver.service.MailingLogsService;
import it.unibo.paserver.service.UserSecretKeyService;
import it.unibo.paserver.service.UserService;

@SuppressWarnings("Duplicates")
@Component
public class AuthenticatorService {
	@Autowired
	private UserService userService;
	@Autowired
	private UserSecretKeyService userSecretKeyService;
	@Autowired
	private MailingLogsService mailingLogsService;
	@Autowired
	private IssueSettingService issueSettingService;
	/**
	 * Login
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	public AuthenticatorDetails doLogin(String username, String password) throws AuthenticationException {
		System.out.println(String.format("doLogin Username: %s , Password: %s", username, password));
		username = Useful.escapeSql(username);
		password = Useful.hashed(Useful.escapeSql(password), username);
		return doAuth(username, password);
	}

	/**
	 * Vinculando Convidado a uma conta principal
	 * 
	 * @param username
	 * @param password
	 * @param guestId
	 * @return
	 * @throws AuthenticationException
	 */
	public boolean doProgenitor(String username, String password, Long guestId) throws AuthenticationException {
		username = Useful.escapeSql(username);
		password = Useful.hashed(Useful.escapeSql(password), username);

		User user = userService.findByEmailAndPassword(username, password);
		System.out.println(String.format("doProgenitor Username: %s , Password: %s", username, password));
		if (user != null && user.getId() != guestId) {
			//Desfaz remocao simbolica
			userService.unRemoved(user.getId());
			userService.unRemoved(guestId);
			//Get
			User guest = userService.getUser(guestId);
			if (guest != null) {
				return doGather(guestId, user.getId());
			}
		}
		return false;
	}

	/**
	 * Reunir/Juntar
	 * 
	 * @param userId
	 * @param progenitorId
	 * @return
	 */
	public boolean doGather(Long userId, Long progenitorId) {
		if (userId != progenitorId) {			
			return userSecretKeyService.updateProgenitorId(userId, progenitorId);
		}
		return false;
	}

	/**
	 * Autenticacao com login e senha
	 * 
	 * @note NAO CHAMAR ESSA FUNCAO DIRETAMENTE
	 * @param username
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	private AuthenticatorDetails doAuth(String username, String password) throws AuthenticationException {
		User user = userService.findByEmailAndPassword(username, password);
		System.out.println(String.format("doAuth Username: %s , Password: %s", username, password));
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));

			UUID uuid = UUID.randomUUID();
			UserSecretKeyBuilder ukb = new UserSecretKeyBuilder();
			UserSecretKey k = ukb.setAll(user.getId(), uuid, false).build(true);

			if (userSecretKeyService.saveOrUpdate(k) != null) {
				AuthenticatorDetails authenticatorDetails = new AuthenticatorDetails();
				authenticatorDetails.setId(user.getId());
				authenticatorDetails.setUuid(uuid);
				authenticatorDetails.setToken(Cryptography.encryptToken(user.getId(), uuid, Role.ROLE_USER.toString()));
				authenticatorDetails.setAuthorities(authorities);
				authenticatorDetails.setInAppleReview(doAppleReview());
				//User
				user.setRemoved(false);
				user.setIsActive(true);
				userService.save(user);
				//Enabled	
				return authenticatorDetails;
			}
		}

		throw new UsernameNotFoundException("Username not found");
	}
	/**
	 * Se em revisao
	 * @return
	 */
	public boolean doAppleReview() {
		return issueSettingService.inAppleReview(1L);
	}

	/**
	 * Autenticacao com QR CODE
	 * 
	 * @param qrcode
	 * @return
	 */
	public AuthenticatorDetails doQrCode(String qrcode) throws AuthenticationException {
		// Token Direto
		MailingLogs m = mailingLogsService.findByQrCoded(Useful.escapeSql(qrcode), false);
		if (m != null) {
			User u = doUser(m.getUserId());
			if (u != null) {
				AuthenticatorDetails a = doAuth(u.getOfficialEmail(), u.getPassword());
				if (a != null) {
					m.setQrCodeUsed(true);
					mailingLogsService.saveOrUpdate(m);
					return a;
				}
			}
		}
		//Res
		return null;
	}

	/**
	 * Cria uma conta de visitante, se nao existir
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	public AuthenticatorDetails doGuest(String username, String password, HttpServletRequest request) throws AuthenticationException {
		String uuid = UUID.randomUUID().toString();
		if (doChooseUsername(username) == null) {
			UserBuilder ub = new UserBuilder();
			// X Device
			String xPlataform = request.getHeader("x-plataform");
			String xDeviceModel = request.getHeader("x-device-model");
			String xOsVersion = request.getHeader("x-os-version");
			String xManufacturer = request.getHeader("x-manufacturer");
			String xAppVersionName = request.getHeader("x-app-version-name");
			String xAppVersionCode = request.getHeader("x-app-version-code");

			ub.setxAppVersionCode(xAppVersionCode);
			ub.setxAppVersionName(xAppVersionName);
			ub.setxDeviceModel(xDeviceModel);
			ub.setxManufacturer(xManufacturer);
			ub.setxOsVersion(xOsVersion);
			ub.setxPlataform(xPlataform);
			// User Details
			ub.setCredentials(username, password);
			ub.setSocial(null);
			ub.setPhoto(null);
			ub.setSurname(" . ");
			ub.setCurrentCity(null);
			ub.setName(username);
			ub.setCurrentProvince(null);
			ub.setOfficialEmail(username);
			ub.setProjectEmail(username);
			ub.setDevice(String.format("%s %s", xManufacturer, xDeviceModel));
			ub.setCf(uuid);
			ub.setIccid(uuid);
			ub.setNewIccid(uuid);
			ub.setCurrentAddress(" ");
			ub.setBirthdate(null);
			ub.setRegistrationDateTime(new DateTime());
			ub.setCurrentZipCode(" ");
			ub.setGender(Gender.NONE);
			ub.setDocumentIdType(DocumentIdType.NONE);
			ub.setDocumentId("N/A");
			ub.setContactPhoneNumber(" ");
			ub.setProjectPhoneNumber(" ");
			ub.setUniCity(UniCity.FLORIANOPOLIS);
			ub.setUniCourse(UniCourse.SOCIAL);
			ub.setUniYear(0);
			ub.setUniDegree("N/A");
			ub.setUniDepartment(" ");
			ub.setUniSchool(UniSchool.OUTRO);
			ub.setUniIsSupplementaryYear(false);
			ub.setHasPhone(true);
			ub.setWantsPhone(false);
			ub.setIsActive(true);
			ub.setHasSIM(false);
			ub.setSimStatus(SimStatus.NO);
			ub.setBadges(new HashSet<Badge>());
			ub.setAgeRange(null);
			ub.setGuest(true);
			ub.setRemoved(false);
			ub.setIsActive(true);
			// Build
			User u = ub.build(true);
			if (userService.save(u) != null) {
				return doLogin(username, password);
			}
		}
		throw new UsernameNotFoundException("Username is Taken");
	}

	/**
	 * Criando Conta Padrao com Registro
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param notes
	 * @param occupation
	 * @param homePhoneNumber
	 * @param ageRange
	 * @param facebookUrl
	 * @param googleUrl
	 * @param twitterUrl
	 * @param instagramUrl
	 * @param youtubeUrl
	 * @param enumGender
	 * @param iu
	 * @param su
	 * @return
	 * @throws AuthenticationException
	 */
	public AuthenticatorDetails doSignUpAccount(String username, String password, String name, String notes, String occupation, String homePhoneNumber, String ageRange, String facebookUrl, String googleUrl, String twitterUrl,
			String instagramUrl, String youtubeUrl, Gender enumGender, Institutions iu, SchoolCourse su) throws AuthenticationException {
		String uuid = UUID.randomUUID().toString();
		if (doChooseUsername(username) == null) {
			UserBuilder ub = new UserBuilder();
			ub.setCredentials(username, password);
			ub.setName(name);
			ub.setGender(enumGender);
			ub.setAgeRange(ageRange);
			ub.setGuest(false);
			ub.setNotes(notes);
			ub.setHomePhoneNumber(homePhoneNumber);
			ub.setInstitutionId(iu);
			ub.setSchoolCourseId(su);

			ub.setOccupation(occupation);
			ub.setFacebookUrl(facebookUrl);
			ub.setGoogleUrl(googleUrl);
			ub.setTwitterUrl(twitterUrl);
			ub.setInstagramUrl(instagramUrl);
			ub.setYoutubeUrl(youtubeUrl);

			ub.setSocial(null);
			ub.setPhoto(null);
			ub.setSurname(" . ");
			ub.setCurrentCity(null);
			ub.setCurrentProvince(null);
			ub.setOfficialEmail(username);
			ub.setProjectEmail(username);
			ub.setDevice(null);
			ub.setCf(uuid);
			ub.setIccid(uuid);
			ub.setNewIccid(uuid);
			ub.setCurrentAddress(" ");
			ub.setBirthdate(null);
			ub.setRegistrationDateTime(new DateTime());
			ub.setCurrentZipCode(" ");

			ub.setDocumentIdType(DocumentIdType.NONE);
			ub.setDocumentId("N/A");
			ub.setContactPhoneNumber(" ");
			ub.setProjectPhoneNumber(" ");
			ub.setUniCity(UniCity.FLORIANOPOLIS);
			ub.setUniCourse(UniCourse.SOCIAL);
			ub.setUniYear(0);
			ub.setUniDegree("N/A");
			ub.setUniDepartment(" ");
			ub.setUniSchool(UniSchool.OUTRO);
			ub.setUniIsSupplementaryYear(false);
			ub.setHasPhone(true);
			ub.setWantsPhone(false);
			ub.setIsActive(true);
			ub.setRemoved(false);
			ub.setHasSIM(false);
			ub.setSimStatus(SimStatus.NO);
			ub.setBadges(new HashSet<Badge>());
			// Build
			User u = ub.build(true);
			if (userService.save(u) != null) {
				return doLogin(username, password);
			}
		}
		throw new UsernameNotFoundException("Username is Taken");
	}

	/**
	 * Checa de existe
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public boolean doCheck(String token) throws Exception {
		try {
			Map<String, Object> decrypt = doDecryptToken(token);
			if (decrypt.containsKey("uuid") && decrypt.containsKey("userId")) {
				UUID uuid = UUID.fromString((String) decrypt.get("uuid"));
				Long userId = (new Double(decrypt.get("userId").toString())).longValue();
				// Check
				return userSecretKeyService.doCheck(userId, uuid);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		throw new Exception("X-DECRYPT-TOKEN");
	}

	public Map<String, Object> doDecryptToken(String strToDecrypt) {
		return Cryptography.decryptToken(strToDecrypt);
	}

	/**
	 * Criar uma nova sessao
	 * 
	 * @param session
	 * @param request
	 * @param authenticatorDetails
	 * @return
	 */
	public boolean doSession(HttpSession session, HttpServletRequest request, AuthenticatorDetails authenticatorDetails) {
		HttpSession newSession = request.getSession();
		newSession.setAttribute("userId", authenticatorDetails.getId());
		UUID uuid = authenticatorDetails.getUuid();
		newSession.setAttribute("uuid", uuid.toString());
		newSession.setMaxInactiveInterval(864000);
		return true;
	}

	/**
	 * Invalida a sessao atual
	 * 
	 * @param session
	 * @return
	 */
	public boolean doLogout(HttpSession session, HttpServletRequest request) {
		try {
			session.invalidate();
			new SecurityContextLogoutHandler().logout(request, null, null);
		} catch (IllegalStateException e) {

		}
		return true;
	}

	/**
	 * Retorna o Usuario
	 * 
	 * @param userId
	 * @return
	 */
	public User doUser(Long userId) {
		return userService.getUser(userId);
	}

	/**
	 * Verifica se o login existe
	 * 
	 * @param userId
	 * @return
	 */
	public User doChooseUsername(String username) {
		return userService.getUser(username);
	}

	/**
	 * Atualizando conta
	 * 
	 * @param u
	 * @return
	 */
	public User doUpdateAccount(User u) {
		
		return userService.save(u);
	}
}
