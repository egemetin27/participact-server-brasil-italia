package it.unibo.paserver.web.security.v2;

import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.bergmannsoft.utils.Validator;

@Component
public class AuthenticatorRequestInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private AuthenticatorService authenticatorService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");
		// doLog
		doRequestLogging(request, response);
		// Get
		String headerAuthorization = request.getHeader("x-authorization");
		String headerPlataform = request.getHeader("x-plataform");
		String headerDeviceModel = request.getHeader("x-device-model");
		String headerOsVersion = request.getHeader("x-os-version");
		String headerManufacturer = request.getHeader("x-manufacturer");
		String headerAppVersionName = request.getHeader("x-app-version-name");
		String headerAppVersionCode = request.getHeader("x-app-version-code");
		// Validate
		if (Validator.isEmptyString(headerAuthorization)) {
			throw new Exception("X-AUTHORIZATION");
		} else if (Validator.isEmptyString(headerPlataform)) {
			throw new Exception("X-PLATAFORM");

		} else if (Validator.isEmptyString(headerDeviceModel)) {
			throw new Exception("X-DEVICE-MODEL");

		} else if (Validator.isEmptyString(headerOsVersion)) {
			throw new Exception("X-OS-VERSION");

		} else if (Validator.isEmptyString(headerManufacturer)) {
			throw new Exception("X-MANUFACTURER");

		} else if (Validator.isEmptyString(headerAppVersionName)) {
			throw new Exception("X-APP-VERSION-NAME");

		} else if (Validator.isEmptyString(headerAppVersionCode)) {
			throw new Exception("X-APP-VERSION-CODE");

		} else if (!Validator.isValidStringLength(headerAuthorization, 45, 255)) {
			throw new Exception("X-AUTHORIZATION");
		} else if (isUserLogged(request.getSession(), headerAuthorization)) {
			System.out.println("OK-SESSION");

		} else if (authenticatorService.doCheck(headerAuthorization)) {
			if(addToModelUserDetails(request, headerAuthorization)) {
				System.out.println("OK-addToModelUserDetails");	
			}else {
				throw new Exception("X-USERDETAILS");	
			}
		} else {
			throw new Exception("X-SESSION");
		}
		return true;
	}

	/**
	 * Se esta Logado
	 */
	private boolean isUserLogged(HttpSession session, String headerAuthorization) {
		try {
			Long userId = (Long) session.getAttribute("userId");
			UUID uuid = UUID.fromString((String) session.getAttribute("uuid"));

			Map<String, Object> decrypt = authenticatorService.doDecryptToken(headerAuthorization);
			if (decrypt.containsKey("uuid") && decrypt.containsKey("userId")) {
				UUID decryptUuid = UUID.fromString((String) decrypt.get("uuid"));
				Long decryptUserId = (new Double(decrypt.get("userId").toString())).longValue();

				return userId.equals(decryptUserId) && uuid.equals(decryptUuid);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * 
	 * @param session
	 * @param headerAuthorization
	 * @return
	 */
	private boolean addToModelUserDetails(HttpServletRequest request, String headerAuthorization) {
		try {
			Map<String, Object> decrypt = authenticatorService.doDecryptToken(headerAuthorization);
			if (decrypt.containsKey("uuid") && decrypt.containsKey("userId")) {
				Long userId = (new Double(decrypt.get("userId").toString())).longValue();
				String uuid = (String) decrypt.get("uuid");

				HttpSession newSession = request.getSession();
				newSession.setAttribute("userId", userId);
				newSession.setAttribute("uuid", uuid);
				newSession.setMaxInactiveInterval(86400);
				
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * Log da Requisicao
	 * 
	 * @param request
	 * @param response
	 */
	private void doRequestLogging(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("\n REQUEST: \n");

		@SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + " " + value);
		}
	}
}
