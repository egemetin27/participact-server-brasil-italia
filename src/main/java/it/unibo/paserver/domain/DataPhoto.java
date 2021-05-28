package it.unibo.paserver.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DataPhoto extends Data {

	private static final long serialVersionUID = -8991439730548302173L;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument file;

	@NotNull
	private Long taskId;

	@NotNull
	private Long actionId;

	private Integer height;

	private Integer width;

	public BinaryDocument getFile() {
		return file;
	}

	public void setFile(BinaryDocument file) {
		this.file = file;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
