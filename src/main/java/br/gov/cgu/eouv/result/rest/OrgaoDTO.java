package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class OrgaoDTO {
    @SerializedName("IdOrgaoSiorg")
    private Long IdOrgaoSiorg;
    @SerializedName("NomeOrgao")
    private String NomeOrgao;

    public Long getIdOrgaoSiorg() {
        return IdOrgaoSiorg;
    }

    public void setIdOrgaoSiorg(Long idOrgaoSiorg) {
        IdOrgaoSiorg = idOrgaoSiorg;
    }

    public String getNomeOrgao() {
        return NomeOrgao;
    }

    public void setNomeOrgao(String nomeOrgao) {
        NomeOrgao = nomeOrgao;
    }

    @Override
    public String toString() {
        return "OrgaoDTORestResult{" +
                "IdOrgaoSiorg=" + IdOrgaoSiorg +
                ", NomeOrgao='" + NomeOrgao + '\'' +
                '}';
    }
}