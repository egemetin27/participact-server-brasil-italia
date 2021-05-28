package it.unibo.paserver.web.validator;

import it.unibo.paserver.domain.TaskHistory;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TaskHistoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TaskHistory.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "state", "required");
	}

}
