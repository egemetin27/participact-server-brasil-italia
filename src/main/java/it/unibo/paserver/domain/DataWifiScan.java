package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataWifiScan", indexes = {
		@Index(name = "wifi_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "wifi_ts", columnNames = { "sampletimestamp" }) })
public class DataWifiScan extends Data {

	private static final long serialVersionUID = -593097734513572168L;

	@NotNull
	private String bssid;
	@NotNull
	private String ssid;
	@NotNull
	private String capabilities;
	@NotNull
	private int frequency;
	@NotNull
	private int level;

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
