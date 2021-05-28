package br.gov.cgu.eouv.result.soap;

/**
 * @author Claudio
 * @project participact-server
 * @date 03/05/2019
 **/
public class RegistrarManifestacaoResult {
    private int codigoErro;
    private String descricaoErro;
    private String protocolo;
    private String url;
    private String codigoAcesso;
    private Integer IdManifestacao;
    private String urlSelf;
    private String urlOuv;

    public RegistrarManifestacaoResult() {
    }

    public int getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(int codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
    }

    public Integer getIdManifestacao() {
        return IdManifestacao;
    }

    public void setIdManifestacao(Integer idManifestacao) {
        IdManifestacao = idManifestacao;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlOuv() {
        return urlOuv;
    }

    public void setUrlOuv(String urlOuv) {
        this.urlOuv = urlOuv;
    }

    @Override
    public String toString() {
        return "RegistrarManifestacaoResult{" +
                "codigoErro=" + codigoErro +
                ", descricaoErro='" + descricaoErro + '\'' +
                ", protocolo='" + protocolo + '\'' +
                ", url='" + url + '\'' +
                ", codigoAcesso='" + codigoAcesso + '\'' +
                ", IdManifestacao='" + IdManifestacao + '\'' +
                ", urlSelf='" + urlSelf + '\'' +
                ", urlOuv='" + urlOuv + '\'' +
                '}';
    }
}
