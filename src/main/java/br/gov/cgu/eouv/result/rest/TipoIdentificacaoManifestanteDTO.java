package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class TipoIdentificacaoManifestanteDTO {
    @SerializedName("IdTTipoIdentificacaoManifestanteDTO")
    private Integer IdTTipoIdentificacaoManifestanteDTO;
    @SerializedName("DescTipoIdentificacaoManifestanteDTO")
    private String DescTipoIdentificacaoManifestanteDTO;

    public Integer getIdTTipoIdentificacaoManifestanteDTO() {
        return IdTTipoIdentificacaoManifestanteDTO;
    }

    public void setIdTTipoIdentificacaoManifestanteDTO(Integer idTTipoIdentificacaoManifestanteDTO) {
        IdTTipoIdentificacaoManifestanteDTO = idTTipoIdentificacaoManifestanteDTO;
    }

    public String getDescTipoIdentificacaoManifestanteDTO() {
        return DescTipoIdentificacaoManifestanteDTO;
    }

    public void setDescTipoIdentificacaoManifestanteDTO(String descTipoIdentificacaoManifestanteDTO) {
        DescTipoIdentificacaoManifestanteDTO = descTipoIdentificacaoManifestanteDTO;
    }
}
