package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataGoogleActivityRecognition", indexes = { @Index(name = "dgar_user_sampletimestamp", columnNames = { "user_id", "sampletimestamp" }) })
public class DataGoogleActivityRecognition extends Data {

	private static final long serialVersionUID = 5025465812630425096L;
	@NotNull
	private String classifier_value;
	@NotNull
	private Integer confidence;

	private Integer stationary = 0;
	private Integer walking = 0;
	private Integer running = 0;
	private Integer automotive = 0;
	private Integer unknown = 0;

	public String getClassifier_value() {
		return classifier_value;
	}

	public void setClassifier_value(String classifier_value) {
		this.classifier_value = classifier_value;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	/**
	 * @return the stationary
	 */
	public Integer getStationary() {
		return stationary;
	}

	/**
	 * @param stationary
	 *            the stationary to set
	 */
	public void setStationary(Integer stationary) {
		this.stationary = stationary;
	}

	/**
	 * @return the walking
	 */
	public Integer getWalking() {
		return walking;
	}

	/**
	 * @param walking
	 *            the walking to set
	 */
	public void setWalking(Integer walking) {
		this.walking = walking;
	}

	/**
	 * @return the running
	 */
	public Integer getRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(Integer running) {
		this.running = running;
	}

	/**
	 * @return the automotive
	 */
	public Integer getAutomotive() {
		return automotive;
	}

	/**
	 * @param automotive
	 *            the automotive to set
	 */
	public void setAutomotive(Integer automotive) {
		this.automotive = automotive;
	}

	/**
	 * @return the unknown
	 */
	public Integer getUnknown() {
		return unknown;
	}

	/**
	 * @param unknown
	 *            the unknown to set
	 */
	public void setUnknown(Integer unknown) {
		this.unknown = unknown;
	}
}
