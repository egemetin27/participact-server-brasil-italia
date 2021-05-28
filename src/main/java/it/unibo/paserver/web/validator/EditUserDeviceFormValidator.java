package it.unibo.paserver.web.validator;

import it.unibo.paserver.service.UserDeviceService;
import it.unibo.paserver.web.controller.EditUserDeviceForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditUserDeviceFormValidator implements Validator {

	//private static final String IMEI_PATTERN = "^[0-9]{14,16}$";
	
	@Autowired
	UserDeviceService userDeviceService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (EditUserDeviceForm.class).isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deviceId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uuid", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority","required");
        
		EditUserDeviceForm editUserDeviceForm = ((EditUserDeviceForm)target);
		long userId = editUserDeviceForm.getUserId();
		long priority = editUserDeviceForm.getPriority();
		String imei = editUserDeviceForm.getUuid();
		long id = editUserDeviceForm.getId();
		
		if (!errors.hasFieldErrors("priority")) {
			if(userDeviceService.existPriority(userId,priority,id)){
				errors.rejectValue("priority","duplicate");
			}
		}
		
		if (!errors.hasFieldErrors("priority")) {
			if (priority < 1 || priority > 99)  {
				errors.rejectValue("priority", "invalid");
			}
		}
		
		if (!errors.hasFieldErrors("uuid")) {
			if (userDeviceService.existImei(imei,id)) {
				errors.rejectValue("uuid", "duplicate");
			}
		}
		
		/*if (!errors.hasFieldErrors("imei")) {
			if (!imei.matches(IMEI_PATTERN)) {
				errors.rejectValue("imei", "invalid");
			}
		}*/
	}
}
