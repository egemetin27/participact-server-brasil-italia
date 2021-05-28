package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class PessoaDTO {
    //Required
    @SerializedName("Nome")
    private String Nome;
    @SerializedName("Email")
    private String Email;
    //Optional
    /**
     * Gênero do manifestante. M para masculino e F para feminino.
     */
    @SerializedName("genero")
    private String genero;
    @SerializedName("IdTipoDocumentoIdentificacao")
    private Integer IdTipoDocumentoIdentificacao;
    @SerializedName("NumeroDocumentoIdentificacao")
    private String NumeroDocumentoIdentificacao;
    @SerializedName("IdFaixaEtaria")
    private Integer IdFaixaEtaria;
    @SerializedName("IdRacaCor")
    private Integer IdRacaCor;
    /**
     * Apenas números. 8 dígitos.
     */
    @SerializedName("CEP")
    private String CEP;
    @SerializedName("IdMunicipio")
    private Integer IdMunicipio;
    @SerializedName("Logradouro")
    private String Logradouro;
    @SerializedName("Numero")
    private String Numero;
    @SerializedName("Complemento")
    private String Complemento;
    @SerializedName("Bairro")
    private String Bairro;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getIdTipoDocumentoIdentificacao() {
        return IdTipoDocumentoIdentificacao;
    }

    public void setIdTipoDocumentoIdentificacao(Integer idTipoDocumentoIdentificacao) {
        IdTipoDocumentoIdentificacao = idTipoDocumentoIdentificacao;
    }

    public String getNumeroDocumentoIdentificacao() {
        return NumeroDocumentoIdentificacao;
    }

    public void setNumeroDocumentoIdentificacao(String numeroDocumentoIdentificacao) {
        NumeroDocumentoIdentificacao = numeroDocumentoIdentificacao;
    }

    public Integer getIdFaixaEtaria() {
        return IdFaixaEtaria;
    }

    public void setIdFaixaEtaria(Integer idFaixaEtaria) {
        IdFaixaEtaria = idFaixaEtaria;
    }

    public Integer getIdRacaCor() {
        return IdRacaCor;
    }

    public void setIdRacaCor(Integer idRacaCor) {
        IdRacaCor = idRacaCor;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public Integer getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }
}
