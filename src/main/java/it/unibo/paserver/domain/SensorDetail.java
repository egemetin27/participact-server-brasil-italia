package it.unibo.paserver.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SensorDetail extends AbstractSensorDetail {
	
	private static final long serialVersionUID = 1L;
	
	private String vendor;
	private long version;
	private long type;
	private float maxRange;
	private float resolution;
	private float power;
	private long minDelay;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public float getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(float maxRange) {
		this.maxRange = maxRange;
	}

	public float getResolution() {
		return resolution;
	}

	public void setResolution(float resolution) {
		this.resolution = resolution;
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}

	public long getMinDelay() {
		return minDelay;
	}

	public void setMinDelay(long minDelay) {
		this.minDelay = minDelay;
	}
	
	@Override
	public String toString(){
		if (getSensorType() == null)
			return "Name: "+getName()+"\nVendor: "+vendor+"\nVersion: "+version+"\nType: "+type+"\nMax Range: "+maxRange+"\nResolution: "+resolution+""
				+ "\nPower: "+power+"\nMin Delay: "+minDelay;
		else
			return "Name: "+getName()+"\nSensor: "+getSensorType().toString().toLowerCase()+"\nVendor: "+vendor+"\nVersion: "+version+"\nType: "+type+"\nMax Range: "+maxRange+"\nResolution: "+resolution+""
				+ "\nPower: "+power+"\nMin Delay: "+minDelay;
	}

	@Override
	public String toHtmlString() {
		String s = this.toString();
		s = s.replace("\n", " - ");
		return s;
	}

}
