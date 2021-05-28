package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class InformacoesAdicionaisDTO {
    @SerializedName("Apta")
    private String Apta;
    @SerializedName("EnvolveEmpresa")
    private String EnvolveEmpresa;
    @SerializedName("EnvolveServidorPublico")
    private String EnvolveServidorPublico;
    @SerializedName("EnvolveCargoComissionadoDAS4OuSuperior")
    private String EnvolveCargoComissionadoDAS4OuSuperior;

    public String getApta() {
        return Apta;
    }

    public void setApta(String apta) {
        Apta = apta;
    }

    public String getEnvolveEmpresa() {
        return EnvolveEmpresa;
    }

    public void setEnvolveEmpresa(String envolveEmpresa) {
        EnvolveEmpresa = envolveEmpresa;
    }

    public String getEnvolveServidorPublico() {
        return EnvolveServidorPublico;
    }

    public void setEnvolveServidorPublico(String envolveServidorPublico) {
        EnvolveServidorPublico = envolveServidorPublico;
    }

    public String getEnvolveCargoComissionadoDAS4OuSuperior() {
        return EnvolveCargoComissionadoDAS4OuSuperior;
    }

    public void setEnvolveCargoComissionadoDAS4OuSuperior(String envolveCargoComissionadoDAS4OuSuperior) {
        EnvolveCargoComissionadoDAS4OuSuperior = envolveCargoComissionadoDAS4OuSuperior;
    }
}
