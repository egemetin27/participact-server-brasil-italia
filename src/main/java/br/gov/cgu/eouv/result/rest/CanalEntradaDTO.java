package br.gov.cgu.eouv.result.rest;

import com.google.gson.annotations.SerializedName;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public class CanalEntradaDTO {
    @SerializedName("IdCanalEntrada")
    private Integer IdCanalEntrada;
    @SerializedName("DescCanalEntrada")
    private String DescCanalEntrada;

    public Integer getIdCanalEntrada() {
        return IdCanalEntrada;
    }

    public void setIdCanalEntrada(Integer idCanalEntrada) {
        IdCanalEntrada = idCanalEntrada;
    }

    public String getDescCanalEntrada() {
        return DescCanalEntrada;
    }

    public void setDescCanalEntrada(String descCanalEntrada) {
        DescCanalEntrada = descCanalEntrada;
    }
}
