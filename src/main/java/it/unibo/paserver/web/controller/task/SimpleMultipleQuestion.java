package it.unibo.paserver.web.controller.task;

import java.io.Serializable;

public class SimpleMultipleQuestion implements Serializable {

	private static final long serialVersionUID = -5410473511017787940L;

	private String question;

	private String[] answers;

	public SimpleMultipleQuestion() {
		answers = new String[20];
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

}
