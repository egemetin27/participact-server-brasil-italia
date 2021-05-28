package it.unibo.paserver.service;

import it.unibo.paserver.domain.ClientSWVersion;
import it.unibo.paserver.repository.ClientSWVersionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientSWVersionServiceImpl implements ClientSWVersionService {

	@Autowired
	ClientSWVersionRepository clientSWVersionRepository;

	@Override
	@Transactional(readOnly = true)
	public ClientSWVersion findById(long id) {
		return clientSWVersionRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public ClientSWVersion save(ClientSWVersion clientSWVersion) {
		return clientSWVersionRepository.save(clientSWVersion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientSWVersion> getClientSWVersions() {
		return clientSWVersionRepository.getClientSWVersions();
	}

	/**
	 * Retrieves the latest available version or null if there are no versions
	 * available.
	 */
	@Override
	@Transactional(readOnly = true)
	public ClientSWVersion getLatestVersion() {
		return clientSWVersionRepository.getLatestVersion();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteClientSWVersion(long id) {
		return clientSWVersionRepository.deleteClientSWVersion(id);
	}

}
