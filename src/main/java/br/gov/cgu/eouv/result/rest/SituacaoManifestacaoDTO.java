package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class SituacaoManifestacaoDTO {
    /**
     * Representa a situação da manifestação. Pode assumir os seguintes valores: 1 - Cadastrada; 2 - Complementada pelo Cidadão; 3 - Prorrogada; 4 - Encaminhada para outra Ouvidoria; 5 - Respondida - Aguardando Complementação; 6 - Respondida - Concluída; 7 - Encaminhada para Órgão Externo - Encerrada; 8 - Arquivada
     */
    @SerializedName("IdSituacaoManifestacao")
    private Integer IdSituacaoManifestacao;
    @SerializedName("DescSituacaoManifestacao")
    private String DescSituacaoManifestacao;

    public Integer getIdSituacaoManifestacao() {
        return IdSituacaoManifestacao;
    }

    public void setIdSituacaoManifestacao(Integer idSituacaoManifestacao) {
        IdSituacaoManifestacao = idSituacaoManifestacao;
    }

    public String getDescSituacaoManifestacao() {
        return DescSituacaoManifestacao;
    }

    public void setDescSituacaoManifestacao(String descSituacaoManifestacao) {
        DescSituacaoManifestacao = descSituacaoManifestacao;
    }
}
