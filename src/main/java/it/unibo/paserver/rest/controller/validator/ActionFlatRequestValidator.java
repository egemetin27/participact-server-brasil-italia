package it.unibo.paserver.rest.controller.validator;

import it.unibo.paserver.domain.flat.request.ActionFlatRequest;
import it.unibo.paserver.domain.flat.request.QuestionRequest;
import it.unibo.paserver.domain.support.Pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ActionFlatRequestValidator implements Validator {

	private final QuestionRequestValidator questionRequestValidator;


	@Autowired
	public ActionFlatRequestValidator(QuestionRequestValidator questionRequestValidator) {
		if (questionRequestValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " +
					"required and must not be null.");
		}
		if (!questionRequestValidator.supports(QuestionRequest.class)) {
			throw new IllegalArgumentException("The supplied [Validator] must " +
					"support the validation of [QuestionRequest] instances.");
		}
		this.questionRequestValidator = questionRequestValidator;

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ActionFlatRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ActionFlatRequest request = (ActionFlatRequest) target;
		if(request.getType() == null)
			errors.rejectValue("type","required.task");

		if(request.getType() != null)
		{
			switch(request.getType()){
			case PHOTO:
				validateActionFlatPhoto(request,errors);
				break;
			case ACTIVITY_DETECTION:
				validateActionFlatActivityDetection(request,errors);
				break;
			case SENSING_MOST:
				validateActionFlatSensing(request,errors);
				break;
			case QUESTIONNAIRE:
				validateActionFlatQuestionnaire(request,errors);
				break;
			default:
				errors.rejectValue("type","invalid.action.type");

			}
		}

	}

	private void validateActionFlatQuestionnaire(ActionFlatRequest request,	Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "title", "required.task");
		if(request.getQuestions() == null)
			errors.rejectValue("questions","required.task");
		else
		{
			int index = 0;
			for(QuestionRequest q : request.getQuestions())
			{
				errors.pushNestedPath("questions["+index+"]");
				ValidationUtils.invokeValidator(questionRequestValidator, q, errors);
				errors.popNestedPath();
				index++;
			}
		}

	}

	private void validateActionFlatSensing(ActionFlatRequest request,Errors errors) {

		if(request.getInput_type() == null)
			errors.rejectValue("input_type","required.task");
		else if(Pipeline.Type.fromInt(request.getInput_type()).equals(Pipeline.Type.DUMMY))
			errors.rejectValue("input_type","invalid.pipeline.input_type");

	}

	private void validateActionFlatActivityDetection(ActionFlatRequest request,	Errors errors) {
		if(request.getDuration_threshold() == null || request.getDuration_threshold() < 0)
			errors.rejectValue("duration_threshold","negative.task");		
	}

	private void validateActionFlatPhoto(ActionFlatRequest request,	Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "name", "required.task");
		if(request.getNumeric_threshold() == null)
			errors.rejectValue("numeric_threshold","required.task");
		else if(request.getNumeric_threshold() < 1)
			errors.rejectValue("numeric_threshold","greatherthanzero.task.numeric_threshold");
	}

}
