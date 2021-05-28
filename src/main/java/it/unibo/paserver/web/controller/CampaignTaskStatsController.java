package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.ResponseJson;

@Controller
public class CampaignTaskStatsController extends ApplicationController{
	/**
	 * Retorna o total por estado
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-task-state-stats/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getTotalByState(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setItems(campaignService.getTotalByState(id));
		// Return
		return response;
	}
	/**
	 * Total de Campanhas vinculadas a um usuario
	 * @param id
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-task-user-stats/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getTotalByStateAndUser(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
		DateTime startDate = new DateTime().minusMonths(12);
		DateTime endDate = new DateTime();
		List<Object[]> items = campaignService.getTotalByStateAndUser(id, startDate, endDate);

		List<DateTime> months = Useful.getMonthsBetweenDates(startDate, endDate);
		//Categorias e data
		String[] categories = new String[months.size()];
		int index =0;
		for(DateTime cal: months){
			int m = cal.getMonthOfYear();
			int y = cal.getYear();
			categories[index] = m+"/"+y;
			index++;
		}
		//Series
		List<String> series = new ArrayList<String>();
		for(Object[] i: items){
			String serie = (String) i[2].toString(); 
			if(!series.contains(serie)){
				series.add(serie);
			}
		}
		//Datas
		HashMap<String, String[]> datas = new HashMap<String, String[]>();
		for(String serie: series){
			datas.put(serie, new String[months.size()]);			
		}
		//Values
		for(Object[] i: items){			
			String m = (String) i[0].toString();
			String y = (String) i[1].toString();
			String k =  m+"/"+y;
			String s = (String) i[2].toString();
			String v = (String) i[3].toString();
			int indexOf = ArrayUtils.indexOf(categories, k);
			if (indexOf >= 0) {
				String[] value = datas.get(s);
				value[indexOf] = v;
				datas.put(s, value);
			}
		}
		//Result
		HashMap<String, Object> result = new HashMap<String, Object>();
		for ( Map.Entry<String,  String[]> entry : datas.entrySet()) {
		    String key = entry.getKey();
		    String[] values = entry.getValue();
		    result.put(messageSource.getMessage("statistics.state."+key.toLowerCase(), null, LocaleContextHolder.getLocale()), values);
		}		
		//Chart
		HashMap<String, Object> chart = new HashMap<String, Object>();
		chart.put("type", "line");
		chart.put("zoomType", "x");
		chart.put("text", messageSource.getMessage("chart.task.assigned.title", null, LocaleContextHolder.getLocale()));
		chart.put("categories",categories);
		chart.put("series", series);
		chart.put("datas", datas);
		chart.put("result", result);		
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(items.size()>0);
		response.setChart(chart);
		response.setItem(items);
		// Return
		return response;
	}
}
