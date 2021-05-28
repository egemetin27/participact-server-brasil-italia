package it.unibo.paserver.rest.controller.v2;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ResponseJsonRest;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.RecoverPasswordService;
import it.unibo.paserver.web.security.v2.AuthenticatorService;

/**
 * Verifica se esta logado e compartilha informacoes comuns entre as classes json/restful
 * @author Claudio
 *
 */
@RestController
public class ApplicationRestfulController {
	@Autowired
	protected AuthenticatorService authenticatorService;
	@Autowired
	protected MessageSource messageSource;
	protected ResponseJsonRest response = new ResponseJsonRest();
	private Long userId;
	private UUID uuid;

	/**
	 * Carrega as configuracoes do usuario logado
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void isUserDetails(HttpServletRequest request) throws Exception {
		//String headerAuthorization = request.getHeader("x-authorization");
		String headerAuthorization = getHeaderAuthorization(request);
		if (Validator.isEmptyString(headerAuthorization)) {
			throw new Exception("X-AUTHORIZATION");
		} else {
			Map<String, Object> decrypt = authenticatorService.doDecryptToken(headerAuthorization);
			if (decrypt.containsKey("uuid") && decrypt.containsKey("userId")) {
				this.setUserId((new Double(decrypt.get("userId").toString())).longValue());
				this.setUuid(UUID.fromString((String) decrypt.get("uuid")));
			}
		}
	}

	public String getHeaderAuthorization(HttpServletRequest request) {
		return request.getHeader("x-authorization");
	}
	
	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public User getUser() {
		return getAccount(userId);
	}
	
	public User getAccount(long userId) {
		return authenticatorService.doUser(userId);
	}
	
	public Long getProgenitorId(Long guestId) {
		User u = authenticatorService.doUser(guestId);
		/*System.out.println(u.getProgenitorId());
		System.out.println(guestId);
		System.out.println(Long.valueOf(u.getProgenitorId()) != Long.valueOf(guestId));
		
		System.out.println(u.getProgenitorId().getClass().getName());
		System.out.println(guestId.getClass().getName());
		
		System.out.println(u.getProgenitorId().compareTo(guestId));
		System.out.println(u.getProgenitorId().equals(guestId));*/
	
		if(u != null && u.getProgenitorId() != null && u.getProgenitorId() > 0 && u.getProgenitorId().longValue() != guestId.longValue()) {
			return  u.getProgenitorId();
		}
		return 0L;
	}
	
	public AuthenticatorService getAuthenticatorService() {
		return this.authenticatorService;
	}
}
