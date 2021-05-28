package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class RespostaManifestacaoDTO {
    @SerializedName("IdRespostaManifestacao")
    private Integer IdRespostaManifestacao;

    @SerializedName("TipoRespostaManifestacao")
    private TipoRespostaManifestacaoDTO TipoRespostaManifestacao;

    @SerializedName("TxtResposta")
    private String TxtResposta;

    @SerializedName("RespostaPublicavel")
    private Boolean RespostaPublicavel;

    @SerializedName("Decisao")
    private AvaliacaoManifestacaoDTO Decisao;

    @SerializedName("DataCompromisso")
    private String DataCompromisso;

    public Integer getIdRespostaManifestacao() {
        return IdRespostaManifestacao;
    }

    public void setIdRespostaManifestacao(Integer idRespostaManifestacao) {
        IdRespostaManifestacao = idRespostaManifestacao;
    }

    public TipoRespostaManifestacaoDTO getTipoRespostaManifestacao() {
        return TipoRespostaManifestacao;
    }

    public void setTipoRespostaManifestacao(TipoRespostaManifestacaoDTO tipoRespostaManifestacao) {
        TipoRespostaManifestacao = tipoRespostaManifestacao;
    }

    public String getTxtResposta() {
        return TxtResposta;
    }

    public void setTxtResposta(String txtResposta) {
        TxtResposta = txtResposta;
    }

    public Boolean getRespostaPublicavel() {
        return RespostaPublicavel;
    }

    public void setRespostaPublicavel(Boolean respostaPublicavel) {
        RespostaPublicavel = respostaPublicavel;
    }

    public AvaliacaoManifestacaoDTO getDecisao() {
        return Decisao;
    }

    public void setDecisao(AvaliacaoManifestacaoDTO decisao) {
        Decisao = decisao;
    }

    public String getDataCompromisso() {
        return DataCompromisso;
    }

    public void setDataCompromisso(String dataCompromisso) {
        DataCompromisso = dataCompromisso;
    }
}
