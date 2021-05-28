package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ServicoDTO {
    @SerializedName("IdServicoMPOG")
    private Integer IdServicoMPOG;
    @SerializedName("Nome")
    private String Nome;
    @SerializedName("Links")
    private LinkDTO[] Links;

    public Integer getIdServicoMPOG() {
        return IdServicoMPOG;
    }

    public void setIdServicoMPOG(Integer idServicoMPOG) {
        IdServicoMPOG = idServicoMPOG;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public LinkDTO[] getLinks() {
        return Links;
    }

    public void setLinks(LinkDTO[] links) {
        Links = links;
    }
}
