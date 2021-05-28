package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class HistoricoAcaoDTO {
    @SerializedName("DescTipoAcaoManifestacao")
    private String DescTipoAcaoManifestacao;

    @SerializedName("DataHoraAcao")
    private String DataHoraAcao;

    @SerializedName("Responsavel")
    private String Responsavel;

    @SerializedName("InformacoesAdicionais")
    private String InformacoesAdicionais;

    public String getDescTipoAcaoManifestacao() {
        return DescTipoAcaoManifestacao;
    }

    public void setDescTipoAcaoManifestacao(String descTipoAcaoManifestacao) {
        DescTipoAcaoManifestacao = descTipoAcaoManifestacao;
    }

    public String getDataHoraAcao() {
        return DataHoraAcao;
    }

    public void setDataHoraAcao(String dataHoraAcao) {
        DataHoraAcao = dataHoraAcao;
    }

    public String getResponsavel() {
        return Responsavel;
    }

    public void setResponsavel(String responsavel) {
        Responsavel = responsavel;
    }

    public String getInformacoesAdicionais() {
        return InformacoesAdicionais;
    }

    public void setInformacoesAdicionais(String informacoesAdicionais) {
        InformacoesAdicionais = informacoesAdicionais;
    }
}
