package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.Institutions;;
@Component
public class InstitutionsBuilder extends EntityBuilder<Institutions> {

	@Override
	void initEntity() {
		
		entity = new Institutions();
	}

	@Override
	Institutions assembleEntity() {
		
		return entity;
	}

	public InstitutionsBuilder setAll(String name, String phone, String email, String contact, String address,
			String addressNumber, String addressCity, String addressState, String addressCountry,
			String addressPostalCode, String mapLat, String mapLng) {
		this.setName(name);
		this.setPhone(phone);
		this.setEmail(email);
		this.setContact(contact);
		this.setAddress(address);
		this.setAddressNumber(addressNumber);
		this.setAddressCity(addressCity);
		this.setAddressState(addressState);
		this.setAddressCountry(addressCountry);
		this.setAddressPostalCode(addressPostalCode);
		this.setMapLat(mapLat);
		this.setMapLng(mapLat);
		this.setActive(true);
		this.setCreationDate(new DateTime());
		this.setUpdateDate(new DateTime());
		return this.setRemoved(false);
	}

	/**
	 * Setters/Getters
	 */
	public InstitutionsBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public InstitutionsBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	public InstitutionsBuilder setPhone(String phone) {
		entity.setPhone(phone);
		return this;
	}

	public InstitutionsBuilder setEmail(String email) {
		entity.setEmail(email);
		return this;
	}

	public InstitutionsBuilder setContact(String contact) {
		entity.setContact(contact);
		return this;
	}

	public InstitutionsBuilder setAddress(String address) {
		entity.setAddress(address);
		return this;
	}

	public InstitutionsBuilder setAddressNumber(String addressNumber) {
		entity.setAddressNumber(addressNumber);
		return this;
	}

	public InstitutionsBuilder setAddressCity(String addressCity) {
		entity.setAddressCity(addressCity);
		return this;
	}

	public InstitutionsBuilder setAddressState(String addressState) {
		entity.setAddressState(addressState);
		return this;
	}

	public InstitutionsBuilder setAddressCountry(String addressCountry) {
		entity.setAddressCountry(addressCountry);
		return this;
	}

	public InstitutionsBuilder setAddressPostalCode(String addressPostalCode) {
		entity.setAddressPostalCode(addressPostalCode);
		return this;
	}

	public InstitutionsBuilder setMapLat(String mapLat) {
		entity.setMapLat(mapLat);
		return this;
	}

	public InstitutionsBuilder setMapLng(String mapLng) {
		entity.setMapLng(mapLng);
		return this;
	}

	public InstitutionsBuilder setActive(boolean isActive) {
		entity.setActive(isActive);
		return this;
	}

	public InstitutionsBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public InstitutionsBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public InstitutionsBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}