package it.unibo.paserver.domain.flat.request;

import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Question;

public class ClosedAnswerRequest {
	
		private String answerDescription;

	    private Integer answerOrder;

		public String getAnswerDescription() {
			return answerDescription;
		}

		public void setAnswerDescription(String answerDescription) {
			this.answerDescription = answerDescription;
		}

		public Integer getAnswerOrder() {
			return answerOrder;
		}

		public void setAnswerOrder(Integer answerOrder) {
			this.answerOrder = answerOrder;
		}

		public ClosedAnswer getClosedAnswer(Question question) {
			ClosedAnswer result = new ClosedAnswer();
			result.setAnswerDescription(this.answerDescription);
			result.setAnswerOrder(this.answerOrder);
			result.setQuestion(question);
			return result;
		}

}
