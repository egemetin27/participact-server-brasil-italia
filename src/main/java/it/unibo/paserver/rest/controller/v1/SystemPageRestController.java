package it.unibo.paserver.rest.controller.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.bergmannsoft.config.Config;
import it.unibo.paserver.domain.ResponseMessage;

@Controller
public class SystemPageRestController {

	@RequestMapping(value = "/api/v2/public/pages", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage pages() {
		ResponseMessage response = new ResponseMessage();
		response.setResultCode(200);
		response.setProperty("status", "true");
		
		response.setProperty("data", String.format("%s,%s", Config.PRODUCTION_PAGE_FAQ, Config.PRODUCTION_PAGE_ABOUT) );
		// Return
		return response;
	}
}
