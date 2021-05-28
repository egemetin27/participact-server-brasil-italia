package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ClosedAnswer implements Serializable {

	private static final long serialVersionUID = 7301710346796107097L;

	@Id
	@Column(name = "closed_answer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	private Question question;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String answerDescription;
	@NotNull
	private Integer answerOrder;
	@Column(name = "target_id")
	private Long targetId = 0L;
	@Column(name = "target_order")
	private Integer targetOrder = 0;

	@Column(name = "amount")
	private int amount;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetOrder() {
		return targetOrder;
	}

	public void setTargetOrder(Integer targetOrder) {
		this.targetOrder = targetOrder;
	}

	public int getAmount() {
		return amount;
	}
}
