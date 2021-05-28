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
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.FeedbackReport;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.support.FeedbackReportBuilder;
import it.unibo.paserver.service.FeedbackReportService;
import it.unibo.paserver.service.FeedbackTypeService;

/**
 * Feedback do Aplicativo
 * 
 * @author Claudio
 */
@Controller
public class FeedbackReportController {

	@Autowired
	private FeedbackReportService feedbackReportService;
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
	@RequestMapping(value = "/protected/feedback-report/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/feedback-report/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("types", feedbackTypeService.findAll());
		
		return modelAndView;
	}

	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/feedback-report/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/feedback-report/form");
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
	@RequestMapping(value = "/protected/feedback-report/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/feedback-report/form");
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
	@RequestMapping(value = "/protected/feedback-report/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		FeedbackReport fr = feedbackReportService.findById(id);
		if (fr != null) {
			response.setStatus(true);
			response.setItem(fr);
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
	@RequestMapping(value = "/protected/feedback-report/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = feedbackReportService.removed(id);
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
	@RequestMapping(value = { "/protected/feedback-report/save/", "/protected/feedback-report/edit/save/" }, method = RequestMethod.POST)
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
		String comment = r.getAsString("comment");
		// Validate
		try {
			if (!Validator.isValidNumeric(id)) {
				throw new Exception(messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
			} else if (!Validator.isValidStringLength(comment, 1, 100)) {
				throw new Exception(messageSource.getMessage("comment.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else {
				// id
				long uuid = Long.parseLong(id);
				// New or Update
				FeedbackReportBuilder frb = new FeedbackReportBuilder();
				if (uuid > 0) {// Edicao
					frb.setId(uuid);
				}
				// ab.setAll(uuid, name, description, false);
				// Save
				FeedbackReport fr = frb.build(true);
				FeedbackReport rs = feedbackReportService.saveOrUpdate(fr);
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
	@RequestMapping(value = "/protected/feedback-report/search/{count}/{offset}", method = RequestMethod.POST)
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
			String search = r.getAsString("search");
			if (!Validator.isEmptyString(search)) {
				params.put("search", search);
			}
			String start = r.getAsString("start");
			if (!Validator.isEmptyString(start) && Validator.isValidDateFormat(start, "dd/MM/yyyy")) {
				start = Useful.converteDateToString(Useful.converteStringToDatePattern(start, "dd/MM/yyyy"), "yyyy-MM-dd HH:mm:ss");
				params.put("start", start);
			}
			String end = r.getAsString("end");
			if (!Validator.isEmptyString(end) && Validator.isValidDateFormat(end, "dd/MM/yyyy")) {
				end = Useful.converteDateToString( Useful.converteStringToDatePattern( end, "dd/MM/yyyy"), "yyyy-MM-dd HH:mm:ss").replace("00:00:00", "23:59:59");
				params.put("end", end);
			}
			String feedback_type_id = r.getAsString("feedback_type_id");
			if (!Validator.isEmptyString(feedback_type_id) && Validator.isValidNumeric(feedback_type_id)) {
				params.put("feedback_type_id", feedback_type_id);
			}			
			
			//System.out.println(params.toString());
			// Search
			List<Object[]> items = feedbackReportService.search(params, PaginationUtil.pagerequest(offset, count));
			response.setItems(items);
			if (items.size() > 0) {
				response.setTotal(feedbackReportService.searchTotal(params));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("FeedbackReport search " + e.getMessage());
		}
		// return
		return response;
	}
}