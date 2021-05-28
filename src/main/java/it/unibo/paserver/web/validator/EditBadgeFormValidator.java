package it.unibo.paserver.web.validator;

import it.unibo.paserver.web.controller.EditBadgeForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditBadgeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return (EditBadgeForm.class).isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"required");

	}

}
