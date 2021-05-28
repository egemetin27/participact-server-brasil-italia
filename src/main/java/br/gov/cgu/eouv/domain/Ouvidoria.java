package br.gov.cgu.eouv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
@Entity
@Table(name = "cgu_ouvidorias_v4")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ouvidoria {

    @Id
    @Column(name = "idouvidoria")
    private Long idOuvidoria;

    @Column(name = "idorgaoouvidoria")
    private Long idOrgaoOuvidoria;

    @Column(name = "nomeorgaoouvidoria", columnDefinition = "TEXT", nullable = true)
    private String nomeOrgaoOuvidoria;

    @Column(name = "subassuntosouvidoria", columnDefinition = "TEXT", nullable = true)
    private String subAssuntosOuvidoria;

    @Column(name = "indenviadenunciascgupad")
    private Boolean indEnviaDenunciasCGUPAD = false;

    @Column(name = "indenviadenunciascgupj")
    private Boolean indEnviaDenunciasCGUPJ = false;

    @Column(name = "hasAllowOmbudsman")
    @Type(type = "org.hibernate.type.BooleanType")
    private Boolean hasAllowOmbudsman = false;

    @Column(name = "idesfera")
    private Long idEsfera;

    @Column(name = "descesfera", columnDefinition = "TEXT", nullable = true)
    private String descEsfera;

    @Column(name = "idmunicipio")
    private Long idMunicipio;

    @Column(name = "descmunicipio", columnDefinition = "TEXT", nullable = true)
    private String descMunicipio;

    @Column(name = "SigUf")
    private String sigUf;

    @Column(name = "DescUf", columnDefinition = "TEXT", nullable = true)
    private String descUf;

    @Column(name = "dataadesaoeouvpadrao", nullable = true)
    private String dataAdesaoEOuvPadrao;

    @Column(name = "dataadesaosimplifique", nullable = true)
    private String dataAdesaoSimplifique;

    @Column(name = "datainativacao", nullable = true)
    private String dataInativacao;


    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public Ouvidoria() {
    }

    public Ouvidoria(Long idOuvidoria, Long idOrgaoOuvidoria, String nomeOrgaoOuvidoria, String subAssuntosOuvidoria, Boolean indEnviaDenunciasCGUPAD, Boolean indEnviaDenunciasCGUPJ, Long idEsfera, String descEsfera, Long idMunicipio, String descMunicipio, String dataAdesaoEOuvPadrao, String dataAdesaoSimplifique, String dataInativacao) {
        this.idOuvidoria = idOuvidoria;
        this.idOrgaoOuvidoria = idOrgaoOuvidoria;
        this.nomeOrgaoOuvidoria = nomeOrgaoOuvidoria;
        this.subAssuntosOuvidoria = subAssuntosOuvidoria;
        this.indEnviaDenunciasCGUPAD = indEnviaDenunciasCGUPAD;
        this.indEnviaDenunciasCGUPJ = indEnviaDenunciasCGUPJ;
        this.idEsfera = idEsfera;
        this.descEsfera = descEsfera;
        this.idMunicipio = idMunicipio;
        this.descMunicipio = descMunicipio;
        this.dataAdesaoEOuvPadrao = dataAdesaoEOuvPadrao;
        this.dataAdesaoSimplifique = dataAdesaoSimplifique;
        this.dataInativacao = dataInativacao;
    }

    public Ouvidoria(Long idOuvidoria, String nomeOuvidoria) {
        this.setIdOuvidoria(idOuvidoria);
        this.setNomeOrgaoOuvidoria(nomeOuvidoria);
    }

    public Long getIdOuvidoria() {
        return idOuvidoria;
    }

    public void setIdOuvidoria(Long idOuvidoria) {
        this.idOuvidoria = idOuvidoria;
    }

    public Long getIdOrgaoOuvidoria() {
        return idOrgaoOuvidoria;
    }

    public void setIdOrgaoOuvidoria(Long idOrgaoOuvidoria) {
        this.idOrgaoOuvidoria = idOrgaoOuvidoria;
    }

    public String getNomeOrgaoOuvidoria() {
        return nomeOrgaoOuvidoria;
    }

    public void setNomeOrgaoOuvidoria(String nomeOrgaoOuvidoria) {
        this.nomeOrgaoOuvidoria = nomeOrgaoOuvidoria;
    }

    public String getSubAssuntosOuvidoria() {
        return subAssuntosOuvidoria;
    }

    public void setSubAssuntosOuvidoria(String subAssuntosOuvidoria) {
        this.subAssuntosOuvidoria = subAssuntosOuvidoria;
    }

    public Boolean getIndEnviaDenunciasCGUPAD() {
        return indEnviaDenunciasCGUPAD;
    }

    public void setIndEnviaDenunciasCGUPAD(Boolean indEnviaDenunciasCGUPAD) {
        this.indEnviaDenunciasCGUPAD = indEnviaDenunciasCGUPAD;
    }

    public Boolean getIndEnviaDenunciasCGUPJ() {
        return indEnviaDenunciasCGUPJ;
    }

    public void setIndEnviaDenunciasCGUPJ(Boolean indEnviaDenunciasCGUPJ) {
        this.indEnviaDenunciasCGUPJ = indEnviaDenunciasCGUPJ;
    }

    public Long getIdEsfera() {
        return idEsfera;
    }

    public void setIdEsfera(Long idEsfera) {
        this.idEsfera = idEsfera;
    }

    public String getDescEsfera() {
        return descEsfera;
    }

    public void setDescEsfera(String descEsfera) {
        this.descEsfera = descEsfera;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getDescMunicipio() {
        return descMunicipio;
    }

    public void setDescMunicipio(String descMunicipio) {
        this.descMunicipio = descMunicipio;
    }

    public String getDataAdesaoEOuvPadrao() {
        return dataAdesaoEOuvPadrao;
    }

    public void setDataAdesaoEOuvPadrao(String dataAdesaoEOuvPadrao) {
        this.dataAdesaoEOuvPadrao = dataAdesaoEOuvPadrao;
    }

    public String getDataAdesaoSimplifique() {
        return dataAdesaoSimplifique;
    }

    public void setDataAdesaoSimplifique(String dataAdesaoSimplifique) {
        this.dataAdesaoSimplifique = dataAdesaoSimplifique;
    }

    public String getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(String dataInativacao) {
        this.dataInativacao = dataInativacao;
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

    public String getSigUf() {
        return sigUf;
    }

    public void setSigUf(String sigUf) {
        this.sigUf = sigUf;
    }

    public String getDescUf() {
        return descUf;
    }

    public void setDescUf(String descUf) {
        this.descUf = descUf;
    }

    public Boolean getHasAllowOmbudsman() {
        return hasAllowOmbudsman;
    }

    public void setHasAllowOmbudsman(Boolean hasAllowOmbudsman) {
        this.hasAllowOmbudsman = hasAllowOmbudsman;
    }
}
