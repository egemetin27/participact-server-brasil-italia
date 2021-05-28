package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class EsferaDTO {
    @SerializedName("IdEsfera")
    private Long IdEsfera;
    @SerializedName("DescEsfera")
    private String descEsfera;

    public Long getIdEsfera() {
        return IdEsfera;
    }

    public void setIdEsfera(Long idEsfera) {
        IdEsfera = idEsfera;
    }

    public String getDescEsfera() {
        return descEsfera;
    }

    public void setDescEsfera(String descEsfera) {
        this.descEsfera = descEsfera;
    }
}
