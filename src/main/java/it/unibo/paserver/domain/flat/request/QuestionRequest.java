package it.unibo.paserver.domain.flat.request;

import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRequest {

	private String question;

	private  Integer question_order;

	private List<ClosedAnswerRequest> closed_answers;

	private Boolean isClosedAnswers;

	private Boolean isMultipleAnswers;


	public void setQuestion(String question) {
		this.question = question;
	}

	public void setQuestion_order(Integer question_order) {
		this.question_order = question_order;
	}

	public void setClosed_answers(List<ClosedAnswerRequest> closed_answers) {
		this.closed_answers = closed_answers;
	}

	public void setIsClosedAnswers(Boolean isClosedAnswers) {
		this.isClosedAnswers = isClosedAnswers;
	}

	public void setIsMultipleAnswers(Boolean isMultipleAnswers) {
		this.isMultipleAnswers = isMultipleAnswers;
	}

	public String getQuestion() {
		return question;
	}

	public Integer getQuestion_order() {
		return question_order;
	}

	public List<ClosedAnswerRequest> getClosed_answers() {
		return closed_answers;
	}

	public Boolean getIsClosedAnswers() {
		return isClosedAnswers;
	}

	public Boolean getIsMultipleAnswers() {
		return isMultipleAnswers;
	}

	public Question getQuestionModel() {
		Question result = new Question();
		if(!this.isClosedAnswers)
		{
			result.setQuestion(this.getQuestion());
			result.setQuestionOrder(this.question_order);
			result.setIsClosedAnswers(false);
			result.setIsMultipleAnswers(false);
		}
		else
		{
			result.setQuestion(this.question);
			result.setQuestionOrder(this.getQuestion_order());
			result.setIsClosedAnswers(true);
			result.setIsMultipleAnswers(this.isMultipleAnswers);
			List<ClosedAnswer> toAdd = new ArrayList<ClosedAnswer>();
			for(ClosedAnswerRequest c : this.getClosed_answers())
			{
				ClosedAnswer cl = c.getClosedAnswer(result);
				toAdd.add(cl);				
			}
			result.setClosed_answers(toAdd);
		}
		
		
		return result;
	}

}
