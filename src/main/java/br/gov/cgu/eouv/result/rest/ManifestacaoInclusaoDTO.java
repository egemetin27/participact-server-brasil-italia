package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ManifestacaoInclusaoDTO {
    // Required
    @SerializedName("IdTipoFormulario")
    private Integer idTipoFormulario;
    @SerializedName("IdTipoManifestacao")
    private Integer idTipoManifestacao;
    @SerializedName("TextoManifestacao")
    private String textoManifestacao;
    @SerializedName("IdTipoIdentificacaoManifestante")
    private Integer idTipoIdentificacaoManifestante;
    // Recomend
    @SerializedName("IdOuvidoriaDestino")
    private Integer idOuvidoriaDestino;
    @SerializedName("IdOrgaoInteresse")
    private Integer idOrgaoInteresse;
    @SerializedName("IdMunicipio")
    private Integer idMunicipio;
    @SerializedName("GeoReferencia")
    private LocalizacaoDTO geoReferencia;
    @SerializedName("Manifestante")
    private PessoaDTO pessoa;
    @SerializedName("DadosExtraJson")
    private String DadosExtraJson;
    @SerializedName("IndEnviarEmailCidadao")
    private Boolean IndEnviarEmailCidadao;
    @SerializedName("IndEnviarEmailOuvidoria")
    private Boolean IndEnviarEmailOuvidoria;
    // Optional
    @SerializedName("NumProtocolo")
    private String numProtocolo;
    @SerializedName("IdOrgaoDestinatario")
    private Integer idOrgaoDestinatario;
    @SerializedName("CodServicoMPOG")
    private Integer codServicoMPOG;
    @SerializedName("TxtOutroServico")
    private Integer txtOutroServico;
    @SerializedName("IdAssunto")
    private Integer idAssunto;
    @SerializedName("PropostaMelhoria")
    private Integer propostaMelhoria;
    @SerializedName("Anexos")
    private AnexoDTO[] anexos;
    @SerializedName("Envolvidos")
    private EnvolvidosManifestacaoDTO[] envolvidos;
    @SerializedName("IdCanalEntrada")
    private Integer IdCanalEntrada;

    public Integer getIdTipoFormulario() {
        return idTipoFormulario;
    }

    public void setIdTipoFormulario(Integer idTipoFormulario) {
        this.idTipoFormulario = idTipoFormulario;
    }

    public Integer getIdTipoManifestacao() {
        return idTipoManifestacao;
    }

    public void setIdTipoManifestacao(Integer idTipoManifestacao) {
        this.idTipoManifestacao = idTipoManifestacao;
    }

    public String getTextoManifestacao() {
        return textoManifestacao;
    }

    public void setTextoManifestacao(String textoManifestacao) {
        this.textoManifestacao = textoManifestacao;
    }

    public Integer getIdTipoIdentificacaoManifestante() {
        return idTipoIdentificacaoManifestante;
    }

    public void setIdTipoIdentificacaoManifestante(Integer idTipoIdentificacaoManifestante) {
        this.idTipoIdentificacaoManifestante = idTipoIdentificacaoManifestante;
    }

    public Integer getIdOuvidoriaDestino() {
        return idOuvidoriaDestino;
    }

    public void setIdOuvidoriaDestino(Integer idOuvidoriaDestino) {
        this.idOuvidoriaDestino = idOuvidoriaDestino;
    }

    public Integer getIdOrgaoInteresse() {
        return idOrgaoInteresse;
    }

    public void setIdOrgaoInteresse(Integer idOrgaoInteresse) {
        this.idOrgaoInteresse = idOrgaoInteresse;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public LocalizacaoDTO getGeoReferencia() {
        return geoReferencia;
    }

    public void setGeoReferencia(LocalizacaoDTO geoReferencia) {
        this.geoReferencia = geoReferencia;
    }

    public String getDadosExtraJson() {
        return DadosExtraJson;
    }

    public void setDadosExtraJson(String dadosExtraJson) {
        DadosExtraJson = dadosExtraJson;
    }

    public Boolean getIndEnviarEmailCidadao() {
        return IndEnviarEmailCidadao;
    }

    public void setIndEnviarEmailCidadao(Boolean indEnviarEmailCidadao) {
        IndEnviarEmailCidadao = indEnviarEmailCidadao;
    }

    public Boolean getIndEnviarEmailOuvidoria() {
        return IndEnviarEmailOuvidoria;
    }

    public void setIndEnviarEmailOuvidoria(Boolean indEnviarEmailOuvidoria) {
        IndEnviarEmailOuvidoria = indEnviarEmailOuvidoria;
    }

    public String getNumProtocolo() {
        return numProtocolo;
    }

    public void setNumProtocolo(String numProtocolo) {
        this.numProtocolo = numProtocolo;
    }

    public Integer getIdOrgaoDestinatario() {
        return idOrgaoDestinatario;
    }

    public void setIdOrgaoDestinatario(Integer idOrgaoDestinatario) {
        this.idOrgaoDestinatario = idOrgaoDestinatario;
    }

    public Integer getCodServicoMPOG() {
        return codServicoMPOG;
    }

    public void setCodServicoMPOG(Integer codServicoMPOG) {
        this.codServicoMPOG = codServicoMPOG;
    }

    public Integer getTxtOutroServico() {
        return txtOutroServico;
    }

    public void setTxtOutroServico(Integer txtOutroServico) {
        this.txtOutroServico = txtOutroServico;
    }

    public Integer getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(Integer idAssunto) {
        this.idAssunto = idAssunto;
    }

    public Integer getPropostaMelhoria() {
        return propostaMelhoria;
    }

    public void setPropostaMelhoria(Integer propostaMelhoria) {
        this.propostaMelhoria = propostaMelhoria;
    }

    public AnexoDTO[] getAnexos() {
        return anexos;
    }

    public void setAnexos(AnexoDTO[] anexos) {
        this.anexos = anexos;
    }

    public EnvolvidosManifestacaoDTO[] getEnvolvidos() {
        return envolvidos;
    }

    public void setEnvolvidos(EnvolvidosManifestacaoDTO[] envolvidos) {
        this.envolvidos = envolvidos;
    }

    public Integer getIdCanalEntrada() {
        return IdCanalEntrada;
    }

    public void setIdCanalEntrada(Integer idCanalEntrada) {
        IdCanalEntrada = idCanalEntrada;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }
}
