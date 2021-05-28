package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class TeorManifestacaoDTO {
    @SerializedName("DescricaoAtosOuFatos")
    private String DescricaoAtosOuFatos;
    @SerializedName("PropostaMelhoria")
    private String PropostaMelhoria;
    //    @SerializedName("Anexos")
//    private String Anexos;
    @SerializedName("LocalFato")
    private LocalFatoDTO LocalFato;

    public String getDescricaoAtosOuFatos() {
        return DescricaoAtosOuFatos;
    }

    public void setDescricaoAtosOuFatos(String descricaoAtosOuFatos) {
        DescricaoAtosOuFatos = descricaoAtosOuFatos;
    }

    public String getPropostaMelhoria() {
        return PropostaMelhoria;
    }

    public void setPropostaMelhoria(String propostaMelhoria) {
        PropostaMelhoria = propostaMelhoria;
    }

    public LocalFatoDTO getLocalFato() {
        return LocalFato;
    }

    public void setLocalFato(LocalFatoDTO localFato) {
        LocalFato = localFato;
    }
}
