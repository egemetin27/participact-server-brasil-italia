package br.gov.cgu.eouv.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Cgu Crontab
 */
@Entity
@Table(name = "cgu_crontab")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CguCrontab {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1693679870550016827L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic
    @Column(name = "hh")
    private Integer hh;
    @Basic
    @Column(name = "ss")
    private Integer ss;
    @Basic
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updatedate")
    private DateTime updatedate;
    @Basic
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationdate")
    private DateTime creationdate;
    @Basic
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "deliverydate")
    private DateTime deliverydate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHh() {
        return hh;
    }

    public void setHh(Integer hh) {
        this.hh = hh;
    }

    public Integer getSs() {
        return ss;
    }

    public void setSs(Integer ss) {
        this.ss = ss;
    }

    public DateTime getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(DateTime updatedate) {
        this.updatedate = updatedate;
    }

    public DateTime getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(DateTime creationdate) {
        this.creationdate = creationdate;
    }

    public DateTime getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(DateTime deliverydate) {
        this.deliverydate = deliverydate;
    }
}
