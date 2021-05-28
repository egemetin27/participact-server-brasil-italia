package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataAccelerometerClassifier", indexes = {
		@Index(name = "acceclass_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "acceclass_ts", columnNames = { "sampletimestamp" }) })
public class DataAccelerometerClassifier extends Data {

	private static final long serialVersionUID = 7324068068645508192L;

	@NotNull
	private String classifier_value;

	public String getValue() {
		return classifier_value;
	}

	public void setValue(String classifier_value) {
		this.classifier_value = classifier_value;
	}

}
