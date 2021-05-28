package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.support.Pipeline.Type;
import it.unibo.paserver.repository.PipelineDescriptionRepository;

@Service
@Transactional(readOnly = true)
public class PipelineDescriptionServiceImpl implements PipelineDescriptionService {

	@Autowired
	PipelineDescriptionRepository pipelineDescriptionRepository;
	
	@Override
	public PipelineDescription getPipelineDescription(long id) {
		return pipelineDescriptionRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public PipelineDescription save(PipelineDescription pipelineDescription) {
		return pipelineDescriptionRepository.save(pipelineDescription);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deletePipelineDescription(long id) {
		return pipelineDescriptionRepository.deletePipelineDescription(id);
	}

	@Override
	public PipelineDescription getPipelineDescription(Type type) {
		return pipelineDescriptionRepository.fetchByPipelineType(type);
	}

	@Override
	public List<PipelineDescription> getAllPipelineDescription() {
		return pipelineDescriptionRepository.findAll();
	}

}
