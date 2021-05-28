package it.unibo.paserver.web.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.service.MailingHistoryService;
import it.unibo.paserver.service.SystemEmailService;
import it.unibo.paserver.service.TaskPublishService;

@Controller
public class CampaignTaskResend extends ApplicationController {
	@Autowired
	private SystemEmailService systemEmailService;
	@Autowired
	private TaskPublishService taskPublishService;
	@Autowired
	private MailingHistoryService  mailingHistoryService;
	
	ResponseJson response = new ResponseJson();
	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-resend/index/{taskId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView index(@PathVariable("taskId") long taskId, ModelAndView modelAndView, HttpServletRequest request) {
		// Security
		isRoot(request);
		// Check
		isCheck(taskId);		
		// Var
		modelAndView.setViewName("/protected/campaign-task-resend/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("taskId", taskId);
		modelAndView.addObject("systemEmail",systemEmailService.findAll());
		modelAndView.addObject("historyEmail",mailingHistoryService.findAll());
		// View
		return modelAndView;
	}
	
	/**
	 * Reenvio de Email
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-task-resend/save/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson resendEmail(@PathVariable("id") long id, @RequestBody String json, HttpServletRequest request) throws JsonProcessingException {
		// Security
		isRoot(request);
		// Check
		isCheck(id);
		// Response
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("confirmation.resend.fail", null, LocaleContextHolder.getLocale()));
		// Execute
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		String emailSubject = r.getAsString("emailSubject");
		String emailBody = r.getAsString("emailBody");
		Long emailSystemId = r.getAsLong("emailSystemId");		
		boolean isSelectAll = r.getAsBoolean("isSelectAll");
		JsonArray hashmap = r.getAsJsonArray("hashmap");
		
		try {
			if (!Validator.isValidStringLength(emailBody, 100, 999000)) {
				response.setMessage(messageSource.getMessage("error.altbody.required", null, LocaleContextHolder.getLocale()));
			}else if (emailSystemId == null || emailSystemId == 0) {
				response.setMessage(messageSource.getMessage("fromEmail.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));					
			} else {
				Task task = hasTask;
				if (task != null && task.isPublish() && task.isAssign()) {
					String[] arg = { task.getName() };
					response.setMessage(messageSource.getMessage("confirmation.resend.success", arg, LocaleContextHolder.getLocale()));

					task.setEmailBody(emailBody);
					task.setEmailSubject(emailSubject);
					task.setEmailSystemId(emailSystemId);
					task.setIsSendEmail(true);
					//Filter
					if (hashmap.isJsonArray() && !isSelectAll) {
						Gson gson = new Gson();
						task.setAssignFilter(gson.toJson(hashmap));
						taskPublishService.resendScheduleEmail(task, true);
						
						System.out.println(" resendScheduleEmail ");
					}else if(isSelectAll) {
						taskPublishService.resendEmail(task);
					}
					//Resend
					response.setStatus(true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(" campaign-task-resend" + e.getMessage());
		}
		// Result
		return response;
	}
}
