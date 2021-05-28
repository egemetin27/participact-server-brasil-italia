package it.unibo.paserver.rest.controller.v2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.CkanComcap;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJsonCkanComcap;
import it.unibo.paserver.service.CkanComcapService;

@RestController
public class CkanRestfulController extends ApplicationRestfulController {
	@Autowired
	private CkanComcapService ckanComcapService;

	/**
	 * Comcap
	 * 
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/api/v2/protected/ckan/comcap" }, method = RequestMethod.POST)
	public @ResponseBody ResponseJsonCkanComcap comcap(@RequestBody String json, HttpServletRequest request) throws Exception {
		ResponseJsonCkanComcap response = new ResponseJsonCkanComcap();
		// Details
		isUserDetails(request);
		// Response
		response.setStatus(false);
		response.setCount(0);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		response.setOutcome(getUserId());
		// Params / MultiMap
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		// Request
		ReceiveJson r = new ReceiveJson(json);
		int count = r.getAsInt("count");
		count = count == 0 ? 10 : count;
		int offset = r.getAsInt("offset");
		// Dates Values
		String start = r.getAsString("start");
		String deadline = r.getAsString("deadline");
		String queryAt = null;
		// Search
		String search = r.getAsString("search");
		if (!Validator.isEmptyString(search)) {
			params.put("search", search);
		}
		try {
			if (!Validator.isValidDateFormat(start, "yyyy-MM-dd")) {
				start = null;
			}
			if (!Validator.isValidDateFormat(deadline, "yyyy-MM-dd")) {
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
		} catch (Exception e) {
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
		try {
			// Ckan
			// Query
			List<CkanComcap> items = ckanComcapService.filter(params, "", true, PaginationUtil.pagerequest(offset, count));
			response.setItems(items);
			if (items.size() > 0) {
				response.setStatus(true);
				response.setMessage(null);
				response.setTotal(ckanComcapService.searchTotal(params));
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;

	}
}
