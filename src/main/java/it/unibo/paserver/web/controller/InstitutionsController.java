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
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.support.InstitutionsBuilder;
import it.unibo.paserver.service.InstitutionsService;
/**
 * Controller das instituicoes no dashboard
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class InstitutionsController {

	@Autowired
	private InstitutionsService institutionsService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/institutions/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/institutions/index");
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
	@RequestMapping(value = "/protected/institutions/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/institutions/form");
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
	@RequestMapping(value = "/protected/institutions/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/institutions/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("form", id);
		return modelAndView;
	}
	/**
	 * Edicao de item
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/institutions/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		Institutions u = institutionsService.findById(id);
		if (u != null) {
			response.setStatus(true);
			response.setItem(u);
		}
		return response;
	}

	/**
	 * Removed um item
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/institutions/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = institutionsService.removed(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
				: messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
		return response;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/institutions/save/",
			"/protected/institutions/edit/save/" }, method = RequestMethod.POST)
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
		String email = r.getAsString("email");
		String phone = Useful.removeAllNonNumeric(r.getAsString("phone"));
		String contact = Useful.toLowerCase(r.getAsString("contact"));
		// Geo/Address
		String address = r.getAsString("address");
		String addressCountry = r.getAsString("addressCountry");
		String addressCity = r.getAsString("addressCity");
		String addressPostalCode = r.getAsString("addressPostalCode");
		String addressNumber = r.getAsString("addressNumber");
		String addressState = r.getAsString("addressState");
		String mapLat = r.getAsString("mapLat");
		String mapLng = r.getAsString("mapLng");
		// Validate
		try {
			if (!Validator.isValidNumeric(id)) {
				throw new Exception(messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
			} else if (!Validator.isValidStringLength(name, 1, 100)) {
				throw new Exception(messageSource.getMessage("protected.institutions.name", null, LocaleContextHolder.getLocale()) + '.'
						+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else if (!Validator.isEmptyString(email) && !Validator.isValidEmail(email)) {
				throw new Exception(messageSource.getMessage("protected.institutions.email", null, LocaleContextHolder.getLocale()) + '.'
						+ messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
			} else {
				// id
				long uuid = Long.parseLong(id);
				// New or Update
				InstitutionsBuilder ib = new InstitutionsBuilder();
				if (uuid > 0) {// Edicao
					ib.setId(uuid);
				}
				ib.setAll(name, phone, email, contact, address, addressNumber, addressCity, addressState,
						addressCountry, addressPostalCode, mapLat, mapLng);
				// Save
				Institutions i = ib.build(true);
				Institutions rs = institutionsService.saveOrUpdate(i);
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
	 * Busca customizada
	 * 
	 * @param json
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/institutions/search/{count}/{offset}", method = RequestMethod.POST)
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
		List<Object[]> items = institutionsService.search(r.getAsString("name"), r.getAsString("contact"),
				r.getAsString("email"), r.getAsString("phone"), PaginationUtil.pagerequest(offset, count));
		response.setItems(items);
		if (items.size() > 0) {
			response.setTotal(institutionsService.searchTotal(r.getAsString("name"), r.getAsString("address"),
					r.getAsString("email"), r.getAsString("phone")));
		}
		return response;
	}
}