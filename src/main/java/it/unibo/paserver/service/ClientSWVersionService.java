package it.unibo.paserver.service;

import it.unibo.paserver.domain.ClientSWVersion;

import java.util.List;

public interface ClientSWVersionService {

	ClientSWVersion findById(long id);

	ClientSWVersion save(ClientSWVersion clientSWVersion);

	List<ClientSWVersion> getClientSWVersions();

	ClientSWVersion getLatestVersion();

	boolean deleteClientSWVersion(long id);

}
