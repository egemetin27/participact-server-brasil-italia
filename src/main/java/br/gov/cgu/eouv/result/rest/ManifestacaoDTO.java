package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class ManifestacaoDTO {
    @SerializedName("OrgaoInteresse")
    private OrgaoDTO OrgaoInteresse;
    @SerializedName("SubAssunto")
    private SubAssuntoDTO SubAssunto;
    @SerializedName("RegistradoPor")
    private String RegistradoPor;
    @SerializedName("CanalEntrada")
    private CanalEntradaDTO CanalEntrada;
    @SerializedName("InformacoesAdicionais")
    private InformacoesAdicionaisDTO InformacoesAdicionais;
    //@SerializedName("dadosExtraJson")
    // private String dadosExtraJson;
    @SerializedName("ObservacoesOuvidoria")
    private String ObservacoesOuvidoria;
    @SerializedName("Teor")
    private TeorManifestacaoDTO Teor;
    @SerializedName("Historico")
    private HistoricoManifestacaoDTO[] Historico;
    @SerializedName("TipoIdentificacaoManifestante")
    private TipoIdentificacaoManifestanteDTO TipoIdentificacaoManifestante;
    @SerializedName("Manifestante")
    private PessoaDTO Manifestante;
    @SerializedName("IndAcessoRestrito")
    private Boolean IndAcessoRestrito;
    @SerializedName("Links")
    private LinkDTO[] Links;
    @SerializedName("IdManifestacao")
    private Integer IdManifestacao;
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

    public OrgaoDTO getOrgaoInteresse() {
        return OrgaoInteresse;
    }

    public void setOrgaoInteresse(OrgaoDTO orgaoInteresse) {
        OrgaoInteresse = orgaoInteresse;
    }

    public SubAssuntoDTO getSubAssunto() {
        return SubAssunto;
    }

    public void setSubAssunto(SubAssuntoDTO subAssunto) {
        SubAssunto = subAssunto;
    }

    public String getRegistradoPor() {
        return RegistradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        RegistradoPor = registradoPor;
    }

    public CanalEntradaDTO getCanalEntrada() {
        return CanalEntrada;
    }

    public void setCanalEntrada(CanalEntradaDTO canalEntrada) {
        CanalEntrada = canalEntrada;
    }

    public InformacoesAdicionaisDTO getInformacoesAdicionais() {
        return InformacoesAdicionais;
    }

    public void setInformacoesAdicionais(InformacoesAdicionaisDTO informacoesAdicionais) {
        InformacoesAdicionais = informacoesAdicionais;
    }

    public String getObservacoesOuvidoria() {
        return ObservacoesOuvidoria;
    }

    public void setObservacoesOuvidoria(String observacoesOuvidoria) {
        ObservacoesOuvidoria = observacoesOuvidoria;
    }

    public TeorManifestacaoDTO getTeor() {
        return Teor;
    }

    public void setTeor(TeorManifestacaoDTO teor) {
        Teor = teor;
    }

    public HistoricoManifestacaoDTO[] getHistorico() {
        return Historico;
    }

    public void setHistorico(HistoricoManifestacaoDTO[] historico) {
        Historico = historico;
    }

    public TipoIdentificacaoManifestanteDTO getTipoIdentificacaoManifestante() {
        return TipoIdentificacaoManifestante;
    }

    public void setTipoIdentificacaoManifestante(TipoIdentificacaoManifestanteDTO tipoIdentificacaoManifestante) {
        TipoIdentificacaoManifestante = tipoIdentificacaoManifestante;
    }

    public PessoaDTO getManifestante() {
        return Manifestante;
    }

    public void setManifestante(PessoaDTO manifestante) {
        Manifestante = manifestante;
    }

    public Boolean getIndAcessoRestrito() {
        return IndAcessoRestrito;
    }

    public void setIndAcessoRestrito(Boolean indAcessoRestrito) {
        IndAcessoRestrito = indAcessoRestrito;
    }

    public LinkDTO[] getLinks() {
        return Links;
    }

    public void setLinks(LinkDTO[] links) {
        Links = links;
    }

    public Integer getIdManifestacao() {
        return IdManifestacao;
    }

    public void setIdManifestacao(Integer idManifestacao) {
        IdManifestacao = idManifestacao;
    }

    public ArrayList<String> getNumerosProtocolo() {
        return NumerosProtocolo;
    }

    public void setNumerosProtocolo(ArrayList<String> numerosProtocolo) {
        NumerosProtocolo = numerosProtocolo;
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
}
