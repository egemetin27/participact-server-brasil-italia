package it.unibo.paserver.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.unibo.paserver.domain.MailingHistory;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.service.MailingHistoryService;

/**
 * Controller dos historicos de textos para os emails
 * 
 * @author Claudio
 */
@Controller
public class MailingHistoryController extends ApplicationController {
	@Autowired
	private MailingHistoryService  mailingHistoryService;
	
	ResponseJson response = new ResponseJson();
	/**
	 * Edicao de um item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/mailing-history/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
		// Response
		response.setStatus(false);
		response.setMessage("N/A");
		// Search
		MailingHistory item = mailingHistoryService.find(id);
		if (item != null) {
			// Text
			response.setStatus(true);
			response.setItem(item);
		}
		// Return
		return response;
	}
}