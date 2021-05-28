package it.unibo.paserver.web.validator;

import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.SelectUsersForm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SelectUsersFormValidator implements Validator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (SelectUsersForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userList", "required");

		if (errors.hasErrors()) {
			return;
		}
		SelectUsersForm form = (SelectUsersForm) target;
		String[] userStrings = form.getUserList().split("[^a-zA-Z0-9@.]");
		List<String> unknown = new ArrayList<String>();
		List<String> nogcmid = new ArrayList<String>();

		for (String s : userStrings) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			User u = userService.getUser(s);
			if (u == null) {
				unknown.add(s);
			} else if (u.getGcmId() == null) {
				nogcmid.add(s);
			}
		}

		if (unknown.size() > 0) {
			String unknownUsersString = StringUtils.join(unknown, ", ");
			errors.rejectValue("userList", "unknown",
					new String[] { unknownUsersString }, "Unknown users");
		}
		if (nogcmid.size() > 0) {
			String nogcmidUsersString = StringUtils.join(nogcmid, ", ");
			errors.rejectValue("userList", "nogcmid",
					new String[] { nogcmidUsersString }, "Unknown users");
		}
	}

}
