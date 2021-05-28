package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataAppNetTraffic", indexes = { @Index(name = "appnettraf_user_ts", columnNames = { "user_id", "sampletimestamp" }), @Index(name = "appnettraf_ts", columnNames = { "sampletimestamp" }) })
public class DataAppNetTraffic extends Data {

	private static final long serialVersionUID = -8226719353364583065L;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String name;

	@NotNull
	private Long txBytes;

	@NotNull
	private Long rxBytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTxBytes() {
		return txBytes;
	}

	public void setTxBytes(Long txBytes) {
		this.txBytes = txBytes;
	}

	public Long getRxBytes() {
		return rxBytes;
	}

	public void setRxBytes(Long rxBytes) {
		this.rxBytes = rxBytes;
	}
}
