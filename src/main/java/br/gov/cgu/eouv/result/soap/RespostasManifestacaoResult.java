package br.gov.cgu.eouv.result.soap;

import org.joda.time.DateTime;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
public class RespostasManifestacaoResult {
    private String tipo;
    private String texto;
    private String respondente;
    private String dataPublicacao;
    private String dataCompromisso;
    private String decisao;
    private DateTime dataResposta;

    public RespostasManifestacaoResult(String tipo, String texto, String respondente, String dataPublicacao, String dataCompromisso, String decisao, DateTime dataResposta) {
        this.tipo = tipo;
        this.texto = texto;
        this.respondente = respondente;
        this.dataPublicacao = dataPublicacao;
        this.dataCompromisso = dataCompromisso;
        this.decisao = decisao;
        this.dataResposta = dataResposta;
    }

    public RespostasManifestacaoResult() {

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

    public DateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(DateTime dataResposta) {
        this.dataResposta = dataResposta;
    }
}
