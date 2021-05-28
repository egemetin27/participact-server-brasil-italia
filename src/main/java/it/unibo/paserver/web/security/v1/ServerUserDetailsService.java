package it.unibo.paserver.web.security.v1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.UserRepository;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.UserService;

@Component
public class ServerUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Username ?
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username was empty");
        } else {
            username = username.toLowerCase();
        }
        // Usuario dashboard?
        Account account = accountService.getAccount(username);
        if (account != null) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            for (Role role : account.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
            }
            account.setLastLogin(new DateTime());
            accountService.save(account);
            return new AccountAdminDetails(account, grantedAuthorities);
        }
        // Usuario participante?
        User user = userService.getUser(username);
        if (user != null) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
            return new AccountUserDetails(user, grantedAuthorities);
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
