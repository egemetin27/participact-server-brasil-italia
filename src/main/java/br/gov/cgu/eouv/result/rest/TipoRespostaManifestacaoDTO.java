package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class TipoRespostaManifestacaoDTO {
    @SerializedName("IdTipoRespostaManifestacao")
    private Integer IdTipoRespostaManifestacao;
    @SerializedName("DescTipoRespostaManifestacao")
    private String DescTipoRespostaManifestacao;

    public Integer getIdTipoRespostaManifestacao() {
        return IdTipoRespostaManifestacao;
    }

    public void setIdTipoRespostaManifestacao(Integer idTipoRespostaManifestacao) {
        IdTipoRespostaManifestacao = idTipoRespostaManifestacao;
    }

    public String getDescTipoRespostaManifestacao() {
        return DescTipoRespostaManifestacao;
    }

    public void setDescTipoRespostaManifestacao(String descTipoRespostaManifestacao) {
        DescTipoRespostaManifestacao = descTipoRespostaManifestacao;
    }
}
