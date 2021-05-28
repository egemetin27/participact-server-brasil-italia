package it.unibo.paserver.rest.controller.validator;

import it.unibo.paserver.domain.flat.request.ClosedAnswerRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class ClosedAnswerRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ClosedAnswerRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "answerDescription", "required.task");

		ClosedAnswerRequest request = (ClosedAnswerRequest) target;
		if(request.getAnswerOrder() == null)
			errors.rejectValue("answerOrder", "required.task");
		if(request.getAnswerOrder() == null)
			if(request.getAnswerOrder() < 0 )
				errors.rejectValue("answerOrder", "greatherthanzero.closed_answer.answerorder");


	}

}
