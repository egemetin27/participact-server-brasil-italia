package it.unibo.paserver.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MinimalSensorDetail extends AbstractSensorDetail {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString(){
		if (getSensorType() == null)
			return "Name: "+getName();
		else
			return "Name: "+getName()+"\nSensor: "+getSensorType().toString().toLowerCase();
	}

	@Override
	public String toHtmlString() {
		String s = this.toString();
		s = s.replace("\n", " - ");
		return s;
	}

}
