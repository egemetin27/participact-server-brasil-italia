package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.MinimalSensorDetail;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Sensor;
import it.unibo.paserver.domain.SensorDetail;
import it.unibo.paserver.domain.SensorType;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.domain.support.DevicesBuilder;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.SensorService;

/**
 * @author Claudio
 */
@Controller
public class DevicesController {

	@Autowired
	private DevicesService devicesService;
	@Autowired
	private SensorService sensorService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/devices/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/devices/index");
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
	@RequestMapping(value = "/protected/devices/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/devices/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}	
	
	@RequestMapping(value = "/protected/devices/form/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView formAdd(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/devices/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		Devices d = new Devices();
		Set<Sensor> ls = new HashSet<Sensor>();
		d.setSensors(ls);
		d = devicesService.saveOrUpdate(d);
		modelAndView.addObject("form", d.getId());
		List<String> sens = new ArrayList<String>();
		modelAndView.addObject("sensors", sens);
		modelAndView.addObject("size", sens.size());
		List<String> l = new ArrayList<String>();
		for(SensorType st : SensorType.values()){
			l.add(st.toString());
		}
		modelAndView.addObject("hide", "remove sensor");
		modelAndView.addObject("addableSensorTypes", l);
		modelAndView.addObject("removableSensorTypes", sens);
		return modelAndView;
	}	
	
	@RequestMapping(value = "/protected/devices/edit/{id}/removesensor/{toRemove}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView removeSensor(@PathVariable("id") long id, @PathVariable("toRemove") String sensor, ModelAndView modelAndView) {
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		Devices d = devicesService.findById(id);
		modelAndView.addObject("form", d.getId());
		Set<Sensor> ss = d.getSensors();
		Sensor found = null;
		for(Sensor s : ss){
			if(s.getSensorTypeInfo()!=null && s.getSensorTypeInfo().getSensorType().toString().equalsIgnoreCase(sensor)){
				found = s;
				break;
			}
		}
		if(found!=null){
			ss.remove(found);
			d.setSensors(ss);
			devicesService.saveOrUpdate(d);
		}
		modelAndView.setViewName("/protected/devices/form");
		return modelAndView;
	}	
	
		@RequestMapping(value = "/protected/devices/edit/{id}/removeallsensor", method = RequestMethod.GET)
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ModelAndView removeAllSensor(@PathVariable("id") long id, ModelAndView modelAndView) {
			modelAndView.addObject("controller", this.getClass().getSimpleName());
			Devices d = devicesService.findById(id);
			modelAndView.addObject("form", d.getId());
			d.setSensors(new HashSet<Sensor>());
			devicesService.saveOrUpdate(d);
			modelAndView.setViewName("/protected/devices/form");
			return modelAndView;
		}	
		
		@RequestMapping(value = "/protected/devices/edit/{id}/addallsensor", method = RequestMethod.GET)
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ModelAndView addAllSensor(@PathVariable("id") long id, ModelAndView modelAndView) {
			modelAndView.addObject("controller", this.getClass().getSimpleName());
			Devices d = devicesService.findById(id);
			HashSet<Sensor> set = new HashSet<Sensor>();
			d.setSensors(set);
			modelAndView.addObject("form", d.getId());
			for(SensorType st : SensorType.values()){
				Sensor s = new Sensor();
				s.setDevice(d);
				SensorTypeInfo sti = sensorService.getSensorTypeInfo(st);
				s.setSensorTypeInfo(sti);
				MinimalSensorDetail msd = new MinimalSensorDetail();
				//msd.setSensorType(st);
				s.setSensorDetail(msd);
				d.getSensors().add(s);
			}
			devicesService.saveOrUpdate(d);
			modelAndView.addObject("form", id);
			modelAndView.setViewName("/protected/devices/form");
			return modelAndView;
		}	
	
	@RequestMapping(value = "/protected/devices/edit/{id}/addsensor/{toAdd}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addSensor(@PathVariable("id") long id, @PathVariable("toAdd") String sensor, ModelAndView modelAndView) {
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		Devices d = devicesService.findById(id);
		modelAndView.addObject("form", d.getId());
		Sensor s = new Sensor();
		SensorType found = null;
		for(SensorType st : SensorType.values()){
			if(st.toString().equalsIgnoreCase(sensor)){
				found = st;
				break;
			}
		}
		if(found!=null){
			s.setDevice(d);
			SensorTypeInfo sti = sensorService.getSensorTypeInfo(found);
			s.setSensorTypeInfo(sti);
			MinimalSensorDetail msd = new MinimalSensorDetail();
			//msd.setSensorType(found);
			s.setSensorDetail(msd);
			if(d.getSensors() == null){
				HashSet<Sensor> set = new HashSet<Sensor>();
				d.setSensors(set);
			}
			d.getSensors().add(s);
			devicesService.saveOrUpdate(d);
		}
		modelAndView.addObject("form", id);
		modelAndView.setViewName("/protected/devices/form");
		return modelAndView;
	}	

	/**
	 * Edicao de usuario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/devices/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/devices/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("form", id);
		Devices d = devicesService.findById(id);
		Set<Sensor> ls = d.getSensors();
		List<String> sens = new ArrayList<String>();
		List<String> sens2 = new ArrayList<String>();
		List<String> additional = new ArrayList<String>();
		List<String> sensdet = new ArrayList<String>();
		for(Sensor s : ls){
			if (s.getSensorTypeInfo()!=null){
				sens2.add(s.getSensorTypeInfo().getSensorType().toString());
				if(s.getSensorDetail().getName() != null)
					sens.add(s.getSensorTypeInfo().getSensorType().toString()+ " ("+s.getSensorDetail().getName()+")" );
				else
					sens.add(s.getSensorTypeInfo().getSensorType().toString());
			}
			else
				additional.add(s.getSensorDetail().getName().toString());
		}
		for(Sensor s : ls){
			if(s.getSensorDetail() != null && s.getSensorDetail() instanceof SensorDetail)
				sensdet.add(s.toString());
		}
		modelAndView.addObject("sensors", sens);
		modelAndView.addObject("additional", additional);
		modelAndView.addObject("sensorsdetails", sensdet);
		modelAndView.addObject("size", sens.size());
		List<String> l = new ArrayList<String>();
		for(SensorType st : SensorType.values()){
			boolean found = false;
			for(String s : sens2){
				if(s.equalsIgnoreCase(st.toString())){
					found = true;
					break;
				}
			}
			if(!found)
				l.add(st.toString());
		}
		if(l.size() == 0){
			modelAndView.addObject("hide", "add sensor");
		} else if(ls.size() == 0) {
			modelAndView.addObject("hide", "remove sensor");
		} else {
			modelAndView.addObject("hide", "none");
		}
		if(additional.size()>0)
			modelAndView.addObject("showadditional", "show");
		else
			modelAndView.addObject("showadditional", "notshow");
		if(sensdet.size()>0)
			modelAndView.addObject("details", "show");
		else
			modelAndView.addObject("details", "notshow");
		modelAndView.addObject("addableSensorTypes", l);
		modelAndView.addObject("removableSensorTypes", sens2);
		return modelAndView;
	}

	/**
	 * Edicao de usuario
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/devices/edit/{id}/find", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		Devices d = devicesService.findById(id);
		if (d != null) {
			response.setStatus(true);
			response.setItem(d);
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
	@RequestMapping(value = "/protected/devices/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = devicesService.removed(id);
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
	@RequestMapping(value = { "/protected/devices/save/",
			"/protected/devices/edit/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody ResponseJson save(@RequestBody String json) {
		ResponseJson response = new ResponseJson();
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		ReceiveJson r = new ReceiveJson(json);
		long id = Useful.convertStringToLong(r.getAsString("id"));
		String brand = r.getAsString("brand");
		String model = r.getAsString("model");
		String manufacturer =r.getAsString("manufacturer");
		String display = r.getAsString("display");
		String fingerprint = r.getAsString("fingerprint");
		String hardware = r.getAsString("hardware");
		String tags = r.getAsString("tags");
		String type = r.getAsString("type");
		String changelist = r.getAsString("changelist");
		try {
			if (!Validator.isValidStringLength(brand, 1, 100)) {
				throw new Exception(messageSource.getMessage("brand.title", null, LocaleContextHolder.getLocale()) + '.'
						+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else if (Validator.isEmptyString(model)) {
				throw new Exception(messageSource.getMessage("model.title", null, LocaleContextHolder.getLocale()) + '.'
						+ messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
			} else {
				Devices d = devicesService.findById(id);
				if(d == null){
					DevicesBuilder ds = new DevicesBuilder();
					ds.setAll(id, brand, model, manufacturer, null , display, fingerprint, hardware, tags, type, changelist);
					d = ds.build(true);
					Devices rs = devicesService.saveOrUpdate(d);
					if (rs != null) {
						response.setStatus(true);
						response.setOutcome(rs.getId());
					}
				}
				else
				{
					d.setBrand(brand);
					d.setModel(model);
					d.setManufacturer(manufacturer);
					d.setDisplay(display);
					d.setFingerprint(fingerprint);
					d.setHardware(hardware);
					d.setTags(tags);
					d.setType(type);
					d.setChangelist(changelist);
					d.setRemoved(false);
					Devices rs = devicesService.saveOrUpdate(d);
					if (rs != null) {
						response.setStatus(true);
						response.setOutcome(rs.getId());
					}
				}

			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
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
	@RequestMapping(value = "/protected/devices/search/{count}/{offset}", method = RequestMethod.POST)
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
		List<Object[]> items = devicesService.search(r.getAsString("brand"), r.getAsString("manufacturer"),
				r.getAsString("model"), r.getAsString("tags"), PaginationUtil.pagerequest(offset, count));
		response.setItems(items);
		if (items.size() > 0) {
			response.setTotal(devicesService.searchTotal(r.getAsString("brand"), r.getAsString("manufacturer"),
					r.getAsString("model"), r.getAsString("tags")));
		}
		return response;
	}
}