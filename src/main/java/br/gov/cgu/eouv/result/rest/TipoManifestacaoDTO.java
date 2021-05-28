package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class TipoManifestacaoDTO {
    @SerializedName("IdTipoManifestacao")
    private Integer IdTipoManifestacao;
    @SerializedName("DescTipoManifestacao")
    private String DescTipoManifestacao;

    public Integer getIdTipoManifestacao() {
        return IdTipoManifestacao;
    }

    public void setIdTipoManifestacao(Integer idTipoManifestacao) {
        IdTipoManifestacao = idTipoManifestacao;
    }

    public String getDescTipoManifestacao() {
        return DescTipoManifestacao;
    }

    public void setDescTipoManifestacao(String descTipoManifestacao) {
        DescTipoManifestacao = descTipoManifestacao;
    }
}
