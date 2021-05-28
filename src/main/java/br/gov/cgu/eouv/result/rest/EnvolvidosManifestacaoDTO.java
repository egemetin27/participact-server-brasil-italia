package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class EnvolvidosManifestacaoDTO {
    @SerializedName("Nome")
    private String nome;
    @SerializedName("Orgao")
    private String orgao;
    @SerializedName("IdFuncaoEnvolvidoManifestacao")
    private Integer idFuncaoEnvolvidoManifestacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Integer getIdFuncaoEnvolvidoManifestacao() {
        return idFuncaoEnvolvidoManifestacao;
    }

    public void setIdFuncaoEnvolvidoManifestacao(Integer idFuncaoEnvolvidoManifestacao) {
        this.idFuncaoEnvolvidoManifestacao = idFuncaoEnvolvidoManifestacao;
    }
}
