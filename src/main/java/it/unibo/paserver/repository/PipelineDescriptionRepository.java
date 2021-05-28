package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.support.Pipeline;

public interface PipelineDescriptionRepository {
	
	PipelineDescription findById(long id);
	PipelineDescription save(PipelineDescription pipelineDescription);
	boolean deletePipelineDescription(long id);
	PipelineDescription fetchByPipelineType(Pipeline.Type type);
	List<PipelineDescription> findAll();
	
}
