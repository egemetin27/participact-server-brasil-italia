package br.gov.cgu.eouv.result.soap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 07/05/2019
 **/
public class ListaManifestacaoOuvidoriaResult {
    private int codigoErro;
    private String descricaoErro;
    private List<ManifestacaoOuvidoriaResult> manifestacoesOuvidoria = new ArrayList<>();

    public ListaManifestacaoOuvidoriaResult() {
    }

    public int getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(int codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public List<ManifestacaoOuvidoriaResult> getManifestacoesOuvidoria() {
        return manifestacoesOuvidoria;
    }

    public void setManifestacoesOuvidoria(List<ManifestacaoOuvidoriaResult> manifestacoesOuvidoria) {
        this.manifestacoesOuvidoria = manifestacoesOuvidoria;
    }

    public void addManifestacoesOuvidoria(ManifestacaoOuvidoriaResult manifestacaoOuvidoriaResult) {
        this.manifestacoesOuvidoria.add(manifestacaoOuvidoriaResult);
    }

    @Override
    public String toString() {
        return "ListaManifestacaoOuvidoriaResult{" +
                "codigoErro=" + codigoErro +
                ", descricaoErro='" + descricaoErro + '\'' +
                ", manifestacoesOuvidoria=" + manifestacoesOuvidoria.toString() +
                '}';
    }
}
