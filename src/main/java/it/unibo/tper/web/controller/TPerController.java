package it.unibo.tper.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.tper.TPerProxy;
import it.unibo.tper.TPerProxyImpl;
import it.unibo.tper.domain.BusStop;
import it.unibo.tper.domain.TPerBusStop;
import it.unibo.tper.service.BusStopService;
import it.unibo.tper.ws.domain.extensions.FermateResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TPerController {

	@Autowired
	BusStopService service;
	@Autowired
	TPerProxy proxy;

	@RequestMapping(value = "/protected/tper", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView getTPerBusSTop(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/tper/summary");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/tper", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView updateTPerBusSTop(ModelAndView modelAndView) {

		try
		{
			FermateResponse fermateResponse = proxy.getOpenDataLineeFermateResponse();
			if(fermateResponse!=null)
			{
				Map<Integer,TPerBusStop> map = new HashMap<Integer,TPerBusStop>();
				for(FermateResponse.Table t : fermateResponse.getTable())
				{				

					TPerBusStop current =  map.get(t.getCodiceFermata());
					if(current != null)
						current.getLines().add(t.getCodiceLinea());
					else
					{
						current = new TPerBusStop(t);
						current.getLines().add(t.getCodiceLinea());
						map.put(current.getCode(), current);
					}
				}

				service.updateBusStopData(new ArrayList<TPerBusStop>(map.values()), new DateTime().minusDays(1));
				modelAndView.setViewName("/protected/tper/confirmation");
				return modelAndView;
			}
			else
				throw new ResourceNotFoundException();	
		}

		catch(Exception e)
		{
			modelAndView.setViewName("/protected/tper/error");
			return modelAndView;
		}


	}







}
