package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class TipoFormularioDTO {
    @SerializedName("IdTipoFormulario")
    private Integer IdTipoFormulario;
    @SerializedName("DescTipoFormulario")
    private String DescTipoFormulario;

    public Integer getIdTipoFormulario() {
        return IdTipoFormulario;
    }

    public void setIdTipoFormulario(Integer idTipoFormulario) {
        IdTipoFormulario = idTipoFormulario;
    }

    public String getDescTipoFormulario() {
        return DescTipoFormulario;
    }

    public void setDescTipoFormulario(String descTipoFormulario) {
        DescTipoFormulario = descTipoFormulario;
    }
}
