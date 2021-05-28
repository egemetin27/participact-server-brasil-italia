package it.unibo.paserver.rest.controller.v2;

import java.sql.Timestamp;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
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
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.IssueSetting;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJsonRest;
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.service.IssueSettingService;
import it.unibo.paserver.service.SchoolCourseService;

/**
 * Gerais
 * 
 * @author Claudio
 *
 */
@RestController
public class GeneralRestfulController extends ApplicationRestfulController {
	@Autowired
	private InstitutionsService institutionsService;
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private IssueSettingService issueSettingService;

	@RequestMapping(value = { "/api/v2/public/general/institutions" }, method = RequestMethod.POST)
	public @ResponseBody ResponseJsonRest institutions(@RequestBody String json) {
		// Response
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.login.taken", null, LocaleContextHolder.getLocale()));
		response.setItem(null);
		try {
			// Request
			ReceiveJson r = new ReceiveJson(json);
			int count = r.getAsInt("count");
			count = count == 0 ? 10 : count;
			int offset = r.getAsInt("offset");
			// Version
			String version = r.getAsString("version");
			DateTime updateDate = new DateTime().minusYears(1);
			if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
				updateDate = Useful.converteStringToDate(version);
			}
			response.setVersion(new DateTime().minusYears(1));

			ListMultimap<String, Object> params = ArrayListMultimap.create();
			params.put("updateDate", updateDate);
			List<Institutions> result = institutionsService.filter(params, PaginationUtil.pagerequest(offset, count));
			// Resultado
			response.setStatus(true);
			response.setMessage(null);
			if (result.size() > 0) {
				response.setItem(result);
				response.setCount(result.size());
			} else {
				response.setItem(new ArrayList<>());
				response.setCount(0);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;
	}

	@RequestMapping(value = { "/api/v2/public/general/courses" }, method = RequestMethod.POST)
	public @ResponseBody ResponseJsonRest courses(@RequestBody String json) {
		// Response
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.login.taken", null, LocaleContextHolder.getLocale()));
		response.setItem(null);
		try {
			// Request
			ReceiveJson r = new ReceiveJson(json);
			int count = r.getAsInt("count");
			count = count == 0 ? 10 : count;
			int offset = r.getAsInt("offset");
			// Version
			String version = r.getAsString("version");
			DateTime updateDate = new DateTime().minusYears(1);
			if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
				updateDate = Useful.converteStringToDate(version);
			}
			response.setVersion(new DateTime().minusYears(1));

			ListMultimap<String, Object> params = ArrayListMultimap.create();
			params.put("updateDate", updateDate);
			List<SchoolCourse> result = schoolCourseService.filter(params, PaginationUtil.pagerequest(offset, count));
			// Resultado
			response.setStatus(true);
			response.setMessage(null);
			if (result.size() > 0) {
				response.setItem(result);
				response.setCount(result.size());
			} else {
				response.setItem(new ArrayList<>());
				response.setCount(0);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;
	}

	/**
	 * Configuracoes Gerais da campanha fixa
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/api/v2/public/general/issue-setting" }, method = RequestMethod.POST)
	public @ResponseBody ResponseJsonRest campaignFixex(@RequestBody String json) {
		// Response
		response.setStatus(false);
		response.setMessage(null);
		response.setItem(null);
		try {
			// Search
			IssueSetting item = issueSettingService.findById(1L);
			if (item != null) {
				// in milliseconds
				long ms = item.getIntervalTime()*1000;		
				DateTime d0 = new DateTime(0L, DateTimeZone.UTC);
				DateTime d1 = new DateTime(ms);
				//System.out.println(d0.toString("yyyy-MM-dd HH:mm:ss"));
				//System.out.println(d1.toString("yyyy-MM-dd HH:mm:ss"));
				// Set new value
				item.setIntervalTime(Useful.getDateDifferent(d0.toString("yyyy-MM-dd HH:mm:ss"), d1.toString("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")*1000);
				// Res
				response.setStatus(true);
				response.setItem(item);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;
	}
}
