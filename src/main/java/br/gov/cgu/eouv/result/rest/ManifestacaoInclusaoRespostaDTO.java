package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ManifestacaoInclusaoRespostaDTO {
    @SerializedName("IdManifestacao")
    private Integer IdManifestacao;
    @SerializedName("NumeroProtocolo")
    private String NumeroProtocolo;
    @SerializedName("CodigoAcesso")
    private String CodigoAcesso;
    @SerializedName("Links")
    private LinkDTO[] links;

    public Integer getIdManifestacao() {
        return IdManifestacao;
    }

    public void setIdManifestacao(Integer idManifestacao) {
        IdManifestacao = idManifestacao;
    }

    public String getNumeroProtocolo() {
        return NumeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        NumeroProtocolo = numeroProtocolo;
    }

    public String getCodigoAcesso() {
        return CodigoAcesso;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        CodigoAcesso = codigoAcesso;
    }

    public LinkDTO[] getLinks() {
        return links;
    }

    public void setLinks(LinkDTO[] links) {
        this.links = links;
    }
}
