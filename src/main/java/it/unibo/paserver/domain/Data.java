package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Data implements Serializable {

	private static final long serialVersionUID = -7022941042935851846L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "data_id")
	private Long id;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime sampleTimestamp;

	@NotNull
	@Column(name = "received_timestamp")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime dataReceivedTimestamp;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getSampleTimestamp() {
		return sampleTimestamp;
	}

	public void setSampleTimestamp(DateTime sampleTimestamp) {
		this.sampleTimestamp = sampleTimestamp;
	}

	public DateTime getDataReceivedTimestamp() {
		return dataReceivedTimestamp;
	}

	public void setDataReceivedTimestamp(DateTime dataReceivedTimestamp) {
		this.dataReceivedTimestamp = dataReceivedTimestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		if (id != null) {
			hash += id * 37;
		}
		if (sampleTimestamp != null) {
			hash += sampleTimestamp.toDate().getTime();
		}
		if (user != null) {
			hash += user.getId() * 101;
		}
		return hash;
	}
}
