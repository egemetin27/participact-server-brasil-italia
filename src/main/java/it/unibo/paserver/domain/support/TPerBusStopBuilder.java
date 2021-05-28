package it.unibo.paserver.domain.support;

import java.util.List;

import org.joda.time.DateTime;

import it.unibo.tper.domain.TPerBusStop;


public class TPerBusStopBuilder extends EntityBuilder<TPerBusStop>{


	@Override
	void initEntity() {
		entity = new TPerBusStop();		
	}

	@Override
	TPerBusStop assembleEntity() {
		return entity;
	}
	
	public TPerBusStopBuilder setCode(Integer code)
	{
		entity.setCode(code);
		return this;
	}
	
	public TPerBusStopBuilder setName(String name)
	{
		entity.setName(name);
		return this;
	}
	
	public TPerBusStopBuilder setMunicipality(String municipality)
	{
		entity.setMunicipality(municipality);
		return this;
	}
	
	public TPerBusStopBuilder setCoordinateX(Integer coordinateX)
	{
		entity.setCoordinateX(coordinateX);
		return this;
	}
	
	public TPerBusStopBuilder setCoordinateY(Integer coordinateY)
	{
		entity.setCoordinateY(coordinateY);
		return this;
	}
	
	public TPerBusStopBuilder setSite(String site)
	{
		entity.setSite(site);
		return this;
	}
	
	public TPerBusStopBuilder setZoneCode(Integer zoneCode)
	{
		entity.setZoneCode(zoneCode);
		return this;
	}
	
	public TPerBusStopBuilder setLatitude(Double latitude)
	{
		entity.setLatitude(latitude);
		return this;
	}
	
	public TPerBusStopBuilder setLongitude(Double longitude)
	{
		entity.setLongitude(longitude);
		return this;
	}
	
	public TPerBusStopBuilder setCreationTime(DateTime creationTime)
	{
		entity.setCreationTime(creationTime);
		return this;
	}
	
	
	public TPerBusStopBuilder setId(Long id)
	{
		entity.setId(id);
		return this;
	}
	
	public TPerBusStopBuilder setLines(List<String> lines)
	{
		entity.setLines(lines);
		return this;
	}
	
	


}
