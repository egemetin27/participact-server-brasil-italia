package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DataQuestionaireClosedAnswer extends Data {

	private static final long serialVersionUID = -1900866387953714053L;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "closed_answer_id")
	private ClosedAnswer closedAnswer;

	@NotNull
	private Boolean closedAnswerValue;

	public Boolean getClosedAnswerValue() {
		return closedAnswerValue;
	}

	public void setClosedAnswerValue(Boolean closedAnswerValue) {
		this.closedAnswerValue = closedAnswerValue;
	}

	public ClosedAnswer getClosedAnswer() {
		return closedAnswer;
	}

	public void setClosedAnswer(ClosedAnswer closedAnswer) {
		this.closedAnswer = closedAnswer;
	}

	public Question getQuestion() {
		return closedAnswer.getQuestion();
	}

	@Override
	public String toString() {
		return String.format("Closed answer %d to question %d (%s) : %s",
				getId(), getQuestion().getId(), getClosedAnswer()
						.getAnswerDescription(), getClosedAnswerValue());
	}
}
