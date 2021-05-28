package it.unibo.paserver.web.validator;

import it.unibo.paserver.web.controller.PointsDatesForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PointsDatesFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return (PointsDatesForm.class).isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		PointsDatesForm form = (PointsDatesForm) arg0;

		if (form.getStart() == null)
			errors.rejectValue("start", "invalid");

		if (form.getEnd() == null)
			errors.rejectValue("end", "invalid");

	}

}
