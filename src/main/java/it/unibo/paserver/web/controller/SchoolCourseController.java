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
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.support.SchoolCourseBuilder;
import it.unibo.paserver.service.SchoolCourseService;

@Controller
public class SchoolCourseController {
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/school-course/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/school-course/index");
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
	@RequestMapping(value = "/protected/school-course/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/school-course/form");
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
	@RequestMapping(value = { "/protected/school-course/save/",
			"/protected/school-course/edit/save/" }, method = RequestMethod.POST)
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
		String name = r.getAsString("name");
		String description = r.getAsString("description");
		String uniCourseId =  r.getAsString("uniCourseId");
		// Validacao
		try {
			if (!Validator.isValidStringLength(name, 1, 500)) {
				throw new Exception(messageSource.getMessage("protected.account.name", null, LocaleContextHolder.getLocale()) + '.'+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			}else if(Validator.isEmptyString(uniCourseId)){
				throw new Exception(messageSource.getMessage("education.level.title", null, LocaleContextHolder.getLocale()) + '.'+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));				
			} else {
				//Build
				SchoolCourseBuilder sc = new SchoolCourseBuilder();
				// New or Update
				sc.setId(id);
				//Setters
				sc.setAll(id, name, UniCourse.valueOf(uniCourseId), description);
				// Save
				SchoolCourse s = sc.build(true);
				SchoolCourse rs = schoolCourseService.saveOrUpdate(s);
				if (rs != null) {
					response.setStatus(true);
					response.setOutcome(rs.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setMessage(e.getMessage());
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
	@RequestMapping(value = "/protected/school-course/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/school-course/form");
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
	@RequestMapping(value = "/protected/school-course/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") int id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		SchoolCourse s = schoolCourseService.findById(id);
		if (s != null) {
			response.setStatus(true);
			response.setItem(s);
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
	@RequestMapping(value = "/protected/school-course/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = schoolCourseService.removed(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
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
	@RequestMapping(value = "/protected/school-course/search/{count}/{offset}", method = RequestMethod.POST)
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
		List<Object[]> items = schoolCourseService.search(r.getAsString("name"), r.getAsString("description"),r.getAsString("uniCourseId"), PaginationUtil.pagerequest(offset, count));
		if (items.size() > 0) {
			//Translate
			for(Object[] item: items){
				item[5] = messageSource.getMessage("education."+item[5].toString().toLowerCase()+".title", null, LocaleContextHolder.getLocale());
			}
			//Count
			response.setTotal(schoolCourseService.searchTotal(r.getAsString("name"), r.getAsString("description"),r.getAsString("uniCourseId")));
		}
		//Return
		response.setItems(items);
		return response;
	}
}
