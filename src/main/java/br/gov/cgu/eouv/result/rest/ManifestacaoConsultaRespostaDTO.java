package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ManifestacaoConsultaRespostaDTO {
    @SerializedName("IdManifestacao")
    private Integer IdManifestacao;
    @SerializedName("Links")
    private LinkDTO[] Links;
    @SerializedName("NumerosProtocolo")
    private ArrayList<String> NumerosProtocolo;
    @SerializedName("OuvidoriaDestino")
    private OuvidoriaDTO OuvidoriaDestino;
    @SerializedName("Assunto")
    private AssuntoDTO Assunto;
    @SerializedName("Servico")
    private ServicoDTO Servico;
    @SerializedName("TipoFormulario")
    private TipoFormularioDTO TipoFormulario;
    @SerializedName("TipoManifestacao")
    private TipoManifestacaoDTO TipoManifestacao;
    @SerializedName("EmailManifestante")
    private String EmailManifestante;
    @SerializedName("DataCadastro")
    private String DataCadastro;
    @SerializedName("PrazoAtendimento")
    private String PrazoAtendimento;
    @SerializedName("Situacao")
    private SituacaoManifestacaoDTO Situacao;
    @SerializedName("ResponsavelAnalise")
    private String ResponsavelAnalise;

    public Integer getIdManifestacao() {
        return IdManifestacao;
    }

    public void setIdManifestacao(Integer idManifestacao) {
        IdManifestacao = idManifestacao;
    }

    public LinkDTO[] getLinks() {
        return Links;
    }

    public void setLinks(LinkDTO[] links) {
        Links = links;
    }


    public OuvidoriaDTO getOuvidoriaDestino() {
        return OuvidoriaDestino;
    }

    public void setOuvidoriaDestino(OuvidoriaDTO ouvidoriaDestino) {
        OuvidoriaDestino = ouvidoriaDestino;
    }

    public AssuntoDTO getAssunto() {
        return Assunto;
    }

    public void setAssunto(AssuntoDTO assunto) {
        Assunto = assunto;
    }

    public ServicoDTO getServico() {
        return Servico;
    }

    public void setServico(ServicoDTO servico) {
        Servico = servico;
    }

    public TipoFormularioDTO getTipoFormulario() {
        return TipoFormulario;
    }

    public void setTipoFormulario(TipoFormularioDTO tipoFormulario) {
        TipoFormulario = tipoFormulario;
    }

    public TipoManifestacaoDTO getTipoManifestacao() {
        return TipoManifestacao;
    }

    public void setTipoManifestacao(TipoManifestacaoDTO tipoManifestacao) {
        TipoManifestacao = tipoManifestacao;
    }

    public String getEmailManifestante() {
        return EmailManifestante;
    }

    public void setEmailManifestante(String emailManifestante) {
        EmailManifestante = emailManifestante;
    }

    public String getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public String getPrazoAtendimento() {
        return PrazoAtendimento;
    }

    public void setPrazoAtendimento(String prazoAtendimento) {
        PrazoAtendimento = prazoAtendimento;
    }

    public SituacaoManifestacaoDTO getSituacao() {
        return Situacao;
    }

    public void setSituacao(SituacaoManifestacaoDTO situacao) {
        Situacao = situacao;
    }

    public String getResponsavelAnalise() {
        return ResponsavelAnalise;
    }

    public void setResponsavelAnalise(String responsavelAnalise) {
        ResponsavelAnalise = responsavelAnalise;
    }

    public ArrayList<String> getNumerosProtocolo() {
        return NumerosProtocolo;
    }

    public void setNumerosProtocolo(ArrayList<String> numerosProtocolo) {
        NumerosProtocolo = numerosProtocolo;
    }
}
