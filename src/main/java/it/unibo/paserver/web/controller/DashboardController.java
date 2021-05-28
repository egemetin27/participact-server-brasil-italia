package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.unibo.paserver.domain.*;
import it.unibo.paserver.service.IssueCategoryService;
import it.unibo.paserver.service.IssueSubCategoryService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import sun.misc.FloatingDecimal;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DashboardController extends ApplicationController {
    @Autowired
    private CampaignController campaignController;
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryService issueSubCategoryService;

    private ResponseJson response = new ResponseJson();

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/protected/dashboard", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", 0L);
        modelAndView.addObject("isDashboard", true);
        return modelAndView;
    }

    /**
     * ########## Campanhas
     */
    /**
     * Pagina inicial
     */
    @RequestMapping(value = "/protected/dashboard/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("/protected/dashboard/index");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", 0L);
        modelAndView.addObject("isDashboard", true);
        return modelAndView;
    }

    /**
     * Pagina inicial com param id
     *
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/dashboard/index/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView indexOfId(@PathVariable("id") long id, ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("/protected/dashboard/index");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", 0L);
        return modelAndView;
    }

    @RequestMapping(value = "/protected/dashboard/statistics", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson statistics(@RequestBody String json, HttpServletRequest request) {
        // IsAdmin
        isRoot(request);
        // Response
        response.setStatus(true);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Json
        ReceiveJson r = new ReceiveJson(json);
        ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
        params = campaignController.advancedQueryParameters(res, params);
        if (!isAdmin) {
            params.put("parentId", parentId);
        }
        // Stats by States
        List<Object[]> states = campaignService.getTotalByStateAndTask(params);
        String[] hayCompleted = {TaskState.COMPLETED_NOT_SYNC_WITH_SERVER.name(), TaskState.COMPLETED_WITH_FAILURE.name(), TaskState.COMPLETED_WITH_SUCCESS.name()};
        String[] hayReject = {TaskState.REJECTED.name(), TaskState.ERROR.name(), TaskState.FAILED.name()};
        String[] hayRunning = {};
        String[] hayAccepted = {TaskState.ACCEPTED.name(), TaskState.RUNNING.name()};
        String[] hayNone = {TaskState.ANY.name(), TaskState.NONE.name(), TaskState.UNKNOWN.name(), TaskState.IGNORED.name()};

        long available = 0L;
        long accepted = 0L;
        long running = 0L;
        long rejected = 0L;
        long completed = 0L;
        long none = 0L;
        long total = 0L;

        for (Object[] s : states) {
            String name = (String) s[0].toString();
            long value = Long.parseLong(s[1].toString());
            if (Validator.isValueinArray(hayCompleted, name)) {
                completed = completed + value;
                accepted = accepted + value;
            } else if (Validator.isValueinArray(hayReject, name)) {
                rejected = rejected + value;
            } else if (Validator.isValueinArray(hayRunning, name)) {
                running = running + value;
            } else if (Validator.isValueinArray(hayAccepted, name)) {
                accepted = accepted + value;
            } else if (Validator.isValueinArray(hayNone, name)) {
                none = none + value;
            } else {
                available = available + value;
            }
            total = total + value;
        }
        HashMap<String, Long> byStateValue = new HashMap<String, Long>();
        byStateValue.put("AVAILABLE", available);
        byStateValue.put("ACCEPTED", accepted);
        //byStateValue.put("RUNNING", running);
        byStateValue.put("REJECTED", rejected);
        byStateValue.put("COMPLETED", completed);
        byStateValue.put("NONE", none);
        byStateValue.put("TOTAL", total);
        //Chart
        List<HashMap<String, Object>> chart = new ArrayList<HashMap<String, Object>>();
        for (Map.Entry<String, Long> entry : byStateValue.entrySet()) {
            if (!entry.getKey().equals("TOTAL")) {
                HashMap<String, Object> m = new HashMap<String, Object>();
                m.put("name", messageSource.getMessage("statistics.state." + entry.getKey().toLowerCase(), null, LocaleContextHolder.getLocale()));
                m.put("data", new Object[]{entry.getValue()});
                m.put("connectNulls", false);
                m.put("id", entry.getKey());
                m.put("color", Useful.getColorValueState(entry.getKey()));
                chart.add(m);
            }
        }
        // Data
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("byState", byStateValue);
        map.put("chartColumn", chart);
        map.put("chartColumnyAxis", messageSource.getMessage("statistics.bystate.yaxis", null, LocaleContextHolder.getLocale()));
        map.put("chartColumnxAxis", messageSource.getMessage("statistics.bystate.xaxis", null, LocaleContextHolder.getLocale()));
        //Set
        response.setChart(map);
        // Result
        return response;
    }

    /**
     * ########## Relatos/Issues/Categories
     */
    /**
     * Pagina inicial
     */
    @RequestMapping(value = "/protected/dashboard/category", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView category(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("/protected/dashboard/category");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", 0L);
        modelAndView.addObject("isDashboard", true);
        return modelAndView;
    }


    @RequestMapping(value = "/protected/dashboard/statistics/categories", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson categories(@RequestBody String json, HttpServletRequest request) {
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Json
        ReceiveJson r = new ReceiveJson(json);
        // Search
        List<Object[]> res = issueCategoryService.getTotalByCategory();
        if (res != null && res.size() > 0) {
            // Map
            HashMap<String, Object> map = new HashMap<String, Object>();
            // ### CATEGORIES
            // BEGIN
            List<IssueCategory> categories = issueCategoryService.findAll();
            map.put("categories", categories);
            // Dates
            DateTime now = new DateTime();
            int count = 0;
            int max = 24;
            ArrayList<HashMap<String, Object>> multiple = new ArrayList<>();
            while (max >= 0) {
                DateTime minus = now.minusMonths(max);
                //dates.add(String.format("%d-%02d", minus.getYear(), minus.getMonthOfYear()));
                HashMap<String, Object> item = new HashMap<>();
                //item.put("date", String.format("new Date(%d, 0, %d)", minus.getYear(), minus.getMonthOfYear()));
                item.put("date", String.format("%d-%02d", minus.getYear(), minus.getMonthOfYear()));
                //Begin
                for (IssueCategory ic : categories) {
                    Long amount = issueCategoryService.getTotalByCategoryDatePart(ic.getId(), minus.getYear(), minus.getMonthOfYear());
                    item.put(String.format("value%d", ic.getId()), amount);
                }
                multiple.add(item);
                //End
                max--;
            }
            System.out.println(multiple.toString());
            map.put("timeline", multiple);
            // Chart
            ArrayList<HashMap<String, Object>> series = new ArrayList();
            for (Object[] o : res) {
                long id = Long.parseLong(o[0].toString());
                String name = (String) o[1].toString();
                Float value = Float.parseFloat(o[2].toString());
                HashMap<String, Object> item = new HashMap<>();
                item.put("name", name);
                item.put("values", value);
                String color = Useful.getRandomRGB();
                //Get
                for (IssueCategory ic : categories) {
                    if (ic.getId().longValue() == id) {
                        color = ic.getColor();
                    }
                }
                //Check
                if (!color.contains("#")) {
                    color = String.format("#%s", color);
                }
                item.put("color", color);
                //Add
                series.add(item);
            }
            // Data
            map.put("byCategory", series);
            // END

            // ### SUB CATEGORIES
            // BEGIN
            res = issueSubCategoryService.getTotalBySubCategory();
            // Chart
            ArrayList<HashMap<String, Object>> is = new ArrayList();
            for (Object[] o : res) {
                String name = (String) o[1].toString();
                Float value = Float.parseFloat(o[2].toString());
                HashMap<String, Object> item = new HashMap<>();
                item.put("name", name);
                item.put("values", value);
                //Add
                is.add(item);
            }
            // Data
            map.put("bySubCategory", is);
            // END
            //Set
            response.setChart(map);
            // Response
            response.setStatus(true);
        }
        // Result
        return response;
    }
}
