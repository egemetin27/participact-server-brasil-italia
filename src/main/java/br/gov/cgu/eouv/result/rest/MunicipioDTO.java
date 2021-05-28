package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class MunicipioDTO {
    @SerializedName("IdMunicipio")
    private Long IdMunicipio;
    @SerializedName("DescMunicipio")
    private String DescMunicipio;
    @SerializedName("Uf")
    private UfDTO Uf;

    public Long getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public String getDescMunicipio() {
        return DescMunicipio;
    }

    public void setDescMunicipio(String descMunicipio) {
        DescMunicipio = descMunicipio;
    }

    public UfDTO getUf() {
        return Uf;
    }

    public void setUf(UfDTO uf) {
        Uf = uf;
    }
}

