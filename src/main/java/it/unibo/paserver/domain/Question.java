package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question implements Serializable {

	private static final long serialVersionUID = 4337352852760317052L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String question;

	@NotNull
	private Integer question_order;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "QuestionClosedAnswers", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "closed_answer_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy(value = "answerOrder")
	private List<ClosedAnswer> closed_answers;
	@NotNull
	private Boolean isClosedAnswers;
	@NotNull
	private Boolean isMultipleAnswers;

	@Column(name = "isrequired")
	private Boolean isRequired = false;
	@Column(name = "isphoto")
	private Boolean isPhoto = false;
	@Column(name = "number_photos")
	private Integer numberPhotos = 0;
	@Column(name = "target_id")
	private Long targetId = 0L;
	@Column(name = "target_order")
	private Integer targetOrder = 0;
	@JsonProperty("isDate")
	@Column(name = "isdate")
	private Boolean isDate = false;
	@JsonProperty("isSchoolsFromGPS")
	@Column(name = "isschoolsfromgps")
	private Boolean isSchoolsFromGPS = false;

	public Boolean getIsClosedAnswers() {
		return isClosedAnswers;
	}

	public void setIsClosedAnswers(Boolean isClosedAnswers) {
		this.isClosedAnswers = isClosedAnswers;
	}

	public Boolean getIsMultipleAnswers() {
		return isMultipleAnswers;
	}

	public void setIsMultipleAnswers(Boolean isMultipleAnswers) {
		this.isMultipleAnswers = isMultipleAnswers;
	}

	public Question() {
		closed_answers = new LinkedList<ClosedAnswer>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getQuestionOrder() {
		return question_order;
	}

	public void setQuestionOrder(int question_order) {
		this.question_order = question_order;
	}

	public List<ClosedAnswer> getClosed_answers() {
		return closed_answers;
	}

	public void setClosed_answers(List<ClosedAnswer> closed_answers) {
		this.closed_answers = closed_answers;
	}

	@JsonIgnore
	public int getNextClosedAnswerOrder() {
		if (closed_answers == null) {
			throw new IllegalStateException("closed answers can't be null");
		}
		if (closed_answers.size() == 0) {
			return 0;
		}
		int max = 0;
		for (ClosedAnswer ca : closed_answers) {
			if (ca.getAnswerOrder() > max) {
				max = ca.getAnswerOrder();
			}
		}
		max = max + 1;
		return max;
	}

	public Boolean getRequired() {
		return isRequired;
	}

	public void setRequired(Boolean required) {
		isRequired = required;
	}

	public Boolean getPhoto() {
		return isPhoto;
	}

	public void setPhoto(Boolean photo) {
		isPhoto = photo;
	}

	public int getNumberPhotos() {
		return numberPhotos != null ? numberPhotos : 0;
	}

	public void setNumberPhotos(int numberPhotos) {
		this.numberPhotos = numberPhotos;
	}

	public Long getTargetId() {
		return targetId != null ? targetId : 0L;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public int getTargetOrder() {
		return targetOrder != null ? targetOrder : 0;
	}

	public void setTargetOrder(int targetOrder) {
		this.targetOrder = targetOrder;
	}

	public Boolean getIsDate() {
		return isDate != null ? this.isDate : false;
	}

	public void setDate(Boolean date) {
		isDate = date;
	}

	public Boolean getSchoolsFromGPS() {
		return isSchoolsFromGPS;
	}

	public void setSchoolsFromGPS(Boolean schoolsFromGPS) {
		isSchoolsFromGPS = schoolsFromGPS;
	}

	@JsonIgnore
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Question ");
		sb.append(id);
		sb.append(" ");
		if (isClosedAnswers) {
			sb.append("[closed]");
			if (isMultipleAnswers) {
				sb.append("[multiple]");
			} else {
				sb.append("[single]");
			}
		} else {
			sb.append("[open]");
		}
		sb.append(" Is Required : ");
		sb.append(getRequired());
		sb.append(" Is Photo : ");
		sb.append(getPhoto());
		sb.append(" ");
		sb.append(getNumberPhotos());
		sb.append(" >> ");
		sb.append(getQuestion());
		return sb.toString();
	}
}
