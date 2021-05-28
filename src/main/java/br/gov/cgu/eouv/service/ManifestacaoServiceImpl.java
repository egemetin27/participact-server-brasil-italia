package br.gov.cgu.eouv.service;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.result.soap.*;
import br.gov.cgu.eouv.ws.soap.ServicoManterManifestacaoClient;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.PushNotificationsBuilder;
import it.unibo.paserver.service.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
@SuppressWarnings("Duplicates")
@Service
@Transactional(readOnly = true)
public class ManifestacaoServiceImpl implements ManifestacaoService {
    @Autowired
    OrgaoSiorgOuvidoriaService orgaoSiorgOuvidoriaService;
    @Autowired
    OuvidoriaService ouvidoriaService;
    @Autowired
    ManifestacaoHistoricoService manifestacaoHistoricoService;
    @Autowired
    ManifestacaoRespostaService manifestacaoRespostaService;
    @Autowired
    private MeOUVService meOUVService;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private ReverseGeocodingService reverseGeocodingService;
    @Autowired
    private IssueSubCategoryHasRelationshipService issueSubCategoryHasRelationshipService;
    @Autowired
    private PushNotificationsService pushNotificationsService;
    @Autowired
    private TaskPublishService taskPublishService;
    @Autowired
    private UserListService userListService;
    @Autowired
    private UserListItemService userListItemService;
    @Autowired
    private UserListPushService userListPushService;
    @Autowired
    private UserService userService;

    /**
     * Busca atualizacao dos tickets e salva no banco
     *
     * @param numProtocolo
     * @param issueId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public boolean getListaManifestacaoOuvidoria(String numProtocolo, Long issueId) {
        //Issue Report
        //Vars
        String dataAtualizacaoInicio = null;
        String dataAtualizacaoFim = null;
        IssueReport ir = null;
        if (issueId != null && issueId > 0) {
            ir = issueReportService.findById(issueId);
            if (ir != null) {
                numProtocolo = Useful.removeAllNonNumeric(ir.getPublicProtocol());
            }
        }
        //Check
        if (!Validator.isValidNumeric(numProtocolo)) {
            DateTime now = new DateTime();
            dataAtualizacaoInicio = now.minusHours(1).toString("dd/MM/yyyy HH:mm");
            dataAtualizacaoFim = now.plusHours(1).toString("dd/MM/yyyy HH:mm");
        }
        //GetLista
        ServicoManterManifestacaoClient client = new ServicoManterManifestacaoClient();
        ListaManifestacaoOuvidoriaResult rs = client.getListaManifestacaoOuvidoria(dataAtualizacaoInicio, dataAtualizacaoFim, numProtocolo);
        if (rs != null && rs.getCodigoErro() == 0) {
            List<ManifestacaoOuvidoriaResult> manifestacoes = rs.getManifestacoesOuvidoria();
            for (ManifestacaoOuvidoriaResult m : manifestacoes) {
                //Check
                ir = issueReportService.findByPublicProtocol(m.getNumProtocolo());
                if (ir != null) {
                    ir.setTransient(true);//Flag para forcar atualizar, remover na rotina em background
                    numProtocolo = ir.getPublicProtocol();
                    if (numProtocolo.equals(m.getNumProtocolo())) {
                        //Clear
                        manifestacaoHistoricoService.deleteAll(ir.getId());
                        manifestacaoRespostaService.deleteAll(ir.getId());
                        //Up date
                        ir.setPublicDate(new DateTime());
                        issueReportService.saveOrUpdate(ir);
                        //Loop/Update
                        if (ir.isTransient()) {
                            //Historicos
                            List<HistoricoAcaoManifestacaoResult> historicoAcaoManifestacaoResult = m.getListaHistoricosManifestacao();
                            for (HistoricoAcaoManifestacaoResult h : historicoAcaoManifestacaoResult) {
                                //Historico
                                ManifestacaoHistorico mh = new ManifestacaoHistorico(h);
                                mh.setRelationshipId(ir.getId());
                                manifestacaoHistoricoService.saveOrUpdate(mh);
                            }
                            //Respostas
                            List<RespostasManifestacaoResult> respostasManifestacaoResult = m.getListaRespostasManifestacao();
                            if (respostasManifestacaoResult.size() > 0) {
                                for (RespostasManifestacaoResult rm : respostasManifestacaoResult) {
                                    //Resposta
                                    ManifestacaoResposta mr = new ManifestacaoResposta(rm);
                                    mr.setRelationshipId(ir.getId());
                                    manifestacaoRespostaService.saveOrUpdate(mr);
                                }
                            }
                        }
                    }
                }
            }
            //Resposta
            return true;
        }
        return false;
    }


    /**
     * Insere uma manifestacao na CGU
     * CLAUDIONOR: ESSA CLASSE EH COMPARTILHADA COM AS APIs DO APP E PAINEL, MUITA ATENCAO AO ALTERAR O METODO
     *
     * @param issue
     */
    @Override
    @Transactional(readOnly = false)
    @Async
    public void registerThirdPartyManifestation(IssueReport issue) {
        // Long
        Long idOmbudsman = issue.getOmbudsmanId();
        String ombudsmanName = null;
        if (idOmbudsman != null && idOmbudsman > 0) {
            this.includeReportOmbudsman(issue);
        } else {
            // Clazz
            User user = issue.getUser();
            User progenitor = null;
            String userEmailProgenitor = user.getOfficialEmail();
            // Yes we can
            boolean weCanDoIt = Config.CGU_ALLOW_ALL_REPORT || user.getHasAllowOmbudsman();
            if (user.getId() != user.getProgenitorId() && user.getProgenitorId() > 0) { // Se o usuario nao pode, verificamos se seu parent pode
                progenitor = userService.getUser(user.getProgenitorId());
                if (progenitor != null) {
                    // Re check
                    if (!weCanDoIt) {
                        weCanDoIt = progenitor.getHasAllowOmbudsman();
                    }
                    // Email is valid of the progenitor?
                    if (!Validator.isPreventEmailProject(progenitor.getOfficialEmail()) && Validator.isValidEmail(progenitor.getOfficialEmail())) {
                        userEmailProgenitor = progenitor.getOfficialEmail();
                    } else if (!Validator.isEmptyString(progenitor.getSecondaryEmail()) && Validator.isValidEmail(progenitor.getSecondaryEmail())) {
                        userEmailProgenitor = progenitor.getSecondaryEmail();
                    }
                }
            }
            if (weCanDoIt) { // Usuario pode cadastrar?
                //Contem valido email?
                String userEmail = user.getOfficialEmail();
                String userName = user.getName();
                String userEmailSecondary = user.getSecondaryEmail();
                String userEmailPrimary = userEmail;
                String optionalUserName = issue.getOptionalUserName();
                String optionalUserEmail = issue.getOptionalUserEmail();
                // Nomes setados direto no report
                if (!Validator.isEmptyString(optionalUserName) && !userName.equalsIgnoreCase(optionalUserName)) {
                    user.setName(optionalUserName);
                }
                if (!Validator.isEmptyString(optionalUserEmail) && Validator.isValidEmail(optionalUserEmail)) {
                    userEmailPrimary = optionalUserEmail;
                }
                //Validacao de Emails
                if (Validator.isPreventEmailProject(userEmail) && !Validator.isEmptyString(userEmailSecondary) && !Validator.isPreventEmailProject(userEmailSecondary) && Validator.isValidEmail(userEmailSecondary)) {
                    userEmailPrimary = userEmailSecondary;
                } else if (!Validator.isPreventEmailProject(userEmailProgenitor) && Validator.isValidEmail(userEmailProgenitor)) {
                    userEmailPrimary = userEmailProgenitor;
                }
                // Check
                if (!Validator.isPreventEmailProject(userEmailPrimary) && Validator.isValidEmail(userEmailPrimary)) {
                    // Municipio
                    String mun = null;
                    String formattedCity = issue.getFormattedCity();
                    // Geo Reverse
                    if (Validator.isEmptyString(formattedCity)) {
                        double lat = issue.getLatitude();
                        double lng = issue.getLongitude();
                        ReverseGeocoding geocoding = reverseGeocodingService.lookup(lat, lng);
                        if (geocoding != null) {
                            mun = geocoding.getAddressCity();
                            formattedCity = mun;
                            issue.setFormattedCity(formattedCity);
                        }
                    } else {
                        mun = formattedCity;
                    }
                    // Check
                    if (!Validator.isEmptyString(formattedCity)) {
                        if (mun != null) {
                            idOmbudsman = 0L;
                            List<HashMap<String, Object>> listOmbudsman = new ArrayList<>();
                            List<Object[]> listOuv = ouvidoriaService.fetchAllowOmbudsmansOffice(Useful.unaccent(mun));
                            if (listOuv != null && listOuv.size() > 0) {
                                boolean isOk = false;
                                for (Object[] obk : listOuv) {
                                    Long idouvidoria = Long.parseLong(obk[0].toString());
                                    Long idmunicipio = Long.parseLong(obk[1].toString());
                                    Long codmun7 = Long.parseLong(obk[2].toString());
                                    Long codmun6 = Long.parseLong(obk[3].toString());
                                    Long counter = Long.parseLong(obk[4].toString());
                                    String nomeorgaoouvidoria = obk[5] != null ? obk[5].toString() : "";
                                    //Se contem maior que 0, entao eh restrita
                                    if (counter > 0) {
                                        if (issueSubCategoryHasRelationshipService.ItContains(issue.getSubcategory().getId(), idouvidoria)) {
                                            if (!isOk) {
                                                isOk = true;
                                                idOmbudsman = idouvidoria;
                                                ombudsmanName = nomeorgaoouvidoria;
                                            } else {
                                                //Map
                                                HashMap<String, Object> map = new HashMap<String, Object>();
                                                map.put("idOmbudsman", idouvidoria);
                                                map.put("ombudsmanName", nomeorgaoouvidoria);
                                                //List
                                                listOmbudsman.add(map);
                                            }
                                        }
                                    } else if (counter == 0) {
                                        // Se nao tiver nenhum vinculo, eh default, mas continua procurando se nao ha restritas
                                        if (!isOk) {
                                            isOk = true;
                                            idOmbudsman = idouvidoria;
                                            ombudsmanName = nomeorgaoouvidoria;
                                        } else {
                                            //Map
                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("idOmbudsman", idouvidoria);
                                            map.put("ombudsmanName", nomeorgaoouvidoria);
                                            //List
                                            listOmbudsman.add(map);
                                        }
                                    }
                                }
                                //Encontrou alguma?
                                if (isOk && idOmbudsman > 0) {
                                    issue.setOmbudsman(true);
                                    issue.setOmbudsmanId(idOmbudsman);
                                    issue.setOmbudsmanName(ombudsmanName);
                                    issue.setPublicEmail(userEmailPrimary);
                                    issue.setOmbudsmanList(listOmbudsman);
                                    //Primario
                                    this.includeReportOmbudsman(issue);
                                    //Secundarios
                                    if (listOmbudsman.size() > 0 && issue.getFileCounter() > 0) {
                                        System.out.println("listOmbudsman");
                                        for (HashMap<String, Object> map : listOmbudsman) {
                                            idOmbudsman = Long.parseLong(map.getOrDefault("idOmbudsman", 0L).toString());
                                            ombudsmanName = (String) map.getOrDefault("ombudsmanName", " - ");
                                            if (idOmbudsman.longValue() > 0) {
                                                try {
                                                    IssueReport copy = issue.copy();
                                                    copy.setOmbudsmanId(idOmbudsman);
                                                    copy.setOmbudsmanName(ombudsmanName);
                                                    copy.setPublicUrl(null);
                                                    copy.setPublicMessage(null);
                                                    copy.setPublicProtocol(null);
                                                    copy.setPrivateProtocol(null);
                                                    copy.setPrivateCodeAccess(null);
                                                    copy.setPrivateRelSelf(null);
                                                    copy.setPrivateRelEouv(null);
                                                    copy.setQueued(false);
                                                    copy.setFail(false);
                                                    if (issueReportService.saveOrUpdate(copy) != null) {
                                                        //System.out.println(copy.toString());
                                                        // Muitas requisicoes seguidas podem ser bloqueadas na CGU
                                                        Useful.uSleep(5);
                                                        // Secundarios
                                                        this.includeReportOmbudsman(copy);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace(System.out);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                //Fail
                                issue.setFail(true);
                                issue.setPublicMessage("Ouvidoria, dados não encontrados");
                                // Update
                                issueReportService.saveOrUpdate(issue);
                            }
                        } else {
                            //Fail
                            issue.setFail(true);
                            issue.setPublicMessage("Cidade, dados não carregados");
                            // Update
                            issueReportService.saveOrUpdate(issue);
                        }
                    } else {
                        //Fail, nao foi possivel detectar a geo localizacao
                        issue.setFail(true);
                        issue.setPublicMessage("Localização, coordenadas não encontradas");
                        // Update
                        issueReportService.saveOrUpdate(issue);
                    }
                } else {
                    //Fail
                    issue.setFail(true);
                    issue.setPublicMessage("E-mail, Usuário não possui um e-mail de contato válido.");
                    // Update
                    issueReportService.saveOrUpdate(issue);
                }
            } else {
                issue.setPublicMessage(String.format("Usuario. Id (%d) do Usario sem permissao", user.getId()));
                disableOmbudsman(issue);
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public IssueReport includeReportOmbudsman(IssueReport issue) {
        // Protocolo/Criando
        if (Config.CGU_PROTOCOL_DEFAULT.equals(Config.CGU_PROTOCOL_SOAP)) {
            ServicoManterManifestacaoClient s = new ServicoManterManifestacaoClient();
            RegistrarManifestacaoResult rs = s.registrarManifestacaoTerceiro(issue);
            // Set
            issue.setFail(rs.getCodigoErro() != 0);
            issue.setPublicMessage(rs.getDescricaoErro());
            issue.setPublicProtocol(rs.getProtocolo());
            issue.setPublicUrl(rs.getUrl());
            // Update
            return issueReportService.saveOrUpdate(issue);
        } else if (Config.CGU_PROTOCOL_DEFAULT.equals(Config.CGU_PROTOCOL_REST)) {
            Long idOuvidoria = issue.getOmbudsmanId();
            if (idOuvidoria != null && idOuvidoria > 0) {
                Ouvidoria ouv = ouvidoriaService.find(idOuvidoria);
                if (ouv != null) {
                    issue.setIdCity(ouv.getIdMunicipio());
                    issue.setIdSphere(ouv.getIdEsfera());
                }
            }
            // Client
            RegistrarManifestacaoResult rs = meOUVService.postManifestacaoInclusao(issue);
            // Set
            boolean isFail = rs.getCodigoErro() != 0;
            issue.setFail(isFail);
            issue.setPublicMessage(rs.getDescricaoErro());
            issue.setPublicProtocol(rs.getProtocolo());
            issue.setPublicUrl(String.format("https://%s", Config.CGU_REST_URL));
            issue.setPrivateCodeAccess(rs.getCodigoAcesso());
            issue.setPrivateProtocol(String.valueOf(rs.getIdManifestacao()));
            issue.setPrivateRelEouv(rs.getUrlOuv());
            issue.setPrivateRelSelf(rs.getUrlSelf());
            // Se sucesso, envia push
            if (!isFail && !issue.isSecondary()) {
                String message = String.format("Sua manifestação foi enviada para a ouvidoria responsável.  %s ", issue.getPrivateRelEouv());
                this.pushOmdudsman(issue.getUser().getId(), message);
            }
            // Update
            return issueReportService.saveOrUpdate(issue);
        }
        return null;
    }

    @Async
    @Override
    @Transactional(readOnly = false)
    public void pushOmdudsman(Long userId, String message) {
        // Enum
        PANotification.Type enumPaNotification = PANotification.Type.MESSAGE;
        // Data
        PushNotificationsBuilder pb = new PushNotificationsBuilder();
        pb.setAll(0L, 0L, enumPaNotification, "[]", false, message);
        pb.setPublish(true);
        pb.setMail(false);
        pb.setEmailSubject("Fala.BR");
        pb.setEmailBody("Fala.BR");
        pb.setEmailSystemId(0L);
        PushNotifications p = pb.build(true);
        PushNotifications push = pushNotificationsService.saveOrUpdate(p);
        if (push != null) {
            //User List
            UserList userList = new UserList();
            userList.setHashmap("[]");
            userList.setAudienceSelector(AudienceSelector.SELECTOR_CLOSED);
            userList.setParentId(0L);
            userList = userListService.saveOrUpdate(userList);
            if (userList != null) {
                //Item List
                UserListItem userListItem = new UserListItem();
                userListItem.setId(0L);
                userListItem.setListId(userList.getId());
                userListItem.setUserId(userId);
                if (userListItemService.saveOrUpdate(userListItem) != null) {
                    //Push List
                    UserListPush userListPush = new UserListPush();
                    userListPush.setId(0L);
                    userListPush.setPushId(push);
                    userListPush.setUserListId(userList);
                    if (userListPushService.saveOrUpdate(userListPush) != null) {
                        taskPublishService.sendParticipantListPush(push);
                    }
                }
            }
        }
    }

    /**
     * Desabilita o relato como de ouvidoria
     *
     * @param issue
     */
    @Override
    @Transactional(readOnly = false)
    public void disableOmbudsman(IssueReport issue) {
        if (issue.isOmbudsman()) {
            issue.setOmbudsman(false);
            issueReportService.saveOrUpdate(issue);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoHistorico> getListaManifestacaoHistorico(Long issueId) {
        return manifestacaoHistoricoService.fetchAllByRelationId(issueId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoResposta> getListaManifestacaoResposta(Long issueId) {
        return manifestacaoRespostaService.fetchAllByRelationId(issueId);
    }

    @Override
    @Transactional(readOnly = true)
    public ManifestacaoResposta getLastManifestacaoResposta(Long issueId) {
        return manifestacaoRespostaService.getLastManifestacaoResposta(issueId);
    }
}
