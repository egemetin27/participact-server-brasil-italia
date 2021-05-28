package br.gov.cgu.eouv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 08/04/2019
 **/
@Entity
@Table(name = "cgu_orgaos_siorg")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrgaoSiorgOuvidoria {

    @Id
    @Column(name = "codOrg")
    private Long codOrg;
    @Column(name = "nomOrgao", columnDefinition = "TEXT", nullable = true)
    private String nomOrgao;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public OrgaoSiorgOuvidoria() {
    }

    public OrgaoSiorgOuvidoria(Long codOrgao, String nomOrgao) {
        setCodOrg(codOrgao);
        setNomOrgao(nomOrgao);
    }

    public Long getCodOrg() {
        return codOrg;
    }

    public void setCodOrg(long codOrg) {
        this.codOrg = codOrg;
    }

    public String getNomOrgao() {
        return nomOrgao;
    }

    public void setNomOrgao(String nomOrgao) {
        this.nomOrgao = nomOrgao;
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
