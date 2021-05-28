package it.unibo.paserver.web.validator;

import it.unibo.paserver.domain.MonthlyTargetScore;
import it.unibo.paserver.service.MonthlyTargetScoreService;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MonthlyTargetScoreValidator implements Validator {

	@Autowired
	MonthlyTargetScoreService monthlyTargetScoreService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (MonthlyTargetScore.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "year", "required");
		ValidationUtils.rejectIfEmpty(errors, "month", "required");
		ValidationUtils.rejectIfEmpty(errors, "targetScore", "required");

		MonthlyTargetScore mts = (MonthlyTargetScore) target;
		if (!errors.hasFieldErrors("year")
				&& (mts.getYear() < 1990 || mts.getYear() > 2100)) {
			errors.rejectValue("year", "invalid");
		}

		if (!errors.hasFieldErrors("month")
				&& (mts.getMonth() < 1 || mts.getMonth() > 12)) {
			errors.rejectValue("month", "invalid");
		}

		if (!errors.hasFieldErrors("targetScore") && mts.getTargetScore() < 0) {
			errors.rejectValue("targetScore", "invalid");
		}

		if (!errors.hasErrors()) {
			try {
				monthlyTargetScoreService.findByYearMonth(mts.getYear(),
						mts.getMonth());
				errors.rejectValue("year", "alreadyexists");
				errors.rejectValue("month", "alreadyexists");
			} catch (NoResultException e) {

			}
		}
	}

}
