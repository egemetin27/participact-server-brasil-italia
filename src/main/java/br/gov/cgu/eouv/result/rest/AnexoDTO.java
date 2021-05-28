package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class AnexoDTO {
    @SerializedName("NomeArquivo")
    private String nomeArquivo;
    @SerializedName("ConteudoZipadoEBase64")
    private String conteudoZipadoEBase64;
    @SerializedName("TamanhoArquivo")
    private Integer tamanhoArquivo;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getConteudoZipadoEBase64() {
        return conteudoZipadoEBase64;
    }

    public void setConteudoZipadoEBase64(String conteudoZipadoEBase64) {
        this.conteudoZipadoEBase64 = conteudoZipadoEBase64;
    }

    public Integer getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(Integer tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }
}
