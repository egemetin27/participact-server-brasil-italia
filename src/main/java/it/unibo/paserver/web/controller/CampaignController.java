package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.TaskBuilder;
import it.unibo.paserver.service.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.EnumUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;

/**
 * Controller das Campanhas
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class CampaignController extends ApplicationController {
    ResponseJson response = new ResponseJson();
    @Autowired
    private AccountService accountService;
    @Autowired
    private TaskPublishService taskPublishService;
    @Autowired
    private FileExportService fileExportService;
    @Autowired
    private SystemEmailService systemEmailService;
    @Autowired
    private MailingHistoryService mailingHistoryService;
    @Autowired
    private SystemPageService systemPageService;
    @Autowired
    private CampaignTaskQuestionnaireController campaignTaskQuestionnaireController;

    /**
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND','ROLE_RESEARCHER_OMBUDSMAN','ROLE_COOPERATION_AGREEMENT')")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("/protected/campaign/index");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        // Filter
        List<Account> accounts = null;
        isRoot(request);
        if (isAdmin) {
            accounts = accountService.getAccounts();
        }
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isResearcher", isResearcher);
        // View
        return modelAndView;
    }

    /**
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/campaign/form", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/campaign/form");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        // Texto defualt
        String filename = "classpath:/META-INF/invite_task_mail_deault.txt";
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        String altBody = "";
        String agreement = "";
        try {
            // Email
            InputStream source = defaultResourceLoader.getResource(filename).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(source, "UTF-8"));
            altBody = org.apache.commons.io.IOUtils.toString(reader);
            // Termo de Consentimento
            SystemPage privacy = systemPageService.findByType(SystemPageType.PAGE_PRIVACY);
            if (privacy != null) {
                agreement = Useful.htmlifyPlain(privacy.getContent());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);
        }

        modelAndView.addObject("systemEmail", systemEmailService.findAll());
        modelAndView.addObject("altBody", altBody);
        modelAndView.addObject("historyEmail", mailingHistoryService.findAll());
        modelAndView.addObject("agreement", agreement);
        modelAndView.addObject("wpPublishText", "");
        // return
        return modelAndView;
    }

    /**
     * @param id
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView, HttpServletRequest request) {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        if (!isPublish || isAdmin || isAgreement) {
            // Model view
            modelAndView.setViewName("/protected/campaign/form");
            modelAndView.addObject("form", id);
        } else {
            modelAndView.setViewName("/protected/campaign-task/index");
            modelAndView.addObject("id", id);
            modelAndView.addObject("isExpired", isExpired);
            modelAndView.addObject("hasLocation", hasAction(id, Pipeline.Type.LOCATION.toInt()));
        }
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("isPublish", isPublish);
        modelAndView.addObject("systemEmail", systemEmailService.findAll());
        return modelAndView;
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign/collapse-details/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND','ROLE_COOPERATION_AGREEMENT','ROLE_COOPERATION_AGREEMENT')")
    public @ResponseBody
    ResponseJson collapseDetails(@PathVariable("id") long id, HttpServletRequest request) {
        // Security
        isRoot(request);
        // Response
        response.setStatus(false);
        if (isAdmin || isResearcher) {
            // Search
            Task task = campaignService.findByIdAndParentId(id, parentId);
            if (task != null) {
                // Task
                task.setCheckDetails(task.isCheckDetails() ? false : true);
                response.setStatus(true);
                response.setItem(taskService.save(task));
            }
        }
        // Return
        return response;
    }

    /**
     * Edicao de um item
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        // Response
        response.setStatus(false);
        // Search
        Task item = campaignService.findByIdAndParentId(id, parentId);
        if (item != null) {
            Set<Action> actions = item.getActions();
            if (actions.size() > 0) {
                for (Action a : actions) {
                    a.setTranslated(messageSource.getMessage("pipeline.type." + Useful.getTranslatedActionType(a), null, LocaleContextHolder.getLocale()));
                }
            }
            // Task
            response.setStatus(true);
            response.setItem(item);
        }
        // Return
        return response;
    }

    /**
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign/removed/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        // Removed
        boolean removed = false;
        // isAdmin
        if (!isAdmin) {
            if (campaignService.findByIdAndParentId(id, parentId) != null) {
                removed = campaignService.removed(id);
            }
        } else {
            removed = campaignService.removed(id);
        }
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(removed);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign/confirm-publish/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson confirmPublish(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("confirmation.publish.fail", null, LocaleContextHolder.getLocale()));
        // Execute
        Task task = hasTask;
        if (task != null && !task.isPublish() && task.isAssign()) {
            task.setPublish(true);
            task.setEnabled(true);
            if (taskService.save(task) != null) {
                String[] arg = {task.getName()};
                response.setMessage(messageSource.getMessage("confirmation.publish.success", arg, LocaleContextHolder.getLocale()));
                // taskPublishService.publish(task, true);
                taskPublishService.sendParticipantListTask(task, true);
                response.setStatus(true);
            }
        }
        return response;
    }

    /**
     * @param id
     * @param json
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign/copy/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson copyTask(@PathVariable("id") long id, @RequestBody String json, HttpServletRequest request) throws JsonProcessingException {
        // Security
        isRoot(request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("confirmation.copy.task.fail", null, LocaleContextHolder.getLocale()));
        //Default
        //Nodes/Edges
        List<DataSetNode> nodes = new ArrayList<>();
        List<DataSetEdge> edges = new ArrayList<>();
        List<Question> ofQuestions = new ArrayList<Question>();
        Long questionnaireId = 0L;
        // Execute
        Task clone = campaignService.findByIdAndDetach(id);
        if (clone != null) {
            // Detalhes
            clone.setName("COPY " + clone.getName());
            clone.setPublish(false);
            clone.setRemoved(false);
            clone.setParentId(parentId);
            clone.setCopyId(id);
            clone.setHasPhotos(false);
            clone.setHasQuestionnaire(false);
            clone.setInviteQrCodeToken(null);
            // Actions
            Set<Action> actions = clone.getActions();
            Set<Action> copyActions = new LinkedHashSet<Action>();
            clone.setActions(new LinkedHashSet<Action>());
            if (actions.size() > 0) {
                //Actions
                for (Action a : actions) {
                    Action cloneAction = actionService.findByIdAndDetach(a.getId());
                    if (cloneAction instanceof ActionQuestionaire) {
                        int index = 0;
                        clone.setHasQuestionnaire(true);
                        // Old
                        ActionQuestionaire oq = ((ActionQuestionaire) cloneAction);
                        // Questionario
                        ActionQuestionaire nq = new ActionQuestionaire();
                        nq.setName(oq.getName());
                        nq.setTitle(oq.getTitle());
                        nq.setDescription(oq.getDescription());
                        nq.setRepeat(oq.getRepeat());
                        //Id
                        questionnaireId = nq.getId();
                        // Perguntas
                        List<Question> listQuestion = ((ActionQuestionaire) cloneAction).getQuestions();
                        for (Question q : listQuestion) {
                            Question x = new Question();
                            x.setQuestionOrder(q.getQuestionOrder());
                            x.setTargetId(0L);
                            x.setTargetOrder(q.getTargetOrder());
                            x.setTargetId(q.getTargetId());
                            x.setNumberPhotos(q.getNumberPhotos());
                            x.setPhoto(q.getPhoto());
                            x.setRequired(q.getRequired());
                            x.setDate(q.getIsDate());

                            Boolean isPhoto = q.getPhoto();
                            Boolean isText = !q.getIsClosedAnswers() && !q.getIsMultipleAnswers();
                            Boolean isDate = q.getIsDate();
                            nodes.add(q.getQuestionOrder(), new DataSetNode(0L, index, q.getQuestion()));

                            if (q.getIsClosedAnswers()) {
                                x.setIsClosedAnswers(true);
                                x.setIsMultipleAnswers(q.getIsMultipleAnswers());
                                x.setQuestion(q.getQuestion());
                                // Opcoes
                                for (ClosedAnswer opt : q.getClosed_answers()) {
                                    ClosedAnswer e = new ClosedAnswer();
                                    e.setAnswerDescription(opt.getAnswerDescription());
                                    e.setAnswerOrder(opt.getAnswerOrder());
                                    e.setTargetId(opt.getTargetId());
                                    e.setTargetOrder(opt.getTargetOrder());
                                    e.setQuestion(x);
                                    x.getClosed_answers().add(e);
                                    //Edge
                                    edges.add(new DataSetEdge(q.getQuestionOrder(), e.getTargetOrder(), e.getAnswerDescription(), false, e.getAnswerOrder()));
                                }
                                nq.getQuestions().add(x);
                            } else if (!q.getIsClosedAnswers() && !q.getIsMultipleAnswers()) {
                                x.setIsClosedAnswers(false);
                                x.setIsMultipleAnswers(false);
                                x.setQuestion(q.getQuestion());
                                // Set
                                ofQuestions.add(x);
                                nq.getQuestions().add(x);
                                //Edge
                                edges.add(new DataSetEdge(q.getQuestionOrder(), q.getTargetOrder(), q.getQuestion(), true, -1));
                            }
                            index++;
                        }
                        copyActions.add(nq);
                    } else if (cloneAction instanceof ActionSensing) {
                        copyActions.add(cloneAction);
                    } else if (cloneAction instanceof ActionPhoto) {
                        clone.setHasPhotos(true);
                        copyActions.add(cloneAction);
                    }
                }
                // Add Task
                clone.setActions(copyActions);
            }

            Task cp = taskService.save(clone);
            if (cp != null) {
                response.setStatus(true);
                response.setOutcome(cp.getId());
                response.setMessage(messageSource.getMessage("confirmation.copy.success", null, LocaleContextHolder.getLocale()));
                //Nodes/Edges
                campaignTaskQuestionnaireController.setDirectTargetId(cp.getId());
            }
        }
        return response;
    }

    /**
     * @param id
     * @param json
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign/invite/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson inviteTask(@PathVariable("id") long id, @RequestBody String json, HttpServletRequest request) throws JsonProcessingException {
        // Security
        isRoot(request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("confirmation.invite.task.fail", null, LocaleContextHolder.getLocale()));
        // Execute
        Task t = campaignService.findById(id);
        if (t != null && (isAdmin || (isAgreement && t.getParentId() == parentId))) {
            String qrCodeToken = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
            t.setInviteQrCodeToken(qrCodeToken);
            Task rs = taskService.save(t);
            if (rs != null) {
                response.setStatus(true);
                response.setOutcome(rs.getId());
                response.setMessage(messageSource.getMessage("confirmation.invite.task.success", null, LocaleContextHolder.getLocale()));
            }
        }
        return response;
    }

    /**
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(value = {"/protected/campaign/save/", "/protected/campaign/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json, HttpServletRequest request) {
        // Id do usuario
        isRoot(request);
        long parentId = userId;
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        long id = Useful.convertStringToLong(r.getAsString("id"));
        String name = r.getAsString("name");
        String description = r.getAsString("description");
        boolean canBeRefused = (isAdmin) ? r.getAsBoolean("canBeRefused") : true;
        boolean isDuration = r.getAsBoolean("isDuration");
        boolean isSendEmail = r.getAsBoolean("isSendEmail");
        boolean enabled = r.getAsBoolean("enabled");
        String emailSubject = r.getAsString("emailSubject");
        String emailBody = r.getAsString("emailBody");
        Long emailSystemId = r.getAsLong("emailSystemId");
        String agreement = r.getAsString("agreement");
        // Duration/Sensor
        long duration = Useful.convertStringToLong(r.getAsString("duration"));
        long sensingDuration = Useful.convertStringToLong(r.getAsString("sensingDuration"));

        boolean sensingWeekSun = r.getAsBoolean("sensingWeekSun");
        boolean sensingWeekMon = r.getAsBoolean("sensingWeekMon");
        boolean sensingWeekTue = r.getAsBoolean("sensingWeekTue");
        boolean sensingWeekWed = r.getAsBoolean("sensingWeekWed");
        boolean sensingWeekThu = r.getAsBoolean("sensingWeekThu");
        boolean sensingWeekFri = r.getAsBoolean("sensingWeekFri");
        boolean sensingWeekSat = r.getAsBoolean("sensingWeekSat");

        boolean wpPublishPage = r.getAsBoolean("wpPublishPage");
        boolean wpPostDescription = r.getAsBoolean("wpPostDescription");
        boolean wpPostSensorList = r.getAsBoolean("wpPostSensorList");
        boolean wpPostCampaignStats = r.getAsBoolean("wpPostCampaignStats");
        String wpPublishText = r.getAsString("wpPublishText");
        boolean wpPostQuestionnaire = r.getAsBoolean("wpPostQuestionnaire");

        String iconUrl = null;
        String color = null;
        try {
            iconUrl = r.get("iconUrl").isJsonNull() ? null : r.get("iconUrl").getAsString();
            color = r.get("color").isJsonNull() ? "#7E986C" : r.get("color").getAsString();
            if (!Validator.isValidRgbString(color)) {
                color = "#7E986C";
            }
            // System.out.println(color);
        } catch (Exception e) {
            System.out.println(String.format("%s", e.getMessage()));
        }
        // Dates Values
        String start = r.getAsString("start");
        String deadline = r.getAsString("deadline");
        try {
            if (Validator.isValidDateFormat(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
                start = Useful.converteDateToString(Useful.converteStringToDatePattern(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss");
            }

            if (Validator.isValidDateFormat(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
                deadline = Useful.converteDateToString(Useful.converteStringToDatePattern(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss");
            }

            if (r.has("startDate") && r.has("startTime")) {
                String startDate = r.getAsString("startDate");
                String startTime = r.getAsString("startTime");

                start = convertJsDateToJDate(startDate, startTime);
            }

            if (r.has("deadlineDate") && r.has("deadlineTime")) {
                String deadlineDate = r.getAsString("deadlineDate");
                String deadlineTime = r.getAsString("deadlineTime");
                deadline = convertJsDateToJDate(deadlineDate, deadlineTime);
            }

            if (r.has("dt_start") && r.has("time_start") && r.has("dt_end") && r.has("time_end")) {
                start = Useful.dateSystemToDb(r.getAsString("dt_start"), r.getAsString("time_start"));
                deadline = Useful.dateSystemToDb(r.getAsString("dt_end"), r.getAsString("time_end"));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Validate
        try {
            if (!Validator.isValidStringLength(name, 1, 100)) {
                throw new Exception(messageSource.getMessage("protected.participant.name", null, LocaleContextHolder.getLocale()) + ". " + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidDateFormat(start, "yyyy-MM-dd HH:mm") && !Validator.isValidDateFormat(start, "yyyy-MM-dd HH:mm:ss")) {
                throw new Exception(messageSource.getMessage("datestart.title", null, LocaleContextHolder.getLocale()) + ". " + messageSource.getMessage("error.start.format", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidDateFormat(deadline, "yyyy-MM-dd HH:mm") && !Validator.isValidDateFormat(deadline, "yyyy-MM-dd HH:mm:ss")) {
                throw new Exception(messageSource.getMessage("dateend.title", null, LocaleContextHolder.getLocale()) + ". " + messageSource.getMessage("error.deadline.format", null, LocaleContextHolder.getLocale()));
            } else if (Useful.getDateDifferent(deadline, start, "yyyy-MM-dd HH:mm:ss") > 0) {
                throw new Exception(messageSource.getMessage("dateend.title", null, LocaleContextHolder.getLocale()) + ". " + messageSource.getMessage("error.diff.dates", null, LocaleContextHolder.getLocale()));
            } else if (isSendEmail && !Validator.isValidStringLength(emailBody, 100, 999000)) {
                throw new Exception(messageSource.getMessage("error.altbody.required", null, LocaleContextHolder.getLocale()));
            } else if (isSendEmail && (emailSystemId == null || emailSystemId == 0)) {
                throw new Exception(messageSource.getMessage("fromEmail.title", null, LocaleContextHolder.getLocale()) + ". " + messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
            } else {
                // Email
                if (Validator.isEmptyString(emailSubject)) {
                    emailSubject = messageSource.getMessage("email.subject.invite2", null, LocaleContextHolder.getLocale());
                }
                // Segundos/Duracao
                long diffSeconds = Useful.getDateDifferent(start, deadline, "yyyy-MM-dd HH:mm:ss");
                if (isDuration || duration <= 1) {
                    duration = diffSeconds;
                }
                // Default
                Task rs = null;
                // Create
                if (id == 0) {
                    DateTime createAt = Useful.converteStringToDate(deadline);
                    if (createAt.isBeforeNow()) {
                        throw new Exception(messageSource.getMessage("dateend.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.deadline.beforenow", null, LocaleContextHolder.getLocale()));
                    } else {
                        TaskBuilder tb = new TaskBuilder();
                        tb.setId(id).setName(name).setDescription(description).setCanBeRefused(canBeRefused);
                        tb.setStart(Useful.converteStringToDate(start));
                        tb.setDeadline(createAt);
                        tb.setIsDuration(isDuration).setDuration(duration).setSensingDuration(sensingDuration);
                        tb.setSendEmail(isSendEmail);
                        tb.setEmailSubject(emailSubject);
                        tb.setEmailBody(emailBody);
                        tb.setActions(new LinkedHashSet<Action>());
                        tb.setEmailSystemId(emailSystemId);
                        tb.setParentId(parentId);
                        tb.setSensingWeekDay(sensingWeekSun, sensingWeekMon, sensingWeekTue, sensingWeekWed, sensingWeekThu, sensingWeekFri, sensingWeekSat);
                        tb.setAgreement(agreement);
                        tb.setColor(color);
                        tb.setIconUrl(iconUrl);
                        tb.setEnabled(enabled);
                        tb.setWpPublishPage(wpPublishPage);
                        tb.setWpPostDescription(wpPostDescription);
                        tb.setWpPostSensorList(wpPostSensorList);
                        tb.setWpPostCampaignStats(wpPostCampaignStats);
                        tb.setWpPublishText(wpPublishText);
                        tb.setWpPostQuestionnaire(wpPostQuestionnaire);

                        Task newTask = tb.build(true);
                        // Save
                        rs = taskService.save(newTask);
                    }
                } else {// Update
                    Task updateTask = taskService.findById(id);
                    if (updateTask != null && (!updateTask.isPublish() || isAdmin || (isAgreement && updateTask.getParentId() == userId))) {
                        updateTask.setName(name);
                        updateTask.setDescription(description);
                        if (isAdmin) {
                            updateTask.setCanBeRefused(canBeRefused);
                        }
                        updateTask.setStart(Useful.converteStringToDate(start));
                        updateTask.setDeadline(Useful.converteStringToDate(deadline));
                        updateTask.setIsDuration(isDuration);
                        updateTask.setDuration(duration);
                        updateTask.setSensingDuration(sensingDuration);
                        updateTask.setSendEmail(isSendEmail);
                        updateTask.setEmailSubject(emailSubject);
                        updateTask.setEmailBody(emailBody);
                        updateTask.setEmailSystemId(emailSystemId);
                        updateTask.setSensingWeekDay(sensingWeekSun, sensingWeekMon, sensingWeekTue, sensingWeekWed, sensingWeekThu, sensingWeekFri, sensingWeekSat);
                        updateTask.setAgreement(agreement);
                        updateTask.setColor(color);
                        updateTask.setIconUrl(iconUrl);
                        updateTask.setEnabled(enabled);

                        updateTask.setWpPublishPage(wpPublishPage);
                        updateTask.setWpPostDescription(wpPostDescription);
                        updateTask.setWpPostSensorList(wpPostSensorList);
                        updateTask.setWpPostCampaignStats(wpPostCampaignStats);
                        updateTask.setWpPublishText(wpPublishText);
                        updateTask.setWpPostQuestionnaire(wpPostQuestionnaire);
                        // Update
                        rs = taskService.save(updateTask);
                    }

                }

                if (rs != null) {
                    response.setStatus(true);
                    response.setOutcome(rs.getId());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            //e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    private String convertJsDateToJDate(String d, String t) throws ParseException {
        if (Validator.isValidDateFormat(d, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") && Validator.isValidDateFormat(t, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
            Date deDate = Useful.converteStringToDatePattern(d, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date deTime = Useful.converteStringToDatePattern(t, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            d = Useful.converteDateToString(deDate, "yyyy-MM-dd");
            t = Useful.converteDateToString(deTime, "HH:mm:ss");

            return String.format("%s %s", d, t);
        }
        return null;
    }

    /**
     * @param json
     * @param count
     * @param offset
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset, HttpServletRequest request) {
        // Is
        isRoot(request);
        // Response
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        boolean isCloudDownload = false;
        boolean isAdvancedSearch = false;
        boolean orderByDesc = false;
        String orderByColumn = "Status";
        // Request
        try {
            ReceiveJson r = new ReceiveJson(json);
            isAdvancedSearch = r.getAsBoolean("isAdvancedSearch");
            isCloudDownload = r.getAsBoolean("isCloudDownload");
            orderByDesc = r.getAsBoolean("orderByDesc");
            orderByColumn = r.getAsString("orderByColumn");
            if (isAdvancedSearch) {
                // Json
                ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
                params = advancedQueryParameters(res, params);
            } else {
                String name = r.getAsString("name");
                if (!Validator.isEmptyString(name)) {
                    params.put("name", name);
                }
                String description = r.getAsString("description");
                if (!Validator.isEmptyString(description)) {
                    params.put("description", description);
                }

                if (isAdmin) {
                    String parentId = r.getAsString("parentId");
                    if (Validator.isValidNumeric(parentId)) {
                        params.put("parentId", Long.parseLong(parentId));
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        // Is User
        System.out.println(parentId);
        if (parentId > 0) {
            params.put("parentId", parentId);
            if (!isAdmin && !isResearcher && !isAgreement && !isOmbudsman) {
                params.put("isPublish", true);
            }
        }
        // Busca
        List<Object[]> items = campaignService.search(params, orderByColumn, orderByDesc, PaginationUtil.pagerequest(offset, count));
        response.setItems(items);
        if (items.size() > 0) {
            for (Object[] item : items) {
                Account owner = accountService.findById(((Number) item[17]).longValue());
                item[17] = owner != null ? owner.getName() : "";
            }
            response.setTotal(campaignService.searchTotal(params));
        }
        // Para download?
        if (isCloudDownload) {
            if (items.size() > 0) {
                response.setResultType(ResultType.TYPE_INFO);
                response.setMessage(messageSource.getMessage("confirmation.exporting", null, LocaleContextHolder.getLocale()));
                createTaskCsvFile(params);
            } else {
                response.setResultType(ResultType.TYPE_ERROR);
                response.setMessage(messageSource.getMessage("download.error.data", null, LocaleContextHolder.getLocale()));
            }
        }
        // Return
        return response;
    }

    /**
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign/export", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
    public @ResponseBody
    ResponseJson export(@RequestBody String json, HttpServletRequest request) {
        isRoot(request);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        response.setStatus(true);
        boolean isGpsLocation = false;
        try {
            // Request
            ReceiveJson r = new ReceiveJson(json);
            // Json
            ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
            params = advancedQueryParameters(res, params);
            isGpsLocation = r.has("isGpsLocation") ? r.getAsBoolean("isGpsLocation") : false;
            System.out.println(params.toString());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        if (params.size() > 0) {
            // Is User
            if (parentId > 0 && !isGpsLocation) {
                params.put("parentId", parentId);
                if (!isAdmin && !isResearcher) {
                    params.put("isPublish", true);
                }
            }
            // Search
            response.setResultType(ResultType.TYPE_INFO);
            response.setMessage(messageSource.getMessage("confirmation.exporting", null, LocaleContextHolder.getLocale()));
            if (isGpsLocation) {
                createGpsCsvFile();
            } else {
                if (isAgreement) {
                    createPrettyCSV(params);
                } else {
                    createTaskCsvFile(params);
                }
            }
        } else {
            response.setResultType(ResultType.TYPE_ERROR);
            response.setMessage(messageSource.getMessage("download.error.data", null, LocaleContextHolder.getLocale()));
        }
        // Return
        return response;
    }

    /**
     * @param res
     * @param params
     * @return
     */
    protected ListMultimap<String, Object> advancedQueryParameters(ReceiveAdvancedSearch[] res, ListMultimap<String, Object> params) {
        if (res != null && res.length > 0) {
            String[] haystack = {"FILTER_CANBEREFUSED", "FILTER_HAS_PHOTO", "FILTER_HAS_QUESTION"};
            // Loop validacao
            for (ReceiveAdvancedSearch item : res) {
                // Validacao de existencia
                if (EnumUtils.isValidEnum(FilterByTask.class, item.getKey())) {
                    String name = FilterByTask.valueOf(item.getKey()).toString();
                    Object value = null;
                    boolean isOk = false;
                    // Verificando itens enviados
                    if (item.getKey().equals(FilterByTask.FILTER_START.name()) || item.getKey().equals(FilterByTask.FILTER_DEADLINE.name())) {
                        // date string
                        String dt = item.getValue();
                        // Object
                        if (!Validator.isEmptyString(dt) && Validator.isValidDateFormat(dt, "dd/MM/yyyy")) {
                            String str = Useful.getDateUSFromBR(dt).toString();
                            if (!Validator.isEmptyString(str) && Validator.isValidDate(str)) {
                                params.put(name, str);
                                isOk = true;
                            }
                        }
                    } else if (item.getKey().equals(FilterByTask.FILTER_PIPELINE_TYPE.name()) && EnumUtils.isValidEnum(Pipeline.Type.class, item.getItem())) {
                        Pipeline.Type enumType = Pipeline.Type.valueOf(item.getItem());
                        params.put(name, enumType.toInt());
                        isOk = true;
                    } else if (Validator.isStringEquals(FilterByTask.FILTER_TASK_ID.name(), item.getKey())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }

                    } else if (Validator.isStringEquals(FilterByTask.FILTER_FIXED_ID.name(), item.getKey())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num == 0) {
                            params.put(name, num);
                            isOk = true;
                        }

                    } else if (Validator.isStringEquals(FilterByTask.FILTER_PARENT_ID.name(), item.getKey())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }
                    } else if (Validator.isValueinArray(haystack, item.getKey())) {
                        params.put(name, item.getItem().equals("YES"));
                        isOk = true;
                    } else if (!Validator.isEmptyString(item.getValue())) {
                        item.setItem(item.getValue());
                        value = item.getValue();
                        params.put(name, value);
                        isOk = true;
                    }

                    if (!isOk) {
                        // feedback para o usuario
                        item.setLabel("danger");
                    }
                }
            }
        }
        // Feedback
        response.setItem(res);
        // Return
        return params;
    }

    /**
     * @param params
     */
    @Async
    private void createTaskCsvFile(ListMultimap<String, Object> params) {
        // CSV
        List<Task> items = campaignService.searchList(params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
        fileExportService.createTaskCsvFile(items, userId);
    }

    @Async
    private void createPrettyCSV(ListMultimap<String, Object> params) {
        // CSV
        fileExportService.createPrettyCSV(params, userId);
    }

    /**
     *
     */
    @Async
    private void createGpsCsvFile() {
        fileExportService.createGpsCsvFile(userId);
    }
}