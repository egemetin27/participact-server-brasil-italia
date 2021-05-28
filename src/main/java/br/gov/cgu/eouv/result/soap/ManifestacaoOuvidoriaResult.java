package br.gov.cgu.eouv.result.soap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 07/05/2019
 **/
public class ManifestacaoOuvidoriaResult {
    private int codSiorgOrgaoAssociadoOuvDestinataria;
    private String descSituacaoManifestacao;
    private String descSubAssunto;
    private String descTipoManifestacao;
    private String observacao;
    private String numProtocolo;
    private List<HistoricoAcaoManifestacaoResult> listaHistoricosManifestacao = new ArrayList<>();
    private List<RespostasManifestacaoResult> listaRespostasManifestacao = new ArrayList<>();

    public ManifestacaoOuvidoriaResult() {
    }


    public int getCodSiorgOrgaoAssociadoOuvDestinataria() {
        return codSiorgOrgaoAssociadoOuvDestinataria;
    }

    public void setCodSiorgOrgaoAssociadoOuvDestinataria(int codSiorgOrgaoAssociadoOuvDestinataria) {
        this.codSiorgOrgaoAssociadoOuvDestinataria = codSiorgOrgaoAssociadoOuvDestinataria;
    }

    public String getDescSituacaoManifestacao() {
        return descSituacaoManifestacao;
    }

    public void setDescSituacaoManifestacao(String descSituacaoManifestacao) {
        this.descSituacaoManifestacao = descSituacaoManifestacao;
    }

    public String getDescSubAssunto() {
        return descSubAssunto;
    }

    public void setDescSubAssunto(String descSubAssunto) {
        this.descSubAssunto = descSubAssunto;
    }

    public String getDescTipoManifestacao() {
        return descTipoManifestacao;
    }

    public void setDescTipoManifestacao(String descTipoManifestacao) {
        this.descTipoManifestacao = descTipoManifestacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<HistoricoAcaoManifestacaoResult> getListaHistoricosManifestacao() {
        return listaHistoricosManifestacao;
    }

    public void setListaHistoricosManifestacao(List<HistoricoAcaoManifestacaoResult> listaHistoricosManifestacao) {
        this.listaHistoricosManifestacao = listaHistoricosManifestacao;
    }

    public String getNumProtocolo() {
        return numProtocolo;
    }

    public void setNumProtocolo(String numProtocolo) {
        this.numProtocolo = numProtocolo;
    }

    public List<RespostasManifestacaoResult> getListaRespostasManifestacao() {
        return listaRespostasManifestacao;
    }

    public void setListaRespostasManifestacao(List<RespostasManifestacaoResult> listaRespostasManifestacao) {
        this.listaRespostasManifestacao = listaRespostasManifestacao;
    }

    @Override
    public String toString() {
        return "ManifestacaoOuvidoriaResult{" +
                "codSiorgOrgaoAssociadoOuvDestinataria=" + codSiorgOrgaoAssociadoOuvDestinataria +
                ", descSituacaoManifestacao='" + descSituacaoManifestacao + '\'' +
                ", descSubAssunto='" + descSubAssunto + '\'' +
                ", descTipoManifestacao='" + descTipoManifestacao + '\'' +
                ", observacao='" + observacao + '\'' +
                ", numProtocolo='" + numProtocolo + '\'' +
                ", listaHistoricosManifestacao=" + listaHistoricosManifestacao +
                ", listaRespostasManifestacao=" + listaRespostasManifestacao +
                '}';
    }
}
