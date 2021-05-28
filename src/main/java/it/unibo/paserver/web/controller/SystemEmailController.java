package it.unibo.paserver.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.SystemEmail;
import it.unibo.paserver.domain.support.SystemEmailBuilder;
import it.unibo.paserver.service.SystemEmailService;

@Controller
public class SystemEmailController {
	@Autowired
	private SystemEmailService systemEmailService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/system-email/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/system-email/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}

	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/system-email/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/system-email/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/system-email/save/",
			"/protected/system-email/edit/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson save(@RequestBody String json) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long id = Useful.convertStringToLong(r.getAsString("id"));
		String fromEmail = r.getAsString("fromEmail");
		String fromName = r.getAsString("fromName");
		String smtpHost = r.getAsString("smtpHost");
		Long smtpPort = r.getAsLong("smtpPort");
		String username = r.getAsString("username");
		String password = r.getAsString("password");
		String encryption = r.getAsString("encryption");
		boolean isActive = r.getAsBoolean("isActive");
		Integer limitPer = r.getAsInt("limitPer");
		Long limitPeriod = r.getAsLong("limitPeriod");
		Long limitSending = r.getAsLong("limitSending");
		// Validacao
		try {
			if (!Validator.isValidEmail(fromEmail)) {
				throw new Exception(messageSource.getMessage("fromEmail.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));
			} else if (Validator.isEmptyString(fromName)) {
				throw new Exception(messageSource.getMessage("fromName.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));

			} else if (Validator.isEmptyString(smtpHost)) {
				throw new Exception(messageSource.getMessage("smtpHost.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));
			} else if (Validator.isEmptyString(username)) {
				throw new Exception(messageSource.getMessage("username.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));

			} else if (Validator.isEmptyString(password)) {
				throw new Exception(messageSource.getMessage("password.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));

			} else if (Validator.isEmptyString(encryption)) {
				throw new Exception(messageSource.getMessage("encryption.title", null, LocaleContextHolder.getLocale())
						+ '.'
						+ messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale()));
			} else {
				// Build
				SystemEmailBuilder sp = new SystemEmailBuilder();
				// New or Update
				sp.setId(id);
				//Time
				Long limitTime = 86500L;
				if(limitPer == 0) {
					limitTime = limitPeriod * 86501L;
				}else if(limitPer == 1) {
					limitTime = limitPeriod * 3601L;
				}else if(limitPer == 2) {
					limitTime = limitPeriod * 61L;
				}
				// Setters
				sp.setAll(id, fromEmail, fromName, smtpHost, smtpPort, username, password, encryption, isActive, limitPer, limitPeriod, limitSending, limitTime);
				// Save
				SystemEmail p = sp.build(true);
				SystemEmail rs = systemEmailService.saveOrUpdate(p);
				if (rs != null) {
					response.setStatus(true);
					response.setOutcome(rs.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;
	}

	/**
	 * Edicao de usuario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/system-email/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/system-email/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("form", id);
		return modelAndView;
	}

	/**
	 * Edicao de usuario
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = {"/protected/system-email/edit/{id}/find","/protected/system-email/{id}/find"}, method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") int id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		SystemEmail p = systemEmailService.findById(id);
		if (p != null) {
			p.setPassword(null);
			response.setStatus(true);
			response.setItem(p);
		}
		return response;
	}

	/**
	 * Removed um item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/system-email/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = systemEmailService.delete(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed)
				? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
				: messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
		return response;
	}

	/**
	 * Busca customizada
	 * 
	 * @param search
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/system-email/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson search(@RequestBody String json, @PathVariable int count,
			@PathVariable int offset) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		// Request
		ReceiveJson r = new ReceiveJson(json);
		List<Object[]> items = systemEmailService.search(PaginationUtil.pagerequest(offset, count));
		if (items.size() > 0) {
			// Translate
			for (Object[] item : items) {
				item[6] = messageSource.getMessage(item[6].toString().toLowerCase() + ".title", null,
						LocaleContextHolder.getLocale());
			}
			// Total
			response.setTotal(systemEmailService.searchTotal());
		}
		// Return
		response.setItems(items);
		return response;
	}
}
