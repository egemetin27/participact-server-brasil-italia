package br.gov.cgu.eouv.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 16/07/2019
 **/
@Entity
@Table(name = "cgu_ibge_mun")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CguIbgeMun {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1693679870550016827L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic
    @Column(name = "codmun7")
    private Integer codmun7;
    @Basic
    @Column(name = "codmun6")
    private Integer codmun6;
    @Basic
    @Column(name = "nomemun", length = -1)
    private String nomemun;
    @Basic
    @Column(name = "coduf", length = 20)
    private String coduf;
    @Basic
    @Column(name = "siglauf", length = 20)
    private String siglauf;
    @Basic
    @Column(name = "nomeuf", length = -1)
    private String nomeuf;
    @Basic
    @Column(name = "codur_n1")
    private Integer codurN1;
    @Basic
    @Column(name = "divur_n1", length = -1)
    private String divurN1;
    @Basic
    @Column(name = "codur_n2")
    private Integer codurN2;
    @Basic
    @Column(name = "divur_n2", length = -1)
    private String divurN2;
    @Basic
    @Column(name = "divur_n2_sub", length = -1)
    private String divurN2Sub;
    @Basic
    @Column(name = "codur_n3")
    private Integer codurN3;
    @Basic
    @Column(name = "divur_n3", length = -1)
    private String divurN3;
    @Basic
    @Column(name = "cod_n1_polo")
    private Integer codN1Polo;
    @Basic
    @Column(name = "n1_tipo", length = 20)
    private String n1Tipo;
    @Basic
    @Column(name = "cod_n2_polo")
    private Integer codN2Polo;
    @Basic
    @Column(name = "n2_tipo", length = 20)
    private String n2Tipo;
    @Basic
    @Column(name = "cod_n3_polo")
    private Integer codN3Polo;
    @Basic
    @Column(name = "n3_tipo", length = 20)
    private String n3Tipo;

    @Basic
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updatedate")
    private DateTime updatedate;
    @Basic
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationdate")
    private DateTime creationdate;
    @Basic
    @Type(type = "org.hibernate.type.BooleanType")
    @Column(name = "removed")
    private Boolean removed = false;

    @Basic
    @Column(name = "geo_tipo", length = 20)
    private String geoTipo;
    @Basic
    @Column(name = "geo_lat")
    private Double geoLat;
    @Basic
    @Column(name = "geo_long")
    private Double geoLong;
    @Basic
    @Column(name = "geo_alt")
    private Double geoAlt;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Integer getCodmun7() {
        return codmun7;
    }

    public void setCodmun7(Integer codmun7) {
        this.codmun7 = codmun7;
    }


    public Integer getCodmun6() {
        return codmun6;
    }

    public void setCodmun6(Integer codmun6) {
        this.codmun6 = codmun6;
    }


    public String getNomemun() {
        return nomemun;
    }

    public void setNomemun(String nomemun) {
        this.nomemun = nomemun;
    }


    public String getCoduf() {
        return coduf;
    }

    public void setCoduf(String coduf) {
        this.coduf = coduf;
    }


    public String getSiglauf() {
        return siglauf;
    }

    public void setSiglauf(String siglauf) {
        this.siglauf = siglauf;
    }


    public String getNomeuf() {
        return nomeuf;
    }

    public void setNomeuf(String nomeuf) {
        this.nomeuf = nomeuf;
    }


    public Integer getCodurN1() {
        return codurN1;
    }

    public void setCodurN1(Integer codurN1) {
        this.codurN1 = codurN1;
    }


    public String getDivurN1() {
        return divurN1;
    }

    public void setDivurN1(String divurN1) {
        this.divurN1 = divurN1;
    }


    public Integer getCodurN2() {
        return codurN2;
    }

    public void setCodurN2(Integer codurN2) {
        this.codurN2 = codurN2;
    }


    public String getDivurN2() {
        return divurN2;
    }

    public void setDivurN2(String divurN2) {
        this.divurN2 = divurN2;
    }


    public String getDivurN2Sub() {
        return divurN2Sub;
    }

    public void setDivurN2Sub(String divurN2Sub) {
        this.divurN2Sub = divurN2Sub;
    }


    public Integer getCodurN3() {
        return codurN3;
    }

    public void setCodurN3(Integer codurN3) {
        this.codurN3 = codurN3;
    }


    public String getDivurN3() {
        return divurN3;
    }

    public void setDivurN3(String divurN3) {
        this.divurN3 = divurN3;
    }


    public Integer getCodN1Polo() {
        return codN1Polo;
    }

    public void setCodN1Polo(Integer codN1Polo) {
        this.codN1Polo = codN1Polo;
    }


    public String getN1Tipo() {
        return n1Tipo;
    }

    public void setN1Tipo(String n1Tipo) {
        this.n1Tipo = n1Tipo;
    }


    public Integer getCodN2Polo() {
        return codN2Polo;
    }

    public void setCodN2Polo(Integer codN2Polo) {
        this.codN2Polo = codN2Polo;
    }


    public String getN2Tipo() {
        return n2Tipo;
    }

    public void setN2Tipo(String n2Tipo) {
        this.n2Tipo = n2Tipo;
    }


    public Integer getCodN3Polo() {
        return codN3Polo;
    }

    public void setCodN3Polo(Integer codN3Polo) {
        this.codN3Polo = codN3Polo;
    }


    public String getN3Tipo() {
        return n3Tipo;
    }

    public void setN3Tipo(String n3Tipo) {
        this.n3Tipo = n3Tipo;
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


    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }


    public String getGeoTipo() {
        return geoTipo;
    }

    public void setGeoTipo(String geoTipo) {
        this.geoTipo = geoTipo;
    }


    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }


    public Double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(Double geoLong) {
        this.geoLong = geoLong;
    }


    public Double getGeoAlt() {
        return geoAlt;
    }

    public void setGeoAlt(Double geoAlt) {
        this.geoAlt = geoAlt;
    }


    @Override
    public String toString() {
        return "CguIbgeMunEntity{" +
                "id=" + id +
                ", codmun7=" + codmun7 +
                ", codmun6=" + codmun6 +
                ", nomemun='" + nomemun + '\'' +
                ", coduf='" + coduf + '\'' +
                ", siglauf='" + siglauf + '\'' +
                ", nomeuf='" + nomeuf + '\'' +
                ", codurN1=" + codurN1 +
                ", divurN1='" + divurN1 + '\'' +
                ", codurN2=" + codurN2 +
                ", divurN2='" + divurN2 + '\'' +
                ", divurN2Sub='" + divurN2Sub + '\'' +
                ", codurN3=" + codurN3 +
                ", divurN3='" + divurN3 + '\'' +
                ", codN1Polo=" + codN1Polo +
                ", n1Tipo='" + n1Tipo + '\'' +
                ", codN2Polo=" + codN2Polo +
                ", n2Tipo='" + n2Tipo + '\'' +
                ", codN3Polo=" + codN3Polo +
                ", n3Tipo='" + n3Tipo + '\'' +
                ", updatedate=" + updatedate +
                ", creationdate=" + creationdate +
                ", removed=" + removed +
                ", geoTipo='" + geoTipo + '\'' +
                ", geoLat=" + geoLat +
                ", geoLong=" + geoLong +
                ", geoAlt=" + geoAlt +
                '}';
    }
}
