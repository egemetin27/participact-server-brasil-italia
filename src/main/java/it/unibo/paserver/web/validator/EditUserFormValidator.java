package it.unibo.paserver.web.validator;

import it.unibo.paserver.web.controller.EditUserForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditUserFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (EditUserForm.class).isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentAddress",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentZipCode",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentCity",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentProvince",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhoneNumber",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectEmail",
				"required");

		EditUserForm euf = (EditUserForm) target;

		if (!errors.hasFieldErrors("projectEmail")) {
			String projectEmail = euf.getProjectEmail();
			if (!projectEmail.matches(EMAIL_PATTERN)) {
				errors.rejectValue("projectEmail", "invalid");
			}
		}

		if (!errors.hasFieldErrors("password")) {
			if (euf.getPassword() != null && euf.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
			}
		}

		if ((euf.getPassword() != null && euf.getPassword() == null)
				|| (euf.getPassword() == null && euf.getPassword() != null)) {
			errors.rejectValue("confirmPassword", "required");
			errors.rejectValue("password", "required");
		} else if (euf.getPassword() != null && euf.getPassword() != null) {
			if (!euf.getPassword().equals(euf.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "dontmatch");
			}
		}

	}

}
