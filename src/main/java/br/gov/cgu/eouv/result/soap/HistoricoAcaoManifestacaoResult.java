package br.gov.cgu.eouv.result.soap;

import org.joda.time.DateTime;

/**
 * @author Claudio
 * @project participact-server
 * @date 07/05/2019
 **/
public class HistoricoAcaoManifestacaoResult {
    private String acao;
    private DateTime dataAcao;
    private String infoAdicionais;
    private String responsavel;

    public HistoricoAcaoManifestacaoResult(String acao, DateTime dataAcao, String infoAdicionais, String responsavel) {
        this.acao = acao;
        this.dataAcao = dataAcao;
        this.infoAdicionais = infoAdicionais;
        this.responsavel = responsavel;
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

    @Override
    public String toString() {
        return "HistoricoAcaoManifestacaoResult{" +
                "acao='" + acao + '\'' +
                ", dataAcao=" + dataAcao +
                ", infoAdicionais='" + infoAdicionais + '\'' +
                ", responsavel='" + responsavel + '\'' +
                '}';
    }
}
