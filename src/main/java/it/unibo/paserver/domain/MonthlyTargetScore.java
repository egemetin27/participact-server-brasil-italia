package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "year", "month" }) })
public class MonthlyTargetScore implements Serializable {

	private static final long serialVersionUID = -2098240712045550159L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Integer year;

	@NotNull
	private Integer month;

	@NotNull
	private Long targetScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Long getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(Long targetScore) {
		this.targetScore = targetScore;
	}

	@Override
	public String toString() {
		return String.format("Score %d-%d: %d", getYear(), getMonth(),
				getTargetScore());
	}
}
