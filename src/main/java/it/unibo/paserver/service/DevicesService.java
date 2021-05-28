package it.unibo.paserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.SensorTypeInfo;

public interface DevicesService {
	// Sava ou atualiza
	Devices saveOrUpdate(Devices i);

	// Busca pelo brand
	Devices findByBrand(String brand);

	// Busca pelo id
	Devices findById(long id);

	// Todos os itens
	List<Devices> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	// Busca customizada
	List<Object[]> search(String brand, String model, String manufacturer, String tags, PageRequest pageable);

	// Total da busca customizada
	long searchTotal(String brand, String model, String manufacturer, String tags);

	List<Devices> getDevicesWithSensors(ArrayList<SensorTypeInfo> stl);
}
