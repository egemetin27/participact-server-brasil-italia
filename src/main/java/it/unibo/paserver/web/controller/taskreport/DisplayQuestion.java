package it.unibo.paserver.web.controller.taskreport;

import java.util.ArrayList;
import java.util.List;

public class DisplayQuestion {
	private String question;
	private boolean isClosed;
	private List<AnswerResult> answers;
	private String openanswer;

	public DisplayQuestion() {
		answers = new ArrayList<DisplayQuestion.AnswerResult>();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public List<AnswerResult> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerResult> answers) {
		this.answers = answers;
	}

	public String getOpenanswer() {
		return openanswer;
	}

	public void setOpenanswer(String openanswer) {
		this.openanswer = openanswer;
	}

	public static class AnswerResult {
		private String answer;
		private Boolean result;

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public Boolean getResult() {
			return result;
		}

		public void setResult(Boolean result) {
			this.result = result;
		}

		public String toString() {
			return String.format("A: %s - %s", getAnswer(), getResult());
		}
	}
}
