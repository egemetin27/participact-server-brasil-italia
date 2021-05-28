package br.gov.cgu.eouv.ws.rest;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.result.rest.*;
import com.google.gson.Gson;
import it.unibo.paserver.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
public class MeOUVRestClient extends EOuvRestService {

    /**
     * Obtem um novo token
     *
     * @return
     */
    public OAuthTokenRest getAuthToken() {
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String body = String.format("client_id=%d&client_secret=%s&grant_type=%s&username=%s&password=%s", Config.CGU_CLIENT_ID, Config.CGU_CLIENT_SECRET, Config.CGU_GRANT_TYPE, Config.CGU_USERNAME, Config.CGU_PASSWORD);
        // Obtem um novo token
        String response = get(Config.CGU_REST_URL, Config.CGU_REST_GET_TOKEN, body);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            OAuthTokenRest oAuthTokenRest = gson.fromJson(response, OAuthTokenRest.class);
            return oAuthTokenRest;
        }
        return null;
    }

    /**
     * Obtem a lista de orgaoes do siorg
     *
     * @param accessToken, access token
     * @return
     */
    public List<OrgaoSiorgOuvidoria> getOrgaosSiorg(String accessToken) {
        // Default
        List<OrgaoSiorgOuvidoria> listOrgao = new ArrayList<>();
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", String.format("Bearer %s", accessToken));
        // Request
        String response = get(Config.CGU_REST_URL, Config.CGU_REST_GET_ORGAOS_SIORG, null);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            OrgaoDTO[] listOrgaosSiorgDTO = gson.fromJson(response, OrgaoDTO[].class);
            for (OrgaoDTO item : listOrgaosSiorgDTO) {
                listOrgao.add(new OrgaoSiorgOuvidoria(item.getIdOrgaoSiorg(), item.getNomeOrgao()));
            }
        }
        // Res
        return listOrgao;
    }

    /**
     * Obtem lista das ouvidorias ativas
     *
     * @param accessToken, access token
     * @return
     */
    public List<Ouvidoria> getOuvidoriasSomenteOuvidoriasAtivas(String accessToken) {
        // Default
        List<Ouvidoria> listOuvidoria = new ArrayList<>();
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", String.format("Bearer %s", accessToken));
        // Request
        String response = get(Config.CGU_REST_URL, Config.CGU_REST_GET_OUVIDORIAS, null);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            OuvidoriaDTO[] listOuvidoriaDTO = gson.fromJson(response, OuvidoriaDTO[].class);
            for (OuvidoriaDTO item : listOuvidoriaDTO) {
                Ouvidoria ouv = new Ouvidoria();
                ouv.setIdOuvidoria(item.getIdOuvidoria());
                ouv.setIdOrgaoOuvidoria(item.getIdOrgaoSiorg());
                ouv.setNomeOrgaoOuvidoria(item.getNomeOuvidoria());

                ouv.setDataAdesaoEOuvPadrao(item.getDataAdesaoEOuvPadrao());
                ouv.setDataAdesaoSimplifique(item.getDataAdesaoEOuvSimplifique());
                ouv.setDataInativacao(item.getDataInativacao());
                ouv.setIndEnviaDenunciasCGUPAD(item.getIndEnviaDenunciasCguPad());
                ouv.setIndEnviaDenunciasCGUPJ(item.getIndEnviaDenunciasCguPj());

                EsferaDTO esfera = item.getEsfera();
                if (esfera != null) {
                    ouv.setIdEsfera(esfera.getIdEsfera());
                    ouv.setDescEsfera(esfera.getDescEsfera());
                }

                MunicipioDTO municipio = item.getMunicipio();
                if (municipio != null) {
                    ouv.setIdMunicipio(municipio.getIdMunicipio());
                    ouv.setDescMunicipio(municipio.getDescMunicipio());
                    UfDTO uf = municipio.getUf();
                    if (uf != null) {
                        ouv.setSigUf(uf.getSigUf());
                        ouv.setDescUf(uf.getSigUf());
                    }
                }

                SubAssuntoDTO[] subAssuntos = item.getSubAssuntos();
                String assuntos = "";
                if (subAssuntos != null && subAssuntos.length > 0) {
                    for (SubAssuntoDTO sub : subAssuntos) {
                        assuntos += String.format("%d | %s \n", sub.getIdSubAssunto(), sub.getDescSubAssunto());
                    }
                }
                ouv.setSubAssuntosOuvidoria(assuntos);

                //Add
                listOuvidoria.add(ouv);
            }
        }
        // Res
        return listOuvidoria;
    }

    /**
     * Inclue uma manifestacao
     *
     * @param accessToken, access token
     * @param issue,       issue report
     */
    public ManifestacaoInclusaoRespostaDTO postInclusaoManifestacao(String accessToken, IssueReport issue) {
        // Default
        Gson gson = new Gson();
        ManifestacaoInclusaoDTO manifestacao = new ManifestacaoInclusaoDTO();
        List<AnexoDTO> anexos = new ArrayList<>();
        HashMap<String, String> appendix = new HashMap<>();
        ManifestacaoInclusaoRespostaDTO resposta = new ManifestacaoInclusaoRespostaDTO();

        // Manifestacao / Detalhes
        manifestacao.setIdTipoFormulario(1);
        manifestacao.setIndEnviarEmailCidadao(true);
        manifestacao.setIndEnviarEmailOuvidoria(false);
        // manifestacao.setIdTipoManifestacao(5); // Outros
        manifestacao.setIdTipoManifestacao(2); // Reclamação
        manifestacao.setIdTipoIdentificacaoManifestante(3); // Identificadas com Restricao

        String texto = issue.getComment();
        Long idOuvidoriaDestino = issue.getOmbudsmanId();
        Long siOrgId = issue.getSiOrgId();
        if (idOuvidoriaDestino != null && idOuvidoriaDestino > 0) {
            manifestacao.setIdOuvidoriaDestino(idOuvidoriaDestino.intValue());
            manifestacao.setIdMunicipio(issue.getIdCity().intValue());

        } else if (siOrgId != null && siOrgId > 0) {
            manifestacao.setIdOrgaoDestinatario(siOrgId.intValue());

        } /*else if (Validator.isValidLatitude(String.valueOf(issue.getLatitude())) && Validator.isValidLongitude(String.valueOf(issue.getLongitude()))) {
        }*/

        String address = issue.getFormattedAddress();
        if (!Validator.isEmptyString(address)) {
            appendix.put("LocalFato", address);
        }

        // Localizacao
        LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO();
        localizacaoDTO.setLatitude(issue.getLatitude());
        localizacaoDTO.setLongitude(issue.getLongitude());
        manifestacao.setGeoReferencia(localizacaoDTO);
        // Anexos
        List<StorageFile> fileList = issue.getFiles();
        if (fileList.size() > 0) {
            for (StorageFile f : fileList) {
                String className = f.getClass().getSimpleName();
                if (className.equals(StorageFileImage.class.getSimpleName())) {
                    /**
                     * 2019-07-11 : Claudionor
                     * Esta dando erro com o base64
                     */
//                    String conteudoZipadoEBase64 = Useful.encodeUrlFileGZipBase64(f.getAssetUrl());
//                    if (Validator.isValidBase64(conteudoZipadoEBase64)) {
//                        AnexoDTORestResult anexo = new AnexoDTORestResult();
//                        anexo.setNomeArquivo(f.getOriginalFilename());
//                        anexo.setConteudoZipadoEBase64(conteudoZipadoEBase64);
//                        anexo.setTamanhoArquivo(conteudoZipadoEBase64.length());
//                        //Add
//                        anexos.add(anexo);
//                    }
                    appendix.put("StorageFileImage", f.getAssetUrl());
                    texto += String.format(" ,  \n %s \n ", f.getAssetUrl());

                } else if ((className.equals(StorageFileAudio.class.getSimpleName()))) {
                    appendix.put("StorageFileAudio", f.getAssetUrl());
                    texto += String.format(" , \n %s \n ", f.getAssetUrl());
                } else if ((className.equals(StorageFileVideo.class.getSimpleName()))) {
                    appendix.put("StorageFileVideo", f.getAssetUrl());
                    texto += String.format(" , \n %s \n ", f.getAssetUrl());
                }
            }
        }

        // Parte Inicial do Pacote
        if (appendix.size() > 0) {
            manifestacao.setDadosExtraJson(gson.toJson(appendix));
        }
        if (anexos.size() > 0) {
            AnexoDTO[] arrAnexo = new AnexoDTO[anexos.size()];
            int index = 0;
            for (AnexoDTO item : anexos) {
                arrAnexo[index] = item;
                index++;
            }
            manifestacao.setAnexos(arrAnexo);
        }
        //Link do Participact
        texto += String.format(" Mais Detalhes: http://www.participact.com.br/participact-app/map.php?id=%d", (issue.isSecondary() ? issue.getParentId() : issue.getId()));
        //Save
        manifestacao.setTextoManifestacao(texto);
        // Usario/Manifestante
        PessoaDTO pessoa = new PessoaDTO();
        User u = issue.getUser();
        pessoa.setNome(u.getName());
        String email = issue.getPublicEmail();
        String secondary = u.getSecondaryEmail();
        if (!Validator.isValidEmail(email) && Validator.isValidEmail(secondary)) {
            email = secondary;
        }
        pessoa.setEmail(email);
        Gender g = u.getGender();
        if (g == Gender.MALE || g == Gender.FEMALE) {
            pessoa.setGenero((g == Gender.MALE ? "M" : "F"));
        }
        if (!Validator.isEmptyString(address)) {
            pessoa.setLogradouro(address);
        }
        manifestacao.setPessoa(pessoa);
        // Preparando Json
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", String.format("Bearer %s", accessToken));
        // Request
        String response = post(Config.CGU_REST_URL, Config.CGU_REST_POST_CADASTRA_MANIFESTACAO, gson.toJson(manifestacao));
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            resposta = gson.fromJson(response, ManifestacaoInclusaoRespostaDTO.class);
        }
        // Resposta
        return resposta;
    }

    /**
     * Obtem uma lista de manifestacoes em um periodo de atualizacao
     *
     * @param accessToken
     * @param idSituacaoManifestacao
     * @param dataAtualizacaoInicio
     * @param dataAtualizacaoFim
     * @return
     */
    public ManifestacaoConsultaRespostaDTO[] getListaManifestacao(String accessToken, Integer idSituacaoManifestacao, String dataAtualizacaoInicio, String dataAtualizacaoFim) {
        // Default
        ManifestacaoConsultaRespostaDTO[] manifestacoes = new ManifestacaoConsultaRespostaDTO[0];
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", String.format("Bearer %s", accessToken));
        // Request
        String response = get(Config.CGU_REST_URL, String.format(Config.CGU_REST_GET_CONSULTA_MANIFESTACAO, dataAtualizacaoInicio, dataAtualizacaoFim, idSituacaoManifestacao), null);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            manifestacoes = gson.fromJson(response, ManifestacaoConsultaRespostaDTO[].class);
        }
        //Return
        return manifestacoes;
    }

    /**
     * Detalhes de uma manifestacao
     *
     * @param accessToken
     * @param IdManifestacao
     * @return
     */
    public ManifestacaoDTO getDetalhaManifestacao(String accessToken, Integer IdManifestacao) {
        // Default
        ManifestacaoDTO manifestacao = new ManifestacaoDTO();
        //Headers
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", String.format("Bearer %s", accessToken));
        // Request
        String response = get(Config.CGU_REST_URL, String.format(Config.CGU_REST_GET_DETALHA_MANIFESTACAO, IdManifestacao), null);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            manifestacao = gson.fromJson(response, ManifestacaoDTO.class);
        }
        //Return
        return manifestacao;
    }
}
