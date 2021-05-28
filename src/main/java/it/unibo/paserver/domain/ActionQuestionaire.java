package it.unibo.paserver.domain;

import it.unibo.paserver.domain.flat.ActionFlat;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ActionQuestionaire extends Action {

	private static final long serialVersionUID = 2502379939041270345L;

	@NotNull
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	/**
	 * 2017-03-24: Claudionor
	 * Banco nao modelado corretamente, adicionar indexes auxiliares
	 * CREATE INDEX idx_actionquestionaire_action_id ON public.actionquestionaire USING btree (action_id) TABLESPACE pg_default;
	 * CREATE INDEX idx_actionquestionaire_question_action_id ON public.actionquestionaire_question USING btree (action_id) TABLESPACE pg_default;
	 * CREATE INDEX idx_actionquestionaire_question_question_id ON public.actionquestionaire_question USING btree (question_id) TABLESPACE pg_default;
	**/
	@JoinTable(name = "ActionQuestionaire_Question", joinColumns = { @JoinColumn(name = "action_id") }, inverseJoinColumns = { @JoinColumn(name = "question_id") })
	@OrderBy(value = "question_order")
	private List<Question> questions;
	@NotNull
	@Column(columnDefinition = "TEXT")
	private String title;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(name = "isrepeat")
	private Boolean isRepeat;

	public ActionQuestionaire() {
		questions = new LinkedList<Question>();
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}

	public Boolean getRepeat() {
		return isRepeat;
	}

	public void setRepeat(Boolean repeat) {
		this.isRepeat = repeat;
	}

	@JsonIgnore
	public int getNextQuestionOrder() {
		if (questions == null) {
			throw new IllegalStateException("questions can't be null");
		}
		if (questions.size() == 0) {
			return 0;
		}
		int max = 0;
		for (Question q : questions) {
			if (q.getQuestionOrder() > max) {
				max = q.getQuestionOrder();
			}
		}
		max = max + 1;
		return max;
	}
}
