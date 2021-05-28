package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class AvaliacaoManifestacaoDTO {
    @SerializedName("codigoDecisao")
    private Integer codigoDecisao;

    @SerializedName("descricaoDecisao")
    private String descricaoDecisao;

    public Integer getCodigoDecisao() {
        return codigoDecisao;
    }

    public void setCodigoDecisao(Integer codigoDecisao) {
        this.codigoDecisao = codigoDecisao;
    }

    public String getDescricaoDecisao() {
        return descricaoDecisao;
    }

    public void setDescricaoDecisao(String descricaoDecisao) {
        this.descricaoDecisao = descricaoDecisao;
    }
}
