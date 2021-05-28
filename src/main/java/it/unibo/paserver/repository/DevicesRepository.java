package it.unibo.paserver.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.SensorTypeInfo;

public interface DevicesRepository {
	// Sava ou atualiza
	Devices saveOrUpdate(Devices i);

	// Busca pelo nome
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

	List<Devices> getDevicesWithSensors(ArrayList<SensorTypeInfo> sensType);

	Devices getDevice(Platform platform, String manufacturer, String modelCode);
}
