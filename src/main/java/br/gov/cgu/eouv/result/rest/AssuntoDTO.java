package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class AssuntoDTO {
    @SerializedName("IdAssunto")
    private Integer IdAssunto;
    @SerializedName("DescAssunto")
    private String DescAssunto;

    public Integer getIdAssunto() {
        return IdAssunto;
    }

    public void setIdAssunto(Integer idAssunto) {
        IdAssunto = idAssunto;
    }

    public String getDescAssunto() {
        return DescAssunto;
    }

    public void setDescAssunto(String descAssunto) {
        DescAssunto = descAssunto;
    }
}
