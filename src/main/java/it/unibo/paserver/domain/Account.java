package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Account implements Serializable {

    private static final long serialVersionUID = -6503864251024997229L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastLogin;
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationDate;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "account_role")
    @Column(name = "role")
    private Set<Role> roles;

    private Integer removed = 0;
    private String name;
    private String email;
    private String phone;
    private String photo;
    @Column(name = "municipality", nullable = true)
    private String municipality = null;
    /**
     * Se eh admin
     */
    @Column(name = "isAdmin", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isAdmin = false;
    /**
     * Usuario que criou o outro
     */
    @Column(name = "parentId", nullable = true)
    private Long parentId = null;
    /**
     * Nivel mais alto do privilegio
     */
    @Column(name = "privilege", nullable = false)
    private int privilege = 0;
    @Column(name = "privilege2", nullable = false)
    private int privilege2 = 0;

    @ManyToOne
    @JoinColumn(name = "institutionId")
    private Institutions institution;

    public Institutions getInstitution() {
        return institution;
    }

    public void setInstitution(Institutions institution) {
        this.institution = institution;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public int getPrivilege2() {
        return privilege2;
    }

    public void setPrivilege(int privilege, boolean isPrimary) {
        if (isPrimary) {
            this.privilege = privilege;
        } else {
            this.privilege2 = privilege;
        }
    }

    public Account() {
        roles = new HashSet<Role>();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> newRoles) {
        if (newRoles == null) {
            throw new NullPointerException("newRoles can't be null");
        }
        roles = newRoles;
    }

    public Long getId() {
        return id;
    }

    public void setUuid(long uuid) {
        this.id = uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return String.format("%s", username);
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new NullPointerException();
        }
        roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getMunicipality() {
        return municipality;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getRemoved() {
        return removed;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
