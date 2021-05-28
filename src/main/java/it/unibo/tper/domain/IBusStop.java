package it.unibo.tper.domain;

import org.joda.time.DateTime;

public interface IBusStop {
	
	Long getId();
	Integer getCode();
	String getName();
	String getMunicipality();
	Double getLatitude();
	Double getLongitude();
	DateTime getCreationTime();

	void setCreationTime(DateTime creationTime);	
	void setId(Long id);
	void setCode(Integer code);
	void setName(String name);
	void setMunicipality(String municipality);
	void setLatitude(Double latitude);
	void setLongitude(Double longitude);


}
