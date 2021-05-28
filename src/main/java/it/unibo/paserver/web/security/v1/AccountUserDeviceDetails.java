package it.unibo.paserver.web.security.v1;

import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountUserDeviceDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4288259471817837990L;
	private User user;
	private UserDevice userDevice;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public AccountUserDeviceDetails(User user, UserDevice userDevice,
			Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities.addAll(authorities);
		this.userDevice = userDevice;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection(authorities);
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
	
	public String getImei() {
		return userDevice.getImei();
	}
}
