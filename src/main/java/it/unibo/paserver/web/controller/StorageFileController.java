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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.service.StorageFileService;

/**
 * Arquivos Carregados em Cloud
 * 
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class StorageFileController {

	@Autowired
	private StorageFileService storageFileServiceeService;
	@Autowired
	protected MessageSource messageSource;
	/**
	 * Busca customizada
	 * 
	 * @param json
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/storage-file/search/{count}/{offset}", method = RequestMethod.POST)
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
		boolean hasSouce = false;
		try {
			String originalFileName = r.getAsString("originalFileName");
			if (!Validator.isEmptyString(originalFileName)) {
				params.put("originalFileName", originalFileName);
			}
			// IDs
			Long fId = r.getAsLong("feedbackReportId");
			if (fId > 0) {
				hasSouce = true;
				params.put("FeedbackReportId", fId);
			}
			if (hasSouce) {
				// Search
				List<Object[]> items = storageFileServiceeService.searchAllFeedback(params, PaginationUtil.pagerequest(offset, count));
				response.setItems(items);
				response.setTotal(items.size());
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("StorageFile search " + e.getMessage());
		}
		// return
		return response;
	}
	
	/**
	 * Removed um item
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/storage-file/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR','ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = storageFileServiceeService.removed(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
		return response;
	}

}