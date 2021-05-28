package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.Role;

import org.joda.time.DateTime;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountBuilder extends EntityBuilder<Account> {

    private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("sha-256");

    @Override
    void initEntity() {
        entity = new Account();
    }

    public AccountBuilder credentials(String username, String password) {
        entity.setUsername(username);
        entity.setPassword(encoder.encodePassword(password, username));
        return this;
    }

    public AccountBuilder creationDate(DateTime creationDate) {
        entity.setCreationDate(creationDate);
        return this;
    }

    public AccountBuilder addRole(Role role) {
        entity.addRole(role);
        return this;
    }

    public AccountBuilder addName(String name) {
        entity.setName(name);
        return this;
    }


    public AccountBuilder addMunicipality(String municipality) {
        entity.setMunicipality(municipality);
        return this;
    }

    public AccountBuilder addEmail(String email) {
        entity.setEmail(email);
        return this;
    }

    public AccountBuilder addPhone(String phone) {
        entity.setPhone(phone);
        return this;
    }

    public AccountBuilder addUsername(String username) {
        entity.setUsername(username);
        return this;
    }

    public AccountBuilder addPassword(String password) {
        entity.setPassword(password);
        return this;
    }

    public AccountBuilder addUuid(long uuid) {
        entity.setUuid(uuid);
        return this;
    }


    public AccountBuilder setAdmin(boolean isAdmin) {
        entity.setAdmin(isAdmin);
        return this;
    }

    public AccountBuilder setParentId(Long parentId) {
        entity.setParentId(parentId);
        return this;
    }

    public AccountBuilder setPrivilege(int privilege) {
        entity.setPrivilege(privilege);
        return this;
    }

    public AccountBuilder setPrivilege(int privilege, boolean isPrimary) {
        entity.setPrivilege(privilege, isPrimary);
        return this;
    }

    public AccountBuilder setInstitutions(Institutions i) {
        entity.setInstitution(i);
        return this;
    }

    @Override
    Account assembleEntity() {
        return entity;
    }
}
