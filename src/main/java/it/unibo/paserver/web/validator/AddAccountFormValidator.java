package it.unibo.paserver.web.validator;

import it.unibo.paserver.web.controller.AddAccountForm;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddAccountFormValidator implements Validator {

	private static final String USERNAME_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (AddAccountForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "required",
				new Object[] { "Username" });
		ValidationUtils.rejectIfEmpty(errors, "password", "required",
				new Object[] { "password" });
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required",
				new Object[] { "confirmPassword" });
		if (!errors.hasFieldErrors("username")) {
			String username = ((AddAccountForm) target).getUsername();
			if (!username.matches(USERNAME_PATTERN)) {
				errors.rejectValue("username", "invalid");
			}
		}
		AddAccountForm account = (AddAccountForm) target;
		if (!errors.hasFieldErrors("password")) {
			if (account.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
			}
		}

		if (!errors.hasFieldErrors("password")
				&& !errors.hasFieldErrors("confirmPassword")) {
			if (!account.getPassword().equals(account.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "dontmatch");
			}
		}
	}

}
