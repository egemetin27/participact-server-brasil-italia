package br.gov.cgu.eouv.domain;

import br.com.bergmannsoft.utils.Useful;
import br.gov.cgu.eouv.result.rest.HistoricoAcaoDTO;
import br.gov.cgu.eouv.result.soap.HistoricoAcaoManifestacaoResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
@Entity
@Table(name = "cgu_manifestacao_historico")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManifestacaoHistorico {

    private static final long serialVersionUID = 2447571029074162174L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "relationship_id")
    private Long relationshipId;

    @Column(name = "acao", columnDefinition = "TEXT", nullable = true)
    private String acao;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "data", updatable = true, nullable = false)
    private DateTime dataAcao;
    @Column(name = "info", columnDefinition = "TEXT", nullable = true)
    private String infoAdicionais;
    @Column(name = "responsavel", columnDefinition = "TEXT", nullable = true)
    private String responsavel;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public ManifestacaoHistorico(HistoricoAcaoManifestacaoResult h) {
        this.setAcao(h.getAcao());
        this.setDataAcao(h.getDataAcao());
        this.setInfoAdicionais(h.getInfoAdicionais());
        this.setResponsavel(h.getResponsavel());
    }

    public ManifestacaoHistorico(HistoricoAcaoDTO hist) {
        this.setAcao(hist.getDescTipoAcaoManifestacao());
        this.setDataAcao(Useful.converteStringToDate(Useful.datetimeSystemToDb(hist.getDataHoraAcao())));
        this.setInfoAdicionais(hist.getInformacoesAdicionais());
        this.setResponsavel(hist.getResponsavel());
    }

    public ManifestacaoHistorico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public DateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(DateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    public String getInfoAdicionais() {
        return infoAdicionais;
    }

    public void setInfoAdicionais(String infoAdicionais) {
        this.infoAdicionais = infoAdicionais;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }
}
