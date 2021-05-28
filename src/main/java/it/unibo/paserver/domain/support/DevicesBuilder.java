package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.Platform;

@Component
public class DevicesBuilder extends EntityBuilder<Devices> {

	@Override
	void initEntity() {
		entity = new Devices();
	}

	@Override
	Devices assembleEntity() {
		return entity;
	}
	
	//Alberto
	public Devices getEntity(){
		return entity;
	}

	public DevicesBuilder setAll(Long id, String brand, String model, String manufacturer, Platform platform, String display,
			String fingerprint, String hardware, String tags, String type, String changelist) {
		entity.setId(id);
		entity.setBrand(brand);
		entity.setModel(model);
		entity.setManufacturer(manufacturer);
		entity.setDisplay(display);
		entity.setFingerprint(fingerprint);
		entity.setHardware(hardware);
		entity.setTags(tags);
		entity.setType(type);
		entity.setChangelist(changelist);
		entity.setActive(true);
		entity.setCreationDate(new DateTime());
		entity.setUpdateDate(new DateTime());
		entity.setRemoved(false);
		entity.setPlatform(platform);
		return this;
	}
}