package br.gov.cgu.eouv.domain;

import br.gov.cgu.eouv.result.rest.RespostaManifestacaoDTO;
import br.gov.cgu.eouv.result.soap.RespostasManifestacaoResult;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;
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
@Table(name = "cgu_manifestacao_resposta")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManifestacaoResposta {

    private static final long serialVersionUID = 8006372867100949521L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "relationship_id")
    private Long relationshipId;

    @Column(name = "IdRespostaManifestacao")
    private Integer IdRespostaManifestacao;

    @Column(name = "IdTipoRespostaManifestacao")
    private Integer IdTipoRespostaManifestacao;

    @Column(name = "tipo", columnDefinition = "TEXT", nullable = true)
    private String tipo;

    @Column(name = "texto", columnDefinition = "TEXT", nullable = true)
    private String texto;

    @Column(name = "respondente", columnDefinition = "TEXT", nullable = true)
    private String respondente;

    @Column(name = "datapublicacao", columnDefinition = "TEXT", nullable = true)
    private String dataPublicacao;

    @Column(name = "datacompromisso", columnDefinition = "TEXT", nullable = true)
    private String dataCompromisso;

    @Column(name = "decisao", columnDefinition = "TEXT", nullable = true)
    private String decisao;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "dataResposta", updatable = true, nullable = true)
    private DateTime dataResposta = DateTime.now();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public ManifestacaoResposta(RespostasManifestacaoResult r) {
        this.setDecisao(r.getDecisao());
        this.setDataCompromisso(r.getDataCompromisso());
        this.setDataPublicacao(r.getDataPublicacao());
        this.setRespondente(r.getRespondente());
        this.setTexto(r.getTexto());
        this.setTipo(r.getTipo());
        this.setDataResposta(r.getDataResposta());
    }


    public ManifestacaoResposta(RespostaManifestacaoDTO resp) {
        this.setDecisao(resp.getDecisao() != null ? resp.getDecisao().getDescricaoDecisao() : null);
        this.setDataCompromisso(resp.getDataCompromisso());
        this.setTexto(resp.getTxtResposta());
        this.setTipo(resp.getTipoRespostaManifestacao().getDescTipoRespostaManifestacao());
        this.setIdRespostaManifestacao(resp.getIdRespostaManifestacao());
        this.setIdTipoRespostaManifestacao(resp.getTipoRespostaManifestacao().getIdTipoRespostaManifestacao());
    }

    public ManifestacaoResposta() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRespondente() {
        return respondente;
    }

    public void setRespondente(String respondente) {
        this.respondente = respondente;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getDataCompromisso() {
        return dataCompromisso;
    }

    public void setDataCompromisso(String dataCompromisso) {
        this.dataCompromisso = dataCompromisso;
    }

    public String getDecisao() {
        return decisao;
    }

    public void setDecisao(String decisao) {
        this.decisao = decisao;
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

    public DateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(DateTime dataResposta) {
        this.dataResposta = dataResposta;
    }

    public Integer getIdRespostaManifestacao() {
        return IdRespostaManifestacao;
    }

    public void setIdRespostaManifestacao(Integer idRespostaManifestacao) {
        IdRespostaManifestacao = idRespostaManifestacao;
    }

    public Integer getIdTipoRespostaManifestacao() {
        return IdTipoRespostaManifestacao;
    }

    public void setIdTipoRespostaManifestacao(Integer idTipoRespostaManifestacao) {
        IdTipoRespostaManifestacao = idTipoRespostaManifestacao;
    }
}
