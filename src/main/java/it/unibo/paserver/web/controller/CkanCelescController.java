package it.unibo.paserver.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.service.CkanCelescService;

@SuppressWarnings("Duplicates")
@Controller
public class CkanCelescController extends ApplicationController {

	@Autowired
	private CkanCelescService ckanCelescService;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/ckan-celesc/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/ckan-celesc/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}

	/**
	 * Download
	 * 
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/protected/ckan-celesc/download", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson download(@RequestBody String json, HttpServletRequest request) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setItems(new ArrayList<Object[]>());
		// Request
		ReceiveJson r = new ReceiveJson(json);
		try {
			// Security
			isRoot(request);
			// Search
			String search = r.getAsString("search");
			if (Validator.isEmptyString(search)) {
				search = "None";
			}
			String start = r.getAsString("start");
			if (Validator.isValidDateFormat(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
				start = Useful.converteDateToString(Useful.converteStringToDatePattern(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd");
			} else {
				start = "None";
			}
			String deadline = r.getAsString("deadline");
			if (Validator.isValidDateFormat(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
				deadline = Useful.converteDateToString(Useful.converteStringToDatePattern(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd");
			} else {
				deadline = "None";
			}
			long parentId = this.userId;

			try {
				String cmd = String.format(Config.PYTHON_SCRIPT_CELESC_EXPORT, start, deadline, search, parentId);
				System.out.println(cmd);
				Process p = Runtime.getRuntime().exec(cmd);
				response.setMessage(messageSource.getMessage("confirmation.exporting", null, LocaleContextHolder.getLocale()));
			} catch (Exception e) {
				e.printStackTrace(System.out);
				response.setMessage(messageSource.getMessage("download.error.data", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("CkanCelesc Download " + e.getMessage());
		}
		// return
		return response;
	}

	/**
	 * Busca customizada
	 * 
	 * @param json
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/ckan-celesc/search/{count}/{offset}", method = RequestMethod.POST)
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
			// Search
			String search = r.getAsString("search");
			if (!Validator.isEmptyString(search)) {
				params.put("search", search);
			}
			// Dates Values
			String start = r.getAsString("start");
			String deadline = r.getAsString("deadline");
			String queryAt = null;
			try {
				if (Validator.isValidDateFormat(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
					start = Useful.converteDateToString(Useful.converteStringToDatePattern(start, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd");
				} else {
					start = null;
				}
				if (Validator.isValidDateFormat(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
					deadline = Useful.converteDateToString(Useful.converteStringToDatePattern(deadline, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd");
				} else {
					deadline = null;
				}
				// Check
				if (!Validator.isEmptyString(start) && !Validator.isEmptyString(deadline)) {
					if (Useful.getDateDifferent(deadline, start, "yyyy-MM-dd") > 0) {
						queryAt = deadline;
						start = deadline = null;
					}
				} else if (!Validator.isEmptyString(start)) {
					queryAt = start;
					start = deadline = null;
				} else if (!Validator.isEmptyString(deadline)) {
					queryAt = deadline;
					start = deadline = null;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!Validator.isEmptyString(queryAt)) {
				params.put("queryAt", Useful.parseStringToDatePattern(queryAt, "yyyy-MM-dd"));
			} else {
				if (!Validator.isEmptyString(start)) {
					params.put("dateStart", Useful.parseStringToDatePattern(start, "yyyy-MM-dd"));
				}
				if (!Validator.isEmptyString(deadline)) {
					params.put("dateEnd", Useful.parseStringToDatePattern(deadline, "yyyy-MM-dd"));
				}
			}
			// Query
			List<Object[]> items = ckanCelescService.search(params, "", true, PaginationUtil.pagerequest(offset, count));
			response.setItems(items);
			if (items.size() > 0) {
				response.setTotal(ckanCelescService.searchTotal(params));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("CkanCelesc search " + e.getMessage());
		}
		// return
		return response;
	}
}