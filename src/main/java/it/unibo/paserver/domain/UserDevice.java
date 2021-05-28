package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDevice implements Serializable {

	private static final long serialVersionUID = -7993144759022689105L;
	
	@Id
	@Column(name = "user_device_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "device_id")
	private Devices device;
	
	@Column(columnDefinition = "TEXT")
	private String name;
	
	@NotNull
	private String imei;

	@NotNull
	private Long priority;

	@Column(columnDefinition = "TEXT")
	private String gcmId;

	public Devices getDevice() {
		return device;
	}

	public String getGcmId() {
		return gcmId;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getImei() {
		return imei;
	}

	public String getName() {
		return name;
	}

	public Long getPriority() {
		return priority;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		String sImei = getImei();
		if(sImei == null)
			return (int)Math.random(); 
		//done for a strange error (alberto)
		return sImei.hashCode();
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}	
	
	public void setUser(User user) {
		this.user = user;
	}
}
