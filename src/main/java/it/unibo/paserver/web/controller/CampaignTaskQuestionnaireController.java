package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.service.DataSetEdgeService;
import it.unibo.paserver.service.DataSetNodeService;
import it.unibo.paserver.service.QuestionnaireResponseService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller dos questionarios
 *
 * @author Claudio
 */
@Controller
public class CampaignTaskQuestionnaireController extends ApplicationController {
    protected ArrayList<Question> questions = null;
    @Autowired
    private QuestionnaireResponseService questionnaireResponseService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DataSetEdgeService dataSetEdgeService;
    @Autowired
    private DataSetNodeService dataSetNodeService;

    /**
     * Formulario Novo
     *
     * @param campaign_id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task-questionnaire/form/{campaign_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(@PathVariable("campaign_id") long campaign_id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/campaign-task-questionnaire/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("campaign_id", campaign_id);
        modelAndView.addObject("question_id", 0L);
        return modelAndView;
    }

    /**
     * Formulario Edicao
     *
     * @param campaign_id
     * @param action_id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task-questionnaire/form/{campaign_id}/{action_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView edit(@PathVariable("campaign_id") long campaign_id, @PathVariable("action_id") long action_id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/campaign-task-questionnaire/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("campaign_id", campaign_id);
        modelAndView.addObject("question_id", action_id);
        return modelAndView;
    }

    /**
     * @param campaign_id
     * @param action_id
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task-questionnaire/form/{campaign_id}/{action_id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("campaign_id") long campaign_id, @PathVariable("action_id") long action_id) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));

        Action action = actionService.findById(action_id);
        if (action != null && action instanceof ActionQuestionaire) {
            // Questionaire
            ActionQuestionaire questionaire = (ActionQuestionaire) action;
            // Hashmap
            HashMap<String, Object> payload = new HashMap<String, Object>();
            payload.put("campaign_id", campaign_id);
            payload.put("description", questionaire.getDescription());
            payload.put("title", questionaire.getName());
            payload.put("question_id", questionaire.getId());
            payload.put("isrepeat", questionaire.getRepeat());
            int index = 0;
            // Questions
            ArrayList<HashMap<String, Object>> questions = new ArrayList<HashMap<String, Object>>();
            for (Question q : questionaire.getQuestions()) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("question", q.getQuestion());
                item.put("order", q.getQuestionOrder());
                item.put("id", q.getId());
                item.put("isrequired", q.getRequired());
                item.put("isphoto", q.getPhoto());
                item.put("isdate", q.getIsDate());
                item.put("isschoolsfromgps", q.getSchoolsFromGPS());
                item.put("number_photos", q.getNumberPhotos());
                item.put("target_id", q.getTargetId());
                item.put("target_order", String.format("%s", q.getTargetOrder()));

                ArrayList<HashMap<String, Object>> contest_options = new ArrayList<HashMap<String, Object>>();
                if (!q.getIsClosedAnswers()) {
                    String type_name = QuestionType.OPENQUESTION.name();
                    if (q.getPhoto()) {
                        type_name = QuestionType.PHOTOQUESTION.name();
                    } else if (q.getIsDate()) {
                        type_name = QuestionType.DATEQUESTION.name();
                    } else if (q.getSchoolsFromGPS()) {
                        type_name = QuestionType.SCHOOLSFROMGPSQUESTION.name();
                    }
                    item.put("contest_type_id", type_name);
                } else {
                    item.put("contest_type_id", q.getIsMultipleAnswers() ? QuestionType.CLOSEDQUESTIONMUL.name() : QuestionType.CLOSEDQUESTIONSIN.name());
                    for (ClosedAnswer opt : q.getClosed_answers()) {
                        HashMap<String, Object> alternatives = new HashMap<String, Object>();
                        alternatives.put("id", opt.getId());
                        alternatives.put("order", opt.getAnswerOrder());
                        alternatives.put("option", opt.getAnswerDescription());
                        alternatives.put("is_correct", false);
                        alternatives.put("target_id", opt.getTargetId());
                        alternatives.put("target_order", String.format("%s", opt.getTargetOrder()));

                        contest_options.add(alternatives);
                    }
                }
                // Options
                item.put("contest_options", contest_options);
                // Add
                questions.add(index++, item);
            }
            payload.put("questions", questions);
            response.setPayload(payload);
            response.setStatus(true);
        }
        // Return
        return response;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/campaign-task-questionnaire/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json, HttpServletRequest request) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        long campaign_id = r.getAsLong("campaign_id");
        long question_id = r.getAsLong("question_id");
        // Check
        isCheck(campaign_id);
        // Security
        isRoot(request);
        //Validate
        if (!isPublish || isAdmin || (isAgreement && hasTask != null && hasTask.getParentId() == userId)) {
            String name = r.getAsString("title");
            boolean isRepeat = r.getAsBoolean("isrepeat");
            String description = r.getAsString("description");
            Map<Integer, JsonObject> questions = r.getAsJsonMap("questions");
            // Pre validacao
            if (Validator.isEmptyString(name)) {
                response.setMessage(messageSource.getMessage("name.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else if (questions.size() < 1) {
                response.setMessage(messageSource.getMessage("error.question.required", null, LocaleContextHolder.getLocale()));
            } else {
                // Validate Question
                List<Question> ofQuestions = new ArrayList<>();
                List<Question> fromQuestions = new ArrayList<>();
                List<ClosedAnswer> ofClosedAnswer = new ArrayList<>();
                List<DataSetNode> nodes = new ArrayList<>();
                List<DataSetEdge> edges = new ArrayList<>();
                boolean isUpdate = false;
                boolean isAdd = true;
                int currentIndex = 0;
                int addIndex = 0;
                // Acquire
                ActionQuestionaire a = new ActionQuestionaire();
                if (question_id > 0) {
                    Action b = actionService.findById(question_id);
                    if (b != null && b instanceof ActionQuestionaire) {
                        a = (ActionQuestionaire) b;
                        fromQuestions = a.getQuestions();
                    }
                }
                a.setTitle(name);
                a.setName(name);
                a.setDescription(description);
                a.setRepeat(isRepeat);


                try {
                    int index = 0;
                    for (Integer key : questions.keySet()) {
                        JsonObject value = questions.get(key);
                        if (value.has("contest_type_id") && value.has("question") && value.has("contest_options")) {
                            Long qId = value.has("id") ? value.get("id").getAsLong() : null;
                            String typeId = value.get("contest_type_id").getAsString();
                            String question = value.get("question").getAsString();
                            Boolean isRequired = value.has("isrequired") ? value.get("isrequired").getAsBoolean() : false;
                            Integer numberPhotos = value.has("number_photos") ? value.get("number_photos").getAsInt() : 1;
                            if (EnumUtils.isValidEnum(QuestionType.class, typeId) && !Validator.isEmptyString(question)) {
                                QuestionType enumType = QuestionType.valueOf(typeId);
                                Boolean isPhoto = enumType.equals(QuestionType.PHOTOQUESTION);
                                Boolean isText = enumType.equals(QuestionType.OPENQUESTION);
                                Boolean isDate = enumType.equals(QuestionType.DATEQUESTION);
                                Boolean isSchoolsFromGPS = enumType.equals(QuestionType.SCHOOLSFROMGPSQUESTION);
                                nodes.add(index, new DataSetNode(0L, index, question));

                                Question x = new Question();
                                isUpdate = false;
                                currentIndex = 0;
                                if (qId != null && qId > 0) {
                                    for (int i = 0; i < fromQuestions.size(); i++) {
                                        Question y = fromQuestions.get(i);
                                        if (y.getId().longValue() == qId.longValue()) {
                                            x = y;
                                            isUpdate = true;
                                            currentIndex = i;
                                            break;
                                        }
                                    }
                                }


                                if (isText || isPhoto || isDate || isSchoolsFromGPS) {
                                    //Edge
                                    int targetOrder = value.has("target_order") ? value.get("target_order").getAsInt() : -1;
                                    targetOrder = targetOrder != index ? targetOrder : -1;//Ele nao pode ir para ele mesmo
                                    edges.add(new DataSetEdge(index, targetOrder, question, true, -1));
                                    //sET
                                    x.setIsClosedAnswers(false);
                                    x.setIsMultipleAnswers(false);
                                    x.setQuestion(question);
                                    x.setQuestionOrder(index);
                                    x.setRequired(isRequired);
                                    x.setPhoto(isPhoto);
                                    x.setDate(isDate);
                                    x.setSchoolsFromGPS(isSchoolsFromGPS);
                                    x.setNumberPhotos(isPhoto ? numberPhotos : 1);
                                    x.setTargetId(0L);
                                    x.setTargetOrder(targetOrder);
                                    // Set
                                    ofQuestions.add(x);
                                    if (isUpdate) {
                                        fromQuestions.set(currentIndex, x);
                                    } else {
                                        fromQuestions.add(x);
                                    }
                                    // Increment
                                    index++;
                                } else {
                                    if (value.has("contest_options")) {
                                        x.setIsClosedAnswers(true);
                                        x.setIsMultipleAnswers(enumType.equals(QuestionType.CLOSEDQUESTIONMUL));
                                        x.setQuestion(question);
                                        x.setQuestionOrder(index);
                                        x.setRequired(isRequired);
                                        x.setPhoto(false);
                                        x.setDate(false);
                                        x.setSchoolsFromGPS(false);
                                        x.setNumberPhotos(0);
                                        x.setTargetId(0L);
                                        x.setTargetOrder(-1);

                                        JsonArray options = value.get("contest_options").getAsJsonArray();
                                        int sheet = 0;
                                        for (JsonElement elm : options) {
                                            if (elm.isJsonObject()) {
                                                JsonObject ll = elm.getAsJsonObject();
                                                if (ll.has("option") && !Validator.isEmptyString(ll.get("option").getAsString())) {
                                                    Long oId = ll.has("id") ? ll.get("id").getAsLong() : 0L;
                                                    String opt = ll.get("option").getAsString();
                                                    String ion = !Validator.isEmptyString(opt) ? opt : question + " " + index;

                                                    int targetOrder = ll.has("target_order") ? ll.get("target_order").getAsInt() : -1;
                                                    targetOrder = targetOrder != index ? targetOrder : -1;//Ele nao pode ir para ele mesmo
                                                    // Options
                                                    ClosedAnswer e = new ClosedAnswer();
                                                    isAdd = true;
                                                    if (isUpdate) {
                                                        for (int z = 0; z < x.getClosed_answers().size(); z++) {
                                                            ClosedAnswer c = x.getClosed_answers().get(z);
                                                            if (c.getId() != null && c.getId().longValue() == oId.longValue()) {
                                                                e = c;
                                                                isAdd = false;
                                                                addIndex = z;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    e.setAnswerDescription(ion);
                                                    e.setAnswerOrder(sheet);
                                                    e.setQuestion(x);
                                                    e.setTargetOrder(targetOrder);
                                                    if (isAdd) {
                                                        x.getClosed_answers().add(e);
                                                    } else {
                                                        x.getClosed_answers().set(addIndex, e);
                                                    }
                                                    //Lista Auxiliar
                                                    ofClosedAnswer.add(e);
                                                    //Edge
                                                    DataSetEdge edge = new DataSetEdge(index, targetOrder, opt, false, sheet);
                                                    edges.add(edge);
                                                    sheet++;
                                                }
                                            }
                                        }
                                        //Removed
                                        boolean isRemoved = true;
                                        for (int i = 0; i < x.getClosed_answers().size(); i++) {
                                            Long currentId = x.getClosed_answers().get(i).getId();
                                            if (currentId != null && currentId.longValue() > 0) {
                                                isRemoved = true;
                                                for (ClosedAnswer of : ofClosedAnswer) {
                                                    if (of.getId() != null && of.getId().longValue() > 0L && of.getId().longValue() == currentId.longValue()) {
                                                        isRemoved = false;
                                                    }
                                                }
                                                if (isRemoved) {
                                                    x.getClosed_answers().remove(i);
                                                }
                                            }

                                        }
                                        // Set
                                        ofQuestions.add(x);
                                        if (isUpdate) {
                                            fromQuestions.set(currentIndex, x);
                                        } else {
                                            fromQuestions.add(x);
                                        }
                                        // Increment
                                        index++;
                                    }
                                }
                            }
                        }
                    }
                    // Save
                    if (index > 0) {
                        // Campaign
                        Task task = campaignService.findById(campaign_id);
                        if (task != null) {
                            //Questionaro
                            boolean isRemoved = true;
                            for (int i = 0; i < fromQuestions.size(); i++) {
                                Long currentId = fromQuestions.get(i).getId();
                                if (currentId != null && currentId.longValue() > 0) {
                                    isRemoved = true;
                                    for (Question of : ofQuestions) {
                                        if (of.getId() != null && of.getId().longValue() > 0L && of.getId().longValue() == currentId.longValue()) {
                                            isRemoved = false;
                                        }
                                    }

                                    if (isRemoved) {
                                        fromQuestions.remove(i);
                                    }
                                }

                            }
                            a.setQuestions(fromQuestions);
                            // Actions
                            Set<Action> as = task.getActions();
                            // Save Action
                            Action rs = actionService.save(a);
                            if (rs != null) {
                                as.add(a);
                                // Add Actions
                                task.setActions(as);
                                task.setHasQuestionnaire(true);
                                if (question_id == 0) {
                                    taskService.save(task);
                                }
                                // status
                                response.setStatus(true);
                            }
                            //Sync TargetId/TargetOrder
                            this.setDirectTargetId(campaign_id);
                        }
                    } else {
                        response.setMessage(messageSource.getMessage("error.question.invalid", null, LocaleContextHolder.getLocale()));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    response.setMessage(messageSource.getMessage("error.question.invalid", null, LocaleContextHolder.getLocale()));
                    System.out.println(String.format("%s", e.getMessage()));
                    e.printStackTrace(System.out);
                }
            }
        } else if (isPublish && isAgreement) {
            response.setMessage(messageSource.getMessage("error.no.permission", null, LocaleContextHolder.getLocale()));
        }

        // Result
        return response;
    }

    /**
     * @param edge
     * @param nFrom
     * @return
     */
    private DataSetEdge getLeafInHaystack(DataSetEdge edge, DataSetNode nFrom) {
        if (!edge.getDegree()) {
            List<Map<Long, Integer>> leaves = nFrom.getLeaves();
            for (Map<Long, Integer> l : leaves) {
                System.out.println(String.format(" ||| %s", l.toString()));
                if (l.containsValue(edge.getLeafOrder())) {
                    for (Map.Entry<Long, Integer> entry : l.entrySet()) {
                        Long leafId = entry.getKey();
                        Integer leafOrder = entry.getValue();
                        if (edge.getLeafOrder() == leafOrder) {
                            edge.setLeafId(leafId);
                        }
                    }
                }
            }
        }
        edge.setLinkedId(nFrom.getLinkedId());
        edge.setFromId(nFrom.getRefId());
        edge.setFromOrder(nFrom.getOrder());

        return edge;
    }

    protected void setDirectTargetId(Long taskId) {
        Task t = taskService.findById(taskId);
        if (t != null) {
            Set<Action> actions = t.getActions();
            if (actions.size() > 0) {
                //Actions
                for (Action a : actions) {
                    if (a instanceof ActionQuestionaire) {
                        ActionQuestionaire qn = ((ActionQuestionaire) a);
                        // Perguntas
                        List<Question> listQuestion = qn.getQuestions();
                        for (Question q : listQuestion) {
                            if (q.getIsClosedAnswers()) {
                                List<ClosedAnswer> listClosed = q.getClosed_answers();
                                // Opcoes
                                for (ClosedAnswer opt : listClosed) {
                                    if (opt.getTargetOrder() >= 0) {
                                        for (Question comparator : listQuestion) {
                                            if (comparator.getQuestionOrder() == opt.getTargetOrder()) {
                                                opt.setTargetId(comparator.getId());
                                            }
                                        }
                                    }
                                }
                            } else if (!q.getIsClosedAnswers() && !q.getIsMultipleAnswers()) {
                                if (q.getTargetOrder() >= 0) {
                                    for (Question comparator : listQuestion) {
                                        if (comparator.getQuestionOrder() == q.getTargetOrder()) {
                                            q.setTargetId(comparator.getId());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Save
            taskService.save(t);
        }
    }

    /**
     * Respostas de um questionario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task-questionnaire-responses/index/{campaign_id}/{action_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_COOPERATION_AGREEMENT', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(@PathVariable("campaign_id") long campaign_id, @PathVariable("action_id") long action_id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/campaign-task-questionnaire-responses/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("campaign_id", campaign_id);
        modelAndView.addObject("action_id", action_id);
        // Questions
        this.initQuestionnaire(action_id);
        modelAndView.addObject("questions", questions);
        // Resp
        return modelAndView;
    }

    @RequestMapping(value = "/protected/campaign-task-questionnaire-responses/search", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson getListResponses(@RequestBody String json) {
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        int count = r.getAsInt("count");
        int offset = r.getAsInt("offset");

        long campaign_id = r.getAsLong("campaign_id");
        long action_id = r.getAsLong("action_id");
        long user_id = r.getAsLong("user_id");
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        if (questions == null) {
            this.initQuestionnaire(action_id);
        }
        // Rquest
        List<Object[]> items = questionnaireResponseService.search(campaign_id, action_id, user_id, PaginationUtil.pagerequest(Config.SELECT_DEFAULT_OFFSET, Config.SELECT_MAX_COUNT));
        response.setItems(items);
        if (items.size() > 0) {
            //
            /**
             * LEVEL : 0 USERID -> 1 DATA -> 2 QUESTIONID -> 3 RESPOSTA -> 4 RESULTADO
             * Payload/Response
             */
            HashMap<String, Object> level0 = new HashMap<>();
            // Usuario
            for (Object[] i : items) {
                Long rId = Long.parseLong(i[0].toString());
                String userId = i[6].toString();
                Long questionId = Long.parseLong(i[3].toString());
                String answer = Validator.isEmptyString((String) i[7]) ? " " : i[7].toString();
                String name = i[8].toString();
                String surname = i[9].toString();
                String email = i[10].toString();

                Boolean isPhoto = Boolean.parseBoolean(i[11].toString());
                String date = i[12].toString();
                String accuracy = i[13].toString();
                String latitude = i[14].toString();
                String longitude = i[15].toString();
                String altitude = i[16].toString();
                String provider = i[17].toString();
                String ipaddress = Validator.isEmptyString((String) i[18]) ? "N/A" : i[18].toString();

                //  Level 0
                if (!level0.containsKey(userId)) {
                    HashMap<String, Object> user = new HashMap<>();
                    // User info
                    user.put("userId", userId);
                    user.put("name", name);
                    user.put("surname", surname);
                    user.put("email", email);
                    user.put("dates", new ArrayList<String>());
                    // Add
                    level0.put(userId, user);
                }
                //  Level 1
                HashMap<String, Object> level1 = (HashMap<String, Object>) level0.get(userId);
                // Check
                if (!level1.containsKey(date)) {
                    //  Level 2
                    HashMap<Long, Object> x = new HashMap<>();
                    for (Question q : questions) {
                        //Questoes
                        x.put(q.getId(), new HashMap<Long, HashMap<String, Object>>());
                    }
                    //Datas
                    level1.put(date, x);
                    ArrayList<String> dates = (ArrayList<String>) level1.get("dates");
                    dates.add(date);
                }
                //  Level 2
                HashMap<Long, Object> level2 = (HashMap<Long, Object>) level1.get(date);
                //  Level 3
                HashMap<Long, HashMap<String, Object>> level3 = (HashMap<Long, HashMap<String, Object>>) level2.get(questionId);
                if (level3 != null) {
                    //  Level 4
                    HashMap<String, Object> level4 = new HashMap<>();
                    level4.put("isPhoto", isPhoto);
                    level4.put("accuracy", accuracy);
                    level4.put("latitude", latitude);
                    level4.put("longitude", longitude);
                    level4.put("altitude", altitude);
                    level4.put("provider", provider);
                    level4.put("ipaddress", ipaddress);
                    level4.put("answer", answer);
                    level3.put(rId, level4);
                }
            }
            // Result
            response.setPayload(level0);
            // Total
            //response.setTotal(Useful.roundUp(questionnaireResponseService.searchTotal(campaign_id, action_id), (long) questions.size()));
        }
        // response
        return response;
    }

    /**
     * Inicializa o questionario
     *
     * @param ActionId
     */
    protected void initQuestionnaire(Long ActionId) {
        // Action
        Action action = actionService.findById(ActionId);
        questions = new ArrayList<Question>();
        if (action != null && action instanceof ActionQuestionaire) {
            // Questionaire
            ActionQuestionaire questionaire = (ActionQuestionaire) action;
            int index = 0;
            // Questions
            for (Question q : questionaire.getQuestions()) {
                questions.add(index++, q);
            }
        }
    }

    @RequestMapping(value = "/protected/campaign-task-questionnaire-chart/index/{campaign_id}/{action_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_COOPERATION_AGREEMENT', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView chart(@PathVariable("campaign_id") long campaign_id, @PathVariable("action_id") long action_id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/campaign-task-questionnaire-chart/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("campaign_id", campaign_id);
        modelAndView.addObject("action_id", action_id);
        // Questions
        this.initQuestionnaire(action_id);
        modelAndView.addObject("questions", questions);
        // Resp
        return modelAndView;
    }

    @RequestMapping(value = "/protected/campaign-task-questionnaire-chart/series/{question_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_COOPERATION_AGREEMENT', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson series(@PathVariable("question_id") long question_id) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Chart
        ArrayList<HashMap<String, Object>> series = new ArrayList();
        // Question
        for (Question q : questions) {
            if (q.getId() == question_id) {
                // Closed Question
                if (q.getIsClosedAnswers()) {
                    for (ClosedAnswer r : q.getClosed_answers()) {
                        // Begin
                        // Item
                        HashMap<String, Object> item = new HashMap<>();
                        item.put("name", r.getAnswerDescription());
                        item.put("values", r.getAmount());
                        // Add
                        series.add(item);
                        // End
                    }
                    response.setStatus(true);
                } else if (q.getPhoto()) {
                    List<QuestionnaireResponse> res = questionnaireResponseService.search(q.getId(), PaginationUtil.pagerequest(Config.SELECT_DEFAULT_OFFSET, 1000));
                    if (res.size() > 0) {
                        for (QuestionnaireResponse r : res) {
                            // Item
                            HashMap<String, Object> item = new HashMap<>();
                            item.put("src", r.getResponse());
                            // Add
                            series.add(item);
                        }
                        // Set
                        response.setItem(res);
                        response.setStatus(true);
                    }
                }
                break;
            }
        }
        // Set
        response.setPayloadList(series);
        // Return
        return response;
    }
}