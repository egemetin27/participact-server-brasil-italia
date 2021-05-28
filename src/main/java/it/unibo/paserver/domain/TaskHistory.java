package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskHistory implements Serializable {

	private static final long serialVersionUID = 7408740264508346025L;

	@Id
	@Column(name = "task_history_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String details;
	
	private Long sensingProgress;
	private Integer photoProgress;
	private Integer questionnaireProgress;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "task_report_id")
	private TaskReport taskReport;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime timestamp;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskState state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskReport getTaskReport() {
		return taskReport;
	}

	public void setTaskReport(TaskReport taskReport) {
		this.taskReport = taskReport;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Long getSensingProgress() {
		return sensingProgress;
	}

	public void setSensingProgress(Long sensingProgress) {
		this.sensingProgress = sensingProgress;
	}

	public Integer getPhotoProgress() {
		return photoProgress;
	}

	public void setPhotoProgress(Integer photoProgress) {
		this.photoProgress = photoProgress;
	}

	public Integer getQuestionnaireProgress() {
		return questionnaireProgress;
	}

	public void setQuestionnaireProgress(Integer questionnaireProgress) {
		this.questionnaireProgress = questionnaireProgress;
	}		
}
