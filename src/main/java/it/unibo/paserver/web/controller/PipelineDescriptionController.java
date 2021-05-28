package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.PipelineDescriptionService;

@Controller
public class PipelineDescriptionController {
	
	@Autowired
	private PipelineDescriptionService pipelineDescriptionService;
	
	@Autowired
	private DevicesService devicesService;
	
	@RequestMapping(value = "/protected/pipelineDescription/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listPipelines(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/pipelineDescription/index");
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		List<PipelineDescription> l = pipelineDescriptionService.getAllPipelineDescription();
		List<String> pipeNames = new ArrayList<String>();
		for(PipelineDescription p : l){
			pipeNames.add(p.toString());
		}
		modelAndView.addObject("pipeNames", pipeNames);
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/pipelineDescription/show/{name}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showPipelineDetails(@PathVariable String name, ModelAndView modelAndView) {
		modelAndView.setViewName("protected/pipelineDescription/show");
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		Pipeline.Type tp = null;
		for(Pipeline.Type t : Pipeline.Type.values()){
			if(t.toString().equalsIgnoreCase(name)){
				tp = t;
				break;
			}
		}
		if(tp!=null){
			PipelineDescription p = pipelineDescriptionService.getPipelineDescription(tp);
			Set<String> phy = new HashSet<String>();
			Set<String> logic = new HashSet<String>();
			ArrayList<SensorTypeInfo> stl = new ArrayList<SensorTypeInfo>();
			for(SensorTypeInfo s : p.getSensDescr()){
				if(s.isLogic())
					logic.add(s.toString());
				else
					phy.add(s.toString());
				stl.add(s);
			}
			List<Devices> devs = devicesService.getDevicesWithSensors(stl);
			if(phy.size()>0 && logic.size()>0){
				modelAndView.addObject("show", "both");
			} else if(phy.size()>0) {
				modelAndView.addObject("show", "physical");
			} else if(logic.size()>0) {
				modelAndView.addObject("show", "logic");
			}
			modelAndView.addObject("logicsens", logic);
			if(devs.size()>0){
				modelAndView.addObject("dev", "show");
				modelAndView.addObject("devices", devs);
			}
			modelAndView.addObject("physens", phy);
		}
		return modelAndView;
	}
	
}