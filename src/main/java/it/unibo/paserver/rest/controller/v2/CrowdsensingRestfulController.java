package it.unibo.paserver.rest.controller.v2;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.service.CampaignService;
import it.unibo.paserver.service.TaskService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressWarnings("Duplicates")
@RestController
public class CrowdsensingRestfulController extends ApplicationRestfulController {
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected CampaignService campaignService;

    /**
     * Lista de Campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/crowdsensing"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJson search(@RequestBody String json, HttpServletRequest request) throws Exception {
        ResponseJson response = new ResponseJson();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        String search = r.getAsString("search");
        response.setVersion(new DateTime().minusMinutes(10));
        try {
            List<HashMap<String, Object>> payload = new ArrayList<HashMap<String, Object>>();
            // Search
            List<Object[]> wpList = campaignService.searchWpPublishPage(search, PaginationUtil.pagerequest(offset, count));
            if (wpList.size() > 0) {
                // t.id, t.name, t.color, t.iconUrl, t.wpPostDescription, t.description,
                // t.creationDate
                for (Object[] i : wpList) {
                    HashMap<String, Object> h = new HashMap<String, Object>();
                    h.put("id", Long.parseLong((String) i[0].toString()));
                    h.put("name", (String) i[1].toString());
                    h.put("color", (String) i[2].toString());

                    String iconUrl = (String) i[3];
                    h.put("iconUrl", iconUrl);

                    boolean wpPostDescription = Boolean.parseBoolean((String) i[4].toString());
                    h.put("wpPostDescription", wpPostDescription);
                    String description = wpPostDescription ? (String) i[5] : "";
                    h.put("description", description);
                    h.put("creationDate", (String) i[6].toString());
                    payload.add(h);
                }
            }
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (payload.size() > 0) {
                response.setPayloadList(payload);
                response.setCount(payload.size());
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Consultando dados de um item
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/crowdsensing/find"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJson find(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJson response = new ResponseJson();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Params
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Dates
        Long taskId = r.getAsLong("campaign_id");
        try {
            // Check
            if (taskId > 0) {
                // Search
                Task t = campaignService.findById(taskId);
                if (t != null) {
                    if (t.isWpPublishPage()) {
                        Long id = t.getId();
                        String name = t.getName();
                        String color = t.getColor();
                        String description = t.isWpPostDescription() ? t.getDescription() : null;
                        String text = t.getWpPublishText();
                        String author = null;
                        List<String> sensorList = new ArrayList<String>();
                        if (t.isWpPostSensorList()) {
                            Set<Action> actions = t.getActions();
                            if (actions.size() > 0) {
                                for (Action a : actions) {
                                    sensorList.add(messageSource.getMessage("pipeline.type." + Useful.getTranslatedActionType(a), null, LocaleContextHolder.getLocale()));
                                }
                            }
                        }
                        //Questionario
                        List<ActionQuestionaire> questionnaireList = new ArrayList<>();
                        if (t.isWpPostQuestionnaire() || t.isWpPostCampaignStats()) {
                            Set<Action> actions = (Set<Action>) t.getActions();
                            if (actions != null) {
                                for (Action a : actions) {
                                    if (a != null && a instanceof ActionQuestionaire) {
                                        questionnaireList.add((ActionQuestionaire) a);
                                    }
                                }
                            }
                        }
                        //Stats
                        List<Object[]> campaignStats = new ArrayList<Object[]>();
                        if (t.isWpPostCampaignStats()) {
                            //campaignStats = campaignService.getTotalByState(id);
                        }
                        // Chart
                        ArrayList questionnaireChart = new ArrayList();
                        if (t.isWpPostCampaignStats()) {
                            for (ActionQuestionaire a : questionnaireList) {
                                List<Question> questions = a.getQuestions();
                                for (Question q : questions) {
                                    // Closed Question
                                    if (q.getIsClosedAnswers()) {
                                        HashMap<String, Object> asking = new HashMap<>();
                                        asking.put("name", q.getQuestion());
                                        asking.put("order", q.getQuestionOrder());
                                        asking.put("id", q.getId());
                                        ArrayList<HashMap<String, Object>> series = new ArrayList();
                                        for (ClosedAnswer an : q.getClosed_answers()) {
                                            // Begin
                                            // Item
                                            HashMap<String, Object> item = new HashMap<>();
                                            item.put("name", an.getAnswerDescription());
                                            item.put("values", an.getAmount());
                                            // Add
                                            series.add(item);
                                            // End
                                        }
                                        asking.put("series", series);
                                        //Add
                                        questionnaireChart.add(asking);
                                    }
                                }
                            }
                        }
                        //Add
                        HashMap<String, Object> payload = new HashMap<String, Object>();
                        payload.put("id", id);
                        payload.put("name", name);
                        payload.put("color", color);
                        payload.put("description", description);
                        payload.put("text", text);
                        payload.put("author", author);
                        payload.put("sensorList", sensorList);
                        payload.put("campaignStats", campaignStats);
                        payload.put("questionnaireList", t.isWpPostQuestionnaire() ? questionnaireList : new ArrayList());
                        payload.put("questionnaireChart", questionnaireChart);
                        //Res
                        response.setPayload(payload);
                    }
                }
                // Resultado
                response.setStatus(true);
                response.setMessage(null);
            } else {
                throw new Exception("Invalid Report Id");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }
}
