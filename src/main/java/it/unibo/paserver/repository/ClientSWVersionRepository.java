package it.unibo.paserver.repository;

import it.unibo.paserver.domain.ClientSWVersion;

import java.util.List;

public interface ClientSWVersionRepository {

	ClientSWVersion findById(long id);

	ClientSWVersion save(ClientSWVersion clientSWVersion);

	List<ClientSWVersion> getClientSWVersions();

	ClientSWVersion getLatestVersion();

	boolean deleteClientSWVersion(long id);

}
