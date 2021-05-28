package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class UfDTO {
    @SerializedName("SigUf")
    private String sigUf;
    @SerializedName("DescMunicipio")
    private String descUf;

    public String getSigUf() {
        return sigUf;
    }

    public void setSigUf(String sigUf) {
        this.sigUf = sigUf;
    }

    public String getDescUf() {
        return descUf;
    }

    public void setDescUf(String descUf) {
        this.descUf = descUf;
    }
}
