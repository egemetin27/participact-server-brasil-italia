package it.unibo.paserver.web.validator;

import it.unibo.paserver.web.controller.RecoverPasswordForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RecoverPasswordFormValidator implements Validator {

	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (RecoverPasswordForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		RecoverPasswordForm rpf = (RecoverPasswordForm) target;

		if (!errors.hasFieldErrors("email")) {
			String username = rpf.getEmail();
			
			// marcelo brocardo
		    // commented 
			/*if (!username.matches(EMAIL_PATTERN_UNIBO)) {
				errors.rejectValue("email", "invalid");
			}
			*/
		}

	}

}
