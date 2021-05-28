package it.unibo.paserver.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.JsonObject;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.IssueSetting;
import it.unibo.paserver.domain.ReceiveAdvancedSearch;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.ResultType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.IssueSettingBuilder;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.IssueSettingService;

/**
 * Campanha Fixa (Relatos)
 * 
 * @author Claudio
 *
 */
@Controller
public class CampaignFixedController extends ApplicationController {
	@Autowired
	private IssueSettingService issueSettingService;
	@Autowired
	DataService dataService;

	ResponseJson response = new ResponseJson();

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-fixed/index", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("/protected/campaign-fixed/index");
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		isRoot(request);
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("isResearcher", isResearcher);
		modelAndView.addObject("isDeveloper", 1854==userId);
		// View
		return modelAndView;
	}

	/**
	 * Formulario Usuario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-fixed/form/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView form(@PathVariable("id") long id, ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("/protected/campaign-fixed/form");
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("isResearcher", isResearcher);
		modelAndView.addObject("taskId", 0);
		modelAndView.addObject("actionId", 0);
		modelAndView.addObject("userId", id);
		// View
		return modelAndView;
	}

	/**
	 * Edicao de um item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-fixed/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
		// Security
		isRoot(request);
		// Check
		isCheck(id);
		// Response
		response.setStatus(false);
		// Search
		IssueSetting item = issueSettingService.findById(id);
		if (item != null) {
			// Task
			response.setStatus(true);
			response.setItem(item);
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
	@RequestMapping(value = { "/protected/campaign-fixed/save/", "/protected/campaign-fixed/edit/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson save(@RequestBody String json) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		response.setResultType(ResultType.TYPE_ERROR);
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		boolean inAppleReview = r.getAsBoolean("inAppleReview");
		boolean isEnabled = r.getAsBoolean("isEnabled");
		String intervalTime = r.getAsString("intervalTime");
		Long timestamp = 11100L;// Default 5min
		// Validate
		try {
			if (Validator.isValidDateFormat(intervalTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
				timestamp = Useful.convertDateToTimestamp(Useful.converteStringToDatePattern(intervalTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).getTime() / 1000L;
			}
			// Exist?
			IssueSetting item = issueSettingService.findById(1L);
			if (item != null) {
				item.setEnabled(isEnabled);
				item.setIntervalTime(timestamp);
				item.setInAppleReview(inAppleReview);
			} else {
				IssueSettingBuilder isb = new IssueSettingBuilder();
				isb.setId(null);
				isb.setIntervalTime(timestamp);
				isb.setEnabled(isEnabled);
				isb.setInAppleReview(inAppleReview);
				// Build
				item = isb.build(true);
			}
			// Save/Update
			IssueSetting rs = issueSettingService.saveOrUpdate(item);
			if (rs != null) {
				response.setStatus(true);
				response.setMessage(messageSource.getMessage("updated.item.title", null, LocaleContextHolder.getLocale()));
				response.setItem(rs);
				response.setResultType(ResultType.TYPE_SUCCESS);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			response.setMessage(e.getMessage());
		}
		// Result
		return response;
	}

	/**
	 * Gps User
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-fixed/location/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson userLocation(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
		// Security
		isRoot(request);
		// Response
		response.setStatus(false);
		// process sensing actions
		Class<? extends Data> clazz = DataLocation.class;
		String className = clazz.getName();
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		ReceiveJson r = new ReceiveJson("{}");
		ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
		params = Useful.getDataQueryParameters(res, params, className);
		// User
		User u = participantService.findById(id);
		if (u != null) {
			Long progenitorId = u.getProgenitorId();
			Long userId = u.getId();
			if (u.isGuest()) {
				params.put("xorProgenitorId", progenitorId.longValue());
			} else {
				params.put("orProgenitorId", userId.longValue());
			}
		}
		// Find
		DateTime s = new DateTime().minusYears(3);
		DateTime e = new DateTime().plusMinutes(10);
		try {
			List<? extends Data> items = dataService.search((Class<? extends Data>) Class.forName(className), s, e, id, params, PaginationUtil.pagerequest(0, 1000));
			response.setItems(Useful.getDataToObject(items, className));
			if (items.size() > 0) {
				response.setCount(1000);
				response.setStatus(true);
				response.setTotal(dataService.searchTotal((Class<? extends Data>) Class.forName(className), s, e, id, params));
			}
		} catch (Exception err) {
			err.printStackTrace(System.out);
		}
		// Return
		return response;
	}
}