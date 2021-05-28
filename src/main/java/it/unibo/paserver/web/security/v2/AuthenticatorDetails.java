package it.unibo.paserver.web.security.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthenticatorDetails {
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	private String token;
	private Long id;
	private UUID uuid;
	private boolean inAppleReview = false;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities
	 *            the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the inAppleReview
	 */
	public boolean isInAppleReview() {
		return inAppleReview;
	}

	/**
	 * @param inAppleReview the inAppleReview to set
	 */
	public void setInAppleReview(boolean inAppleReview) {
		this.inAppleReview = inAppleReview;
	}	
}
