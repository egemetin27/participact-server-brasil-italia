package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ComplementoManifestacaoDTO {
    @SerializedName("idComplemento")
    private Integer idComplemento;

    @SerializedName("texto")
    private String texto;

    public Integer getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(Integer idComplemento) {
        this.idComplemento = idComplemento;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
