package it.unibo.paserver.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.ResultType;
import it.unibo.paserver.domain.TaskUserExcluded;
import it.unibo.paserver.domain.support.TaskUserExcludedBuilder;
import it.unibo.paserver.service.TaskUserExcludedService;

/**
 * Controler da lista de excluidos
 * 
 * @author Claudio
 */
@Controller
public class CampaignTaskExcludedController extends ApplicationController {
	@Autowired
	private TaskUserExcludedService taskUserExcludedService;

	/**
	 * Adiciona ou remove da lista de excluidos
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-user-excluded/set/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson setExcluded(@RequestBody String json, HttpServletRequest request) {
		// Parent ID
		isRoot(request);
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		response.setResultType(ResultType.TYPE_ERROR);
		// Request
		ReceiveJson r = new ReceiveJson(json);
		try {
			// Values
			long campaign_id = r.getAsLong("campaign_id");
			long user_id = r.getAsLong("user_id");
			boolean isExcluded = r.getAsBoolean("excluded");
			boolean isSelectAll = r.getAsBoolean("isSelectAll");

			if (isExcluded) { // Add
				TaskUserExcludedBuilder tub = new TaskUserExcludedBuilder();
				TaskUserExcluded te = taskUserExcludedService
						.saveOrUpdate(tub.setAll(0L, campaign_id, user_id, userId, isSelectAll).build(true));
				if (te != null) {
					response.setMessage(messageSource.getMessage("confirmation.excluded.add", null,
							LocaleContextHolder.getLocale()));
					response.setResultType(ResultType.TYPE_SUCCESS);
				}
			} else {// Remove
				if (taskUserExcludedService.delete(campaign_id, user_id)) {
					response.setResultType(ResultType.TYPE_WARNING);
					response.setMessage(messageSource.getMessage("confirmation.excluded.rm", null,
							LocaleContextHolder.getLocale()));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Result
		return response;
	}

	/**
	 * Lista de excluidos de uma campanha
	 * 
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-user-excluded/list/{id}" }, method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson getExcludedList(@PathVariable("id") long id, HttpServletRequest request) {
		// Is root
		isRoot(request);
		// Response
		ResponseJson response = new ResponseJson();
		List<Object[]> items = taskUserExcludedService.fetchAll(id, parentId);
		if (!items.isEmpty()) {
			response.setStatus(true);
			response.setItems(items);
		}
		// Result
		return response;
	}

}
