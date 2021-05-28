package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.support.Pipeline;

public interface PipelineDescriptionService {
	
	PipelineDescription getPipelineDescription(long id);
	PipelineDescription save(PipelineDescription pipelineDescription);
	boolean deletePipelineDescription(long id);
	PipelineDescription getPipelineDescription(Pipeline.Type type);
	List<PipelineDescription> getAllPipelineDescription();
	
}
