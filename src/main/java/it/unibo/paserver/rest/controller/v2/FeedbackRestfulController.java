package it.unibo.paserver.rest.controller.v2;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.FeedbackReport;
import it.unibo.paserver.domain.FeedbackType;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJsonRest;
import it.unibo.paserver.domain.support.FeedbackReportBuilder;
import it.unibo.paserver.service.FeedbackReportService;
import it.unibo.paserver.service.FeedbackTypeService;

/**
 * Feedback do Aplicativo
 *
 * @author Claudio
 */
@RestController
public class FeedbackRestfulController extends ApplicationRestfulController {
    @Autowired
    private FeedbackTypeService feedbackTypeService;
    @Autowired
    private FeedbackReportService feedbackReportService;

    /**
     * Tipos de Feedback
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/feedback-type"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest types(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
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
        // Version
        String version = r.getAsString("version");
        DateTime updateDate = new DateTime().minusYears(1);
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
        }
        response.setVersion(new DateTime().minusHours(3));
        // Auxiliares
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // 2019-10-07 : Claudionor
        // Removendo o version para retornar todas as categorias
        //params.put("updateDate", updateDate);
        try {
            //CATALINA.OUT JSON
            System.out.println(json.toString());

            List<FeedbackType> result = feedbackTypeService.filter(params, PaginationUtil.pagerequest(offset, count));
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (result.size() > 0) {
                response.setItem(result);
                response.setCount(result.size());
            } else {
                response.setItem(new ArrayList<>());
                response.setCount(0);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Enviando um Feedback
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/feedback/submit"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest submit(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        String comment = r.getAsString("comment");
        Long feedbackTypeId = r.getAsLong("feedbackTypeId");
        try {
            //CATALINA.OUT JSON
            System.out.println(json.toString());

            if (feedbackTypeId <= 0) {
                throw new Exception(messageSource.getMessage("feedback.type.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else {
                FeedbackType ft = feedbackTypeService.findById(feedbackTypeId);
                if (ft != null) {
                    FeedbackReportBuilder frb = new FeedbackReportBuilder();
                    FeedbackReport fr = frb.setComment(comment).setType(ft).setUser(getUser()).build(true);
                    FeedbackReport result = feedbackReportService.saveOrUpdate(fr);
                    if (result != null) {
                        response.setStatus(true);
                        response.setOutcome(result.getId());
                        response.setMessage(null);
                    }
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }
}
