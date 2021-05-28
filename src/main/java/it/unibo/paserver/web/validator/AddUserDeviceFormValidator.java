package it.unibo.paserver.web.validator;

import it.unibo.paserver.service.UserDeviceService;
import it.unibo.paserver.web.controller.AddUserDeviceForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddUserDeviceFormValidator implements Validator{

	//private static final String IMEI_PATTERN = "^[0-9]{14,16}$";
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AddUserDeviceForm.class);
	
	@Autowired
	UserDeviceService userDeviceService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (AddUserDeviceForm.class).isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deviceId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uuid", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority","required");
		
		AddUserDeviceForm addUserDeviceForm = ((AddUserDeviceForm)target);
		
		boolean hasErrors = false;
		
		if(addUserDeviceForm.getUserId() == null){
			errors.rejectValue("userId","empty");
			hasErrors = true;
		}
		
		if(addUserDeviceForm.getPriority() == null){
			errors.rejectValue("priority","empty");
			hasErrors = true;
		}
		
		if(addUserDeviceForm.getUuid() == null){
			errors.rejectValue("uuid","empty");
			hasErrors = true;
		}
		
		if(hasErrors)
			return;
		
		long userId = addUserDeviceForm.getUserId().longValue();
		long priority = addUserDeviceForm.getPriority().longValue();
		String imei = addUserDeviceForm.getUuid();
		
		if (!errors.hasFieldErrors("priority")) {
			if(userDeviceService.existPriority(userId,priority,0)){
				errors.rejectValue("priority","duplicate");
			}
		}
		
		if (!errors.hasFieldErrors("priority")) {
			if (priority < 1 || priority > 99)  {
				errors.rejectValue("priority", "invalid");
			}
		}
		
		if (!errors.hasFieldErrors("uuid")) {
			if (userDeviceService.existImei(imei,0)) {
				errors.rejectValue("uuid", "duplicate");
			}
		}
		
		/*
		if (!errors.hasFieldErrors("UUID")) {
			if (!imei.matches(IMEI_PATTERN)) {
				errors.rejectValue("imei", "invalid");
			}
		}*/
		
	}
	
}
