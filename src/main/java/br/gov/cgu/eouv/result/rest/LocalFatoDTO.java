package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class LocalFatoDTO {
    @SerializedName("Municipio")
    private MunicipioDTO Municipio;
    @SerializedName("DescricaoLocalFato")
    private String DescricaoLocalFato;
    @SerializedName("GeoReferencia")
    private LocalizacaoDTO GeoReferencia;

    public MunicipioDTO getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(MunicipioDTO municipio) {
        Municipio = municipio;
    }

    public String getDescricaoLocalFato() {
        return DescricaoLocalFato;
    }

    public void setDescricaoLocalFato(String descricaoLocalFato) {
        DescricaoLocalFato = descricaoLocalFato;
    }

    public LocalizacaoDTO getGeoReferencia() {
        return GeoReferencia;
    }

    public void setGeoReferencia(LocalizacaoDTO geoReferencia) {
        GeoReferencia = geoReferencia;
    }
}
