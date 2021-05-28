package it.unibo.paserver.web.security.v1;

import it.unibo.paserver.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountUserDetails implements UserDetails {

	private static final long serialVersionUID = 8712563389685312440L;
	private User user;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	private String token;

	public AccountUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities.addAll(authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection(authorities);
	}

	public long getId() {
		return user.getId();
	}

	@Override
	public String getPassword() {
		return user.getPassword();

	}

	@Override
	public String getUsername() {
		return user.getOfficialEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

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

}
