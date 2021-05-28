package it.unibo.paserver.manteinance;

import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.CguCrontab;
import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import br.gov.cgu.eouv.result.rest.*;
import br.gov.cgu.eouv.result.soap.HistoricoAcaoManifestacaoResult;
import br.gov.cgu.eouv.result.soap.ListaManifestacaoOuvidoriaResult;
import br.gov.cgu.eouv.result.soap.ManifestacaoOuvidoriaResult;
import br.gov.cgu.eouv.result.soap.RespostasManifestacaoResult;
import br.gov.cgu.eouv.service.*;
import br.gov.cgu.eouv.ws.soap.ServicoManterManifestacaoClient;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.PushNotificationsBuilder;
import it.unibo.paserver.service.*;
import it.unibo.paserver.web.controller.GCMController;
import it.unibo.paserver.web.controller.MailerController;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
@Component
public class RunSchedulerManteinance {
    @Autowired
    OuvidoriaService ouvidoriaService;
    @Autowired
    MeOUVService meOUVService;
    @Autowired
    OrgaoSiorgOuvidoriaService orgaoSiorgOuvidoriaService;
    @Autowired
    ManifestacaoService manifestacaoService;
    @Autowired
    ManifestacaoHistoricoService manifestacaoHistoricoService;
    @Autowired
    ManifestacaoRespostaService manifestacaoRespostaService;
    @Autowired
    private SystemEmailService systemEmailService;
    @Autowired
    private MailingLogsService mailingQueueService;
    @Autowired
    private PushNotificationsService pushNotificationsService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private GCMController gcmController;
    @Autowired
    private MailerController mailerController;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private CguCrontabService cguCrontabService;

    /**
     * Rodando Rotina
     */
    public void run() {
        try {
            String cronDate = new Date().toString();
            System.out.println("RunSchedulerManteinance : " + cronDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void runCleanQueue() {
        String cronDate = new Date().toString();
        // System.out.println("\n runCleanQueue : " + cronDate);
        try {
            List<SystemEmail> seList = systemEmailService.findAll();
            if (seList != null && !seList.isEmpty()) {
                for (SystemEmail se : seList) {
                    se.setProcessing(false);
                    systemEmailService.saveOrUpdate(se);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Processando Fila/Lista dos Emails
     */
    public void runMailingQueue() {
        String cronDate = new Date().toString();
        // System.out.println("\n runMailingQueue : " + cronDate + " \n");
        List<SystemEmail> seList = systemEmailService.findAllNotProcessing();
        //System.out.println("\n seList : " + seList.size() + " \n");
        if (seList != null && !seList.isEmpty()) {
            // Email Settings
            for (SystemEmail se : seList) {
                // Users
                List<MailingLogs> userList = new ArrayList<MailingLogs>();
                // Infos
                Long emailId = se.getId();
                Long limitSending = se.getLimitSending() != null ? se.getLimitSending() : 500;
                Long limitTime = se.getLimitTime() != null ? se.getLimitTime() : 60L;
                Long limitCountCurrent = se.getLimitCounterCurrent() != null ? se.getLimitCounterCurrent() : 0;
                Long limitCounterTotal = se.getLimitCounterTotal() != null ? se.getLimitCounterTotal() : 0;
                DateTime limitDeliveryDate = se.getLimitDeliveryDate();
                if (limitDeliveryDate == null) {
                    limitDeliveryDate = new DateTime();
                    se.setLimitDeliveryDate(limitDeliveryDate);
                }
                // Mailing Queue
                if (limitCountCurrent >= limitSending && limitDeliveryDate.isBeforeNow()) {// Limite Antigo
                    se.setLimitCounterCurrent(0L);
                    // System.out.println("isBeforeNow");
                } else if (limitDeliveryDate.isBeforeNow()) {
                    // LOCKED
                    se.setProcessing(true);
                    if (systemEmailService.saveOrUpdate(se) != null) {
                        // Lists
                        List<MailingLogs> mqList = mailingQueueService.findAllByEmailId(emailId, limitSending);
                        if (mqList != null && !mqList.isEmpty()) {
                            for (MailingLogs mq : mqList) {
                                mq.setProcessed(true);
                                mq.setQueued(false);
                                // ATUALIZA CONTADORES
                                limitCounterTotal++;
                                limitCountCurrent++;
                                // ENVIA EMAIL
                                // System.out.println(mq.getTaskId() + " >> " + mq.getEmailId() + " >> Email " + mq.getUserEmail());
                                if (Validator.isPreventEmailProject(mq.getUserEmail())) {
                                    mq.setRejected(true);
                                    mailingQueueService.saveOrUpdate(mq);
                                } else {
                                    userList.add(mq);
                                    // Updata mq item
                                    mailingQueueService.saveOrUpdate(mq);
                                    // Verifica se excedeu o limite
                                    if (limitCountCurrent >= limitSending) {
                                        se.setLimitDeliveryDate(new DateTime().plusSeconds(limitTime.intValue()));
                                        break;
                                    }
                                }
                            }
                        }
                        se.setLimitCounterTotal(limitCounterTotal);
                        se.setLimitCounterCurrent(limitCountCurrent);
                        se.setLimitTime(limitTime);
                    }
                    // UNLOCKED
                    se.setProcessing(false);
                }
                systemEmailService.saveOrUpdate(se);
                // Contem usuarios para envio?
                if (!userList.isEmpty() && userList.size() > 0) {
                    mailerController.inviteMailSending(userList, se);
                }
            }
        }
    }

    /**
     * Envio de Push's
     */
    public void runPushingQueue() {
        String cronDate = new Date().toString();
        System.out.println("\n runPushingQueue : " + cronDate);
        try {

            List<SystemEmail> seList = systemEmailService.findAllNotProcessing();
            // System.out.println("runPushingQueue seList : " + seList.size());
            if (seList != null && !seList.isEmpty()) {
                // Fake para as campanhas sem email
                SystemEmail fake = new SystemEmail();
                fake.setId(0L);
                seList.add(fake);
                // Lists
                // List<User> userList = new ArrayList<User>();
                ListMultimap<Long, User> userMultimap = ArrayListMultimap.create();
                ListMultimap<Long, PushNotifications> pushMultimap = ArrayListMultimap.create();
                // Loop
                for (SystemEmail se : seList) {
                    List<MailingLogs> mqList = mailingQueueService.findAllByEmailId(se.getId(), 9999L, true);
                    System.out.println("runPushingQueue mailingQueueService: " + se.getId() + " ? " + mqList.size());
                    if (mqList != null && !mqList.isEmpty()) {
                        for (MailingLogs mq : mqList) {
                            mq.setProcessed(true);
                            mq.setQueued(false);
                            if (mq.getEmailId() > 0) {
                                mq.setResend(true);
                                // mq.setDeliveryDate(new DateTime().plusDays(1));
                                mq.setDeliveryDate(new DateTime().plusMinutes(3).minusHours(3));
                            }
                            // JOGA PARA A FILA DO PUSH
                            // System.out.println(mq.getTaskId() + " Push " + mq.getUserEmail());
                            User u = new User();
                            u.setId(mq.getUserId());
                            u.setOfficialEmail(mq.getUserEmail());
                            u.setDevice(mq.getUserDevice());
                            u.setGcmId(mq.getUserDeviceToken());
                            // userList.add(u);
                            userMultimap.put(mq.getTaskId(), u);
                            // Update MQ item
                            mailingQueueService.saveOrUpdate(mq);
                            // Push info
                            if (!pushMultimap.containsKey(mq.getTaskId())) {
                                // Task
                                Task t = taskService.findById(mq.getTaskId());
                                // History
                                PushNotificationsBuilder pb = new PushNotificationsBuilder();
                                pb.setAll(0L, t.getParentId(), mq.getUserDevicePushTypeId(), "[]", true, t.getName());
                                pb.setTaskId(mq.getTaskId());
                                PushNotifications p = pb.build(true);
                                PushNotifications push = pushNotificationsService.saveOrUpdate(p);
                                if (push != null) {
                                    pushMultimap.put(mq.getTaskId(), push);
                                }
                            }
                        }
                    }
                }
                // Contem usuarios para envio?
                if (!userMultimap.isEmpty() && userMultimap.size() > 0) {
                    for (Long key : userMultimap.keySet()) {
                        List<User> userList = userMultimap.get(key);
                        PushNotifications push = pushMultimap.get(key).get(0);
                        // Sender
                        gcmController.notifyByListPushies(PANotification.Type.NEW_TASK, userList, push);
                        // System.out.println(userList.toString());
                        // System.out.println(push.toString());
                    }
                    // gcmController.notifyByListUser(PANotification.Type.NEW_TASK, userList);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Reenvio de Avisos
     */
    public void runMailingResendQueue() {
        String cronDate = new Date().toString();
        System.out.println("\n runMailingResendQueue : " + cronDate + " \n");
        List<SystemEmail> seList = systemEmailService.findAllNotProcessing();
        if (seList != null && !seList.isEmpty()) {
            // Loop
            for (SystemEmail se : seList) {
                List<MailingLogs> mqList = mailingQueueService.findAllResendByEmailId(se.getId(), se.getLimitSending());
                if (mqList != null && !mqList.isEmpty()) {
                    for (MailingLogs mq : mqList) {
                        // Atualiza registro antigo
                        if (mq.getUserDevicePushTypeId() == null) {
                            mq.setUserDevicePushTypeId(PANotification.Type.NEW_TASK);
                        }
                        mq.setRejected(true);
                        mailingQueueService.saveOrUpdate(mq);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        MailingLogs mqCopy;
                        mqCopy = mq;
                        mqCopy.setId(0L);
                        mqCopy.setAccepted(true);
                        mqCopy.setRejected(false);
                        mqCopy.setProcessed(false);
                        mqCopy.setDelivered(false);
                        mqCopy.setDropped(false);
                        mqCopy.setResend(false);
                        mqCopy.setQueued(true);
                        mqCopy.setPushed(false);
                        mailingQueueService.saveOrUpdate(mqCopy);
                    }
                }
            }
        }
    }

    /**
     * Sincronizacao em background com SOAP
     */
    public void runCguSoapListaManifestacaoOuvidoria() {
        // System.out.println("runCguGetListaManifestacaoOuvidoria");
        DateTime now = new DateTime();
        String dataAtualizacaoInicio = now.minusHours(1).toString("dd/MM/yyyy HH:mm");
        String dataAtualizacaoFim = now.plusHours(1).toString("dd/MM/yyyy HH:mm");
        try {
            //GetLista
            ServicoManterManifestacaoClient client = new ServicoManterManifestacaoClient();
            ListaManifestacaoOuvidoriaResult rs = client.getListaManifestacaoOuvidoria(dataAtualizacaoInicio, dataAtualizacaoFim, null);
            if (rs != null && rs.getCodigoErro() == 0) {
                List<ManifestacaoOuvidoriaResult> manifestacoes = rs.getManifestacoesOuvidoria();
                for (ManifestacaoOuvidoriaResult m : manifestacoes) {
                    //Check
                    IssueReport ir = issueReportService.findByPublicProtocol(m.getNumProtocolo());
                    if (ir != null) {
                        ir.setTransient(true);//Flag para forcar atualizar, remover na rotina em background
                        String numProtocolo = ir.getPublicProtocol();
                        if (numProtocolo.equals(m.getNumProtocolo())) {
                            //Clear
                            //manifestacaoHistoricoService.deleteAll(ir.getId());
                            //manifestacaoRespostaService.deleteAll(ir.getId());
                            //Loop/Update
                            if (ir.isTransient()) {
                                System.out.println(numProtocolo);
                                //Historicos
                                List<HistoricoAcaoManifestacaoResult> historicoAcaoManifestacaoResult = m.getListaHistoricosManifestacao();
                                System.out.println(String.format("Historico Acao Manifestacao Result : %s ", historicoAcaoManifestacaoResult.size()));
                                for (HistoricoAcaoManifestacaoResult h : historicoAcaoManifestacaoResult) {
                                    //Historico
                                    boolean has = manifestacaoHistoricoService.itContaimItem(ir.getId(), h.getDataAcao());
                                    //System.out.println(String.format("Historico %s", has));
                                    if (!has) {
                                        ManifestacaoHistorico mh = new ManifestacaoHistorico(h);
                                        mh.setRelationshipId(ir.getId());
                                        manifestacaoHistoricoService.saveOrUpdate(mh);
                                    }
                                }
                                //Respostas
                                List<RespostasManifestacaoResult> respostasManifestacaoResult = m.getListaRespostasManifestacao();
                                System.out.println(String.format("Respostas Manifestacao Result : %s ", historicoAcaoManifestacaoResult.size()));
                                if (respostasManifestacaoResult.size() > 0) {
                                    for (RespostasManifestacaoResult rm : respostasManifestacaoResult) {
                                        //Resposta
                                        boolean has = manifestacaoRespostaService.itContaimItem(ir.getId(), rm.getDataResposta());
                                        //System.out.println(String.format("Resposta %s", has));
                                        if (!has) {
                                            ManifestacaoResposta mr = new ManifestacaoResposta(rm);
                                            mr.setRelationshipId(ir.getId());
                                            manifestacaoRespostaService.saveOrUpdate(mr);

                                            //Check se resolvido
                                            String tipo = rm.getTipo();
                                            if (!Validator.isEmptyString(tipo) && tipo.equals("Resposta Conclusiva")) {
                                                ir.setResolved(true);
                                                //Disparar aviso para o usuario
                                            }
                                        }
                                    }
                                }
                            }
                            //Up date
                            ir.setPublicDate(new DateTime());
                            issueReportService.saveOrUpdate(ir);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("RunCguGetListaManifestacaoOuvidoria  Exception %s", e.getMessage()));
        }
    }

    /**
     * Sincronizacao em background com REST
     */
    public void runCguRestListaManifestacaoOuvidoria() {
        System.out.println("runCguRestListaManifestacaoOuvidoria");
        DateTime now = new DateTime();
        String dataAtualizacaoInicio = now.toString("dd/MM/yyyy");
        String dataAtualizacaoFim = now.toString("dd/MM/yyyy");
        CguCrontab crontab = cguCrontabService.first();
        if (crontab != null && crontab.getDeliverydate().isBefore(now)) {
            crontab.setDeliverydate(new DateTime().plusHours(crontab.getHh()));
            cguCrontabService.saveOrUpdate(crontab);
            try {
                //GetLista
                ManifestacaoConsultaRespostaDTO[] listaConsulta = meOUVService.getManifestacaoConsulta(0, dataAtualizacaoInicio, dataAtualizacaoFim);
                if (listaConsulta != null && listaConsulta.length > 0) {
                    for (ManifestacaoConsultaRespostaDTO m : listaConsulta) {
                        //Check
                        IssueReport ir = issueReportService.findByPrivateProtocol(m.getIdManifestacao());
                        if (ir != null && !ir.isResolved()) {
                            ir.setTransient(true);//Flag para forcar atualizar, remover na rotina em background
                            String numProtocolo = ir.getPublicProtocol();
                            Integer[] situacoes = new Integer[]{6, 7, 8};
                            // Convert String Array to List
                            List<Integer> listSituacoes = Arrays.asList(situacoes);
                            // System.out.println(m.getNumerosProtocolo().contains(numProtocolo));
                            if (m.getNumerosProtocolo().contains(numProtocolo) && listSituacoes.contains(m.getSituacao().getIdSituacaoManifestacao())) {
                                //Loop/Update
                                if (ir.isTransient()) {
                                    ir.setResolved(true); // Se caiu aqui, eh pq finalizou/encerrou/concluiu
                                    //Acoes
                                    ManifestacaoDTO detalheManifestacao = meOUVService.getDetalhaManifestacao(m.getIdManifestacao());
                                    if (detalheManifestacao != null && detalheManifestacao.getHistorico().length > 0) {
                                        HistoricoManifestacaoDTO[] eventos = detalheManifestacao.getHistorico();
                                        for (HistoricoManifestacaoDTO evento : eventos) {
                                            // Historico
                                            HistoricoAcaoDTO hist = evento.getHistoricoAcao();
                                            if (hist != null && !Validator.isEmptyString(hist.getDataHoraAcao())) {
                                                DateTime dataAcao = Useful.converteStringToDate(Useful.datetimeSystemToDb(hist.getDataHoraAcao()));
                                                boolean has = manifestacaoHistoricoService.itContaimItem(ir.getId(), dataAcao);
                                                if (!has) {
                                                    ManifestacaoHistorico mh = new ManifestacaoHistorico(hist);
                                                    mh.setRelationshipId(ir.getId());
                                                    manifestacaoHistoricoService.saveOrUpdate(mh);
                                                }
                                            }
                                            // Resposta
                                            RespostaManifestacaoDTO resp = evento.getResposta();
                                            if (resp != null && !Validator.isEmptyString(resp.getIdRespostaManifestacao())) {
                                                boolean has = manifestacaoRespostaService.itContaimItemById(ir.getId(), resp.getIdRespostaManifestacao());
                                                if (!has) {
                                                    ManifestacaoResposta mr = new ManifestacaoResposta(resp);
                                                    mr.setRelationshipId(ir.getId());
                                                    manifestacaoRespostaService.saveOrUpdate(mr);
                                                }
                                            }
                                        }
                                    }
                                    //Push
                                    String message = String.format("Sua manifestação foi respondida pelo órgão responsável.  %s ", ir.getPrivateRelEouv());
                                    manifestacaoService.pushOmdudsman(ir.getUser().getId(), message);
                                    //Secundario
                                    if (ir.isSecondary() && ir.getParentId() > 0) {
                                        IssueReport im = issueReportService.findById(ir.getParentId());
                                        if (im != null) {
                                            im.setResolved(true);
                                            issueReportService.saveOrUpdate(im);
                                        }
                                    }
                                }
                                //Up date
                                ir.setPublicDate(new DateTime());
                                issueReportService.saveOrUpdate(ir);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(String.format("RunCguGetListaManifestacaoOuvidoria  Exception %s", e.getMessage()));
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println(String.format("CGU Crontab %s ", now.toString()));
        }
    }
}