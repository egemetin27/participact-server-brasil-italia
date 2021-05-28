package it.unibo.paserver.domain.format;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unibo.paserver.domain.Question;

public abstract class ActionQuestionaireFormat extends ActionFormat {
	@JsonIgnore
	public abstract List<Question> getQuestions();
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract String getTitle() ;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public abstract String getDescription();
}
