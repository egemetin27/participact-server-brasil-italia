package it.unibo.paserver.web.controller;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public class ResetPasswordForm implements Serializable {

	private static final long serialVersionUID = 3513914189160222353L;
	private String password;
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void validateChoosePassword(ValidationContext context) {
		if (context.getUserEvent().equals("change")) {
			MessageContext msg = context.getMessageContext();
			if (StringUtils.isEmpty(getPassword())) {
				msg.addMessage(new MessageBuilder().error().source("password")
						.code("required.resetPasswordForm").build());
			} else if (getPassword().length() < 8) {
				msg.addMessage(new MessageBuilder().error().source("password")
						.code("tooshort.resetPasswordForm.password").build());
			} else if (StringUtils.isEmpty(getConfirmPassword())) {
				msg.addMessage(new MessageBuilder().error()
						.source("confirmPassword")
						.code("required.resetPasswordForm").build());
			} else if (!getPassword().equals(getConfirmPassword())) {
				msg.addMessage(new MessageBuilder().error()
						.source("confirmPassword")
						.code("notequal.resetPasswordForm.confirmPassword")
						.build());
			}
		}
	}
}