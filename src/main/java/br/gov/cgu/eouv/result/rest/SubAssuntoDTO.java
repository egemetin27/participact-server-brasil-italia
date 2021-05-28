package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class SubAssuntoDTO {
    @SerializedName("IdSubAssunto")
    private Long idSubAssunto;
    @SerializedName("DescSubAssunto")
    private String descSubAssunto;

    public Long getIdSubAssunto() {
        return idSubAssunto;
    }

    public void setIdSubAssunto(Long idSubAssunto) {
        this.idSubAssunto = idSubAssunto;
    }

    public String getDescSubAssunto() {
        return descSubAssunto;
    }

    public void setDescSubAssunto(String descSubAssunto) {
        this.descSubAssunto = descSubAssunto;
    }
}
