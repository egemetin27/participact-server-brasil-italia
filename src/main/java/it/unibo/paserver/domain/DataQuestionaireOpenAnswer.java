package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DataQuestionaireOpenAnswer extends Data {

	private static final long serialVersionUID = 1780396465805694468L;

	@NotNull
	@JoinColumn(name = "question_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String openAnswerValue;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getOpenAnswerValue() {
		return openAnswerValue;
	}

	public void setOpenAnswerValue(String openAnswerValue) {
		this.openAnswerValue = openAnswerValue;
	}

	@Override
	public String toString() {
		return String.format("Open answer %d to question %d: %s", getId(),
				getQuestion().getId(), getOpenAnswerValue());
	}
}
