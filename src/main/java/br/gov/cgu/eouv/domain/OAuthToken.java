package br.gov.cgu.eouv.domain;

import br.gov.cgu.eouv.result.rest.OAuthTokenRest;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
@Entity
@Table(name = "cgu_oauth_token")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OAuthToken {
    private static final long serialVersionUID = 5623823537805478066L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "expiresIn")
    private Long expiresIn;

    @Column(name = "tokenType")
    private String tokenType;

    @Column(name = "accessToken", columnDefinition = "TEXT", nullable = false)
    private String accessToken;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "expireDate", updatable = false, nullable = false)
    private DateTime expireDate = DateTime.now();

    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public OAuthToken() {
    }

    public OAuthToken(OAuthTokenRest authTokenResult) {
        this.setAccessToken(authTokenResult.getAccessToken());
        this.setExpiresIn(authTokenResult.getExpiresIn());
        this.setTokenType(authTokenResult.getTokenType());
        // Expire Date
        DateTime dt = new DateTime();
        dt = dt.plusSeconds(authTokenResult.getExpiresIn().intValue());
        // System.out.println(dt.toString());
        this.setExpireDate(dt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
    }

    public DateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(DateTime expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isExpired() {
        return this.expireDate.minusDays(1).isBeforeNow();
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "id=" + id +
                ", expiresIn=" + expiresIn +
                ", tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", expireDate=" + expireDate +
                ", removed=" + removed +
                '}';
    }
}
