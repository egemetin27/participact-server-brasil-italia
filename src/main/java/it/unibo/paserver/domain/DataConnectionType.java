package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataConnectionType", indexes = {
		@Index(name = "connection_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "connection_ts", columnNames = { "sampletimestamp" }) })
public class DataConnectionType extends Data {

	private static final long serialVersionUID = 297871598994396339L;

	@NotNull
	private String connectionType;
	@NotNull
	private String mobileNetworkType;

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getMobileNetworkType() {
		return mobileNetworkType;
	}

	public void setMobileNetworkType(String mobileNetworkType) {
		this.mobileNetworkType = mobileNetworkType;
	}

}
