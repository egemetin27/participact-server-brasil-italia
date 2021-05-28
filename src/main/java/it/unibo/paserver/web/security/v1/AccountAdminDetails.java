package it.unibo.paserver.web.security.v1;

import it.unibo.paserver.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AccountAdminDetails implements UserDetails {

    private static final long serialVersionUID = 8712563389685312440L;
    private Account account;
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public AccountAdminDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        this.account = account;
        this.authorities.addAll(authorities);
    }

    public long getId() {
        return account.getId();
    }

    public long getParentId() {
        return account.getParentId();
    }

    public String getMunicipality() {
        return account.getMunicipality();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
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

}
