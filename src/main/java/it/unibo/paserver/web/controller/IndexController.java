package it.unibo.paserver.web.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;
import it.unibo.paserver.service.FileUploadService;
import it.unibo.paserver.service.SystemPageService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	@Autowired
	private SystemPageService systemPagaService;
	@Autowired
	FileUploadService fileUploadService;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, ModelAndView model) {
		// obtain locale from LocaleContextHolder
		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addObject("locale", currentLocale);
		// Set view
		model.setViewName("index");

		return model;
	}

	/**
	 * Paginas publicar
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/pages/{name}", method = RequestMethod.GET)
	public ModelAndView viewPage(@PathVariable String name, ModelAndView modelAndView) {
		// Get
		String str = "PAGE_"+name.toUpperCase(); 
		if (Validator.isValueinEnum(SystemPageType.class, name)) {
			str = name;
		}
		SystemPageType pageType = EnumUtils.isValidEnum(SystemPageType.class, str) ? SystemPageType.valueOf(str): SystemPageType.PAGE_FAQ;
		SystemPage page = systemPagaService.findByType(pageType);
		String content = " ";
		if (page != null) {
			content = page.getContent();
		}
		// Model
		modelAndView.setViewName("pages");
		modelAndView.addObject("content", content);
		return modelAndView;
	}
	/**
	 * Arquivo publicos
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/pages/file/{id}", method = RequestMethod.GET)
	public void file(@PathVariable long id, HttpServletResponse response) {
		// Get
		if (id > 0) {
			FileUpload f = fileUploadService.findById(id);
			if (f != null) {
				try {
					byte[] bytes = (byte[]) f.getFile();
					String contentType = (String) f.getMimeType();
					response.setContentType(contentType);
					response.setContentLength(bytes.length);
					ServletOutputStream out;
					out = response.getOutputStream();
					out.write(bytes);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}