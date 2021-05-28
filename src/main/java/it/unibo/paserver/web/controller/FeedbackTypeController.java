package it.unibo.paserver.web.controller;

import java.util.ArrayList;
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
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.FeedbackType;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.support.FeedbackTypeBuilder;
import it.unibo.paserver.service.FeedbackTypeService;

/**
 * Controller dos tipos de feedback
 * 
 * @author Claudio
 */
@Controller
public class FeedbackTypeController {

	@Autowired
	private FeedbackTypeService feedbackTypeService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/feedback-type/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/feedback-type/index");
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
	@RequestMapping(value = "/protected/feedback-type/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/feedback-type/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}

	/**
	 * Edicao de item
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/feedback-type/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/feedback-type/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("form", id);
		return modelAndView;
	}

	/**
	 * Edicao de item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/feedback-type/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		FeedbackType u = feedbackTypeService.findById(id);
		if (u != null) {
			response.setStatus(true);
			response.setItem(u);
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
	@RequestMapping(value = "/protected/feedback-type/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = feedbackTypeService.removed(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
		return response;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/feedback-type/save/", "/protected/feedback-type/edit/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson save(@RequestBody String json) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		String id = (r.getAsString("id") == null) ? "0" : r.getAsString("id");
		String name = r.getAsString("name");
		String description = r.getAsString("description");
		// Validate
		try {
			if (!Validator.isValidNumeric(id)) {
				throw new Exception(messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
			} else if (!Validator.isValidStringLength(name, 1, 100)) {
				throw new Exception(messageSource.getMessage("protected.feedbackType.name", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else {
				// id
				long uuid = Long.parseLong(id);
				// New or Update
				FeedbackTypeBuilder ab = new FeedbackTypeBuilder();
				if (uuid > 0) {// Edicao
					ab.setId(uuid);
				}
				ab.setAll(uuid, name, description, false);
				// Save
				FeedbackType a = ab.build(true);
				FeedbackType rs = feedbackTypeService.saveOrUpdate(a);
				if (rs != null) {
					response.setStatus(true);
					response.setOutcome(rs.getId());
				}

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
	 * Busca customizada
	 * 
	 * @param search
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/feedback-type/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		response.setItems(new ArrayList<Object[]>());
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Params / MultiMap
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		try {
			String name = r.getAsString("name");
			if (!Validator.isEmptyString(name)) {
				params.put("name", name);
			}
			// Search
			List<Object[]> items = feedbackTypeService.search(params, PaginationUtil.pagerequest(offset, count));
			response.setItems(items);
			if (items.size() > 0) {
				response.setTotal(feedbackTypeService.searchTotal(params));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("FeedbackType search " + e.getMessage());
		}
		// return
		return response;
	}
}