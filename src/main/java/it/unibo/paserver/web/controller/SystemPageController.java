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
import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;
import it.unibo.paserver.domain.support.SystemPageBuilder;
import it.unibo.paserver.service.SystemPageService;

@Controller
public class SystemPageController {
	@Autowired
	private SystemPageService systemPageService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/system-page/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/system-page/index");
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
	@RequestMapping(value = "/protected/system-page/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/system-page/form");
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
	@RequestMapping(value = { "/protected/system-page/save/","/protected/system-page/edit/save/" }, method = RequestMethod.POST)
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
		String title = r.getAsString("title");
		String content = r.getAsString("content");
		boolean isActive = false;
		long typeId = Useful.convertStringToLong(r.getAsString("typeId"));
		// Para publicar?
		Integer[] haystack = {SystemPageType.PAGE_FAQ.ordinal(),SystemPageType.PAGE_ABOUT.ordinal(),SystemPageType.PAGE_LICENSE.ordinal(),SystemPageType.PAGE_PRIVACY.ordinal()};
		if (Validator.isValueinArray(haystack, typeId)) {
			typeId = SystemPageType.PAGE_DRAFT.ordinal();
		}else{
			isActive = true;
		}
		// Validacao
		try {
			if (!Validator.isValidStringLength(title, 1, 500)) {
				throw new Exception(messageSource.getMessage("protected.account.name", null, LocaleContextHolder.getLocale()) + '.'
						+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else {
				//Build
				SystemPageBuilder sp = new SystemPageBuilder();
				// New or Update
				sp.setId(id);
				if(isActive){
					SystemPage has = systemPageService.findByType(SystemPageType.values()[(int) typeId]);
					if(has != null){
						systemPageService.delete(has.getId());
					}
				}
				//Setters
				sp.setAll(SystemPageType.values()[(int) typeId], title, content, isActive);
				// Save
				SystemPage p = sp.build(true);
				SystemPage rs = systemPageService.saveOrUpdate(p);
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
	@RequestMapping(value = "/protected/system-page/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/system-page/form");
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
	@RequestMapping(value = {"/protected/system-page/edit/{id}/find","/protected/system-page/{id}/find"}, method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") int id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		SystemPage p = systemPageService.findById(id);
		if (p != null) {
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
	@RequestMapping(value = "/protected/system-page/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = systemPageService.delete(id);
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
	@RequestMapping(value = "/protected/system-page/search/{count}/{offset}", method = RequestMethod.POST)
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
		List<Object[]> items = systemPageService.search(PaginationUtil.pagerequest(offset, count));
		if (items.size() > 0) {
			//Translate
			for(Object[] item: items){
				item[6] = messageSource.getMessage(item[6].toString().toLowerCase()+".title", null, LocaleContextHolder.getLocale());
			}
			//Total
			response.setTotal(systemPageService.searchTotal());
		}
		//Return
		response.setItems(items);
		return response;
	}
}
