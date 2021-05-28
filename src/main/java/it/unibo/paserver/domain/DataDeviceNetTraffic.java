package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataDeviceNetTraffic", indexes = { @Index(name = "devnet_user_ts", columnNames = { "user_id", "sampletimestamp" }), @Index(name = "devnet_ts", columnNames = { "sampletimestamp" }) })
public class DataDeviceNetTraffic extends Data {

	private static final long serialVersionUID = -3206231050724684881L;

	@NotNull
	private Long txBytes;

	@NotNull
	private Long rxBytes;
	@Column(nullable = true)
	private String networkInterface;
	private float networkUptime = 0.0F;
	
	private Long rxPackets = 0L;
	private Long txPackets = 0L;

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

	/**
	 * @return the networkInterface
	 */
	public String getNetworkInterface() {
		return networkInterface;
	}

	/**
	 * @param networkInterface the networkInterface to set
	 */
	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}

	/**
	 * @return the networkUptime
	 */
	public float getNetworkUptime() {
		return networkUptime;
	}

	/**
	 * @param networkUptime the networkUptime to set
	 */
	public void setNetworkUptime(float networkUptime) {
		this.networkUptime = networkUptime;
	}

	/**
	 * @return the rxPackets
	 */
	public Long getRxPackets() {
		return rxPackets;
	}

	/**
	 * @param rxPackets the rxPackets to set
	 */
	public void setRxPackets(Long rxPackets) {
		this.rxPackets = rxPackets;
	}

	/**
	 * @return the txPackets
	 */
	public Long getTxPackets() {
		return txPackets;
	}

	/**
	 * @param txPackets the txPackets to set
	 */
	public void setTxPackets(Long txPackets) {
		this.txPackets = txPackets;
	}
}
