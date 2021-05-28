package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class OuvidoriaDTO {
    @SerializedName("IdOuvidoria")
    private Long idOuvidoria;
    @SerializedName("IdOrgaoSiorg")
    private Long idOrgaoSiorg;
    @SerializedName("NomeOuvidoria")
    private String nomeOuvidoria;

    @SerializedName("DataAdesaoEOuvPadrao")
    private String dataAdesaoEOuvPadrao;
    @SerializedName("DataAdesaoEOuvSimplifique")
    private String dataAdesaoEOuvSimplifique;
    @SerializedName("DataInativacao")
    private String dataInativacao;
    @SerializedName("IndEnviaDenunciasCguPad")
    private Boolean indEnviaDenunciasCguPad;
    @SerializedName("IndEnviaDenunciasCguPj")
    private Boolean indEnviaDenunciasCguPj;

    @SerializedName("Esfera")
    private EsferaDTO esfera;
    @SerializedName("Municipio")
    private MunicipioDTO municipio;
    @SerializedName("SubAssuntos")
    private SubAssuntoDTO[] subAssuntos;

    public Long getIdOuvidoria() {
        return idOuvidoria;
    }

    public void setIdOuvidoria(Long idOuvidoria) {
        this.idOuvidoria = idOuvidoria;
    }

    public Long getIdOrgaoSiorg() {
        return idOrgaoSiorg;
    }

    public void setIdOrgaoSiorg(Long idOrgaoSiorg) {
        this.idOrgaoSiorg = idOrgaoSiorg;
    }

    public String getNomeOuvidoria() {
        return nomeOuvidoria;
    }

    public void setNomeOuvidoria(String nomeOuvidoria) {
        this.nomeOuvidoria = nomeOuvidoria;
    }

    public String getDataAdesaoEOuvPadrao() {
        return dataAdesaoEOuvPadrao;
    }

    public void setDataAdesaoEOuvPadrao(String dataAdesaoEOuvPadrao) {
        this.dataAdesaoEOuvPadrao = dataAdesaoEOuvPadrao;
    }

    public String getDataAdesaoEOuvSimplifique() {
        return dataAdesaoEOuvSimplifique;
    }

    public void setDataAdesaoEOuvSimplifique(String dataAdesaoEOuvSimplifique) {
        this.dataAdesaoEOuvSimplifique = dataAdesaoEOuvSimplifique;
    }

    public String getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(String dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public Boolean getIndEnviaDenunciasCguPad() {
        return indEnviaDenunciasCguPad;
    }

    public void setIndEnviaDenunciasCguPad(Boolean indEnviaDenunciasCguPad) {
        this.indEnviaDenunciasCguPad = indEnviaDenunciasCguPad;
    }

    public Boolean getIndEnviaDenunciasCguPj() {
        return indEnviaDenunciasCguPj;
    }

    public void setIndEnviaDenunciasCguPj(Boolean indEnviaDenunciasCguPj) {
        this.indEnviaDenunciasCguPj = indEnviaDenunciasCguPj;
    }

    public EsferaDTO getEsfera() {
        return esfera;
    }

    public void setEsfera(EsferaDTO esfera) {
        this.esfera = esfera;
    }

    public MunicipioDTO getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioDTO municipio) {
        this.municipio = municipio;
    }

    public SubAssuntoDTO[] getSubAssuntos() {
        return subAssuntos;
    }

    public void setSubAssuntos(SubAssuntoDTO[] subAssuntos) {
        this.subAssuntos = subAssuntos;
    }
}
