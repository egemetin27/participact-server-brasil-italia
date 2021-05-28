package it.unibo.paserver.web.validator;

import it.unibo.paserver.domain.ClientSWVersion;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ClientSWVersionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (ClientSWVersion.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "version", "required",
				new Object[] { "version" });
		if (!errors.hasErrors()) {
			ClientSWVersion csv = (ClientSWVersion) target;
			if (csv.getVersion() < 0) {
				errors.rejectValue("version", "positive");
			}
		}
	}

}
