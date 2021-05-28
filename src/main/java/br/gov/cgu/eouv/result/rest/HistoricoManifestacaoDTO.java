package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class HistoricoManifestacaoDTO {
    @SerializedName("HistoricoAcao")
    private HistoricoAcaoDTO HistoricoAcao;

    @SerializedName("Resposta")
    private RespostaManifestacaoDTO Resposta;

    @SerializedName("Complemento")
    private ComplementoManifestacaoDTO Complemento;

    public HistoricoAcaoDTO getHistoricoAcao() {
        return HistoricoAcao;
    }

    public void setHistoricoAcao(HistoricoAcaoDTO historicoAcao) {
        HistoricoAcao = historicoAcao;
    }

    public RespostaManifestacaoDTO getResposta() {
        return Resposta;
    }

    public void setResposta(RespostaManifestacaoDTO resposta) {
        Resposta = resposta;
    }

    public ComplementoManifestacaoDTO getComplemento() {
        return Complemento;
    }

    public void setComplemento(ComplementoManifestacaoDTO complemento) {
        Complemento = complemento;
    }
}
