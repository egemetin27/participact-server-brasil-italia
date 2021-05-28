package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.support.Pipeline.Type;

@Repository("pipelineRepository")
public class JpaPipelineDescriptionRepository implements PipelineDescriptionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(JpaSensorRepository.class);

	@Override
	public PipelineDescription findById(long id) {
		return entityManager.find(PipelineDescription.class, id);
	}

	@Override
	public PipelineDescription save(PipelineDescription pipelineDescription) {
		logger.trace("Merging pipelineDescription {}", pipelineDescription.getType().toString());
		return entityManager.merge(pipelineDescription);
	}

	@Override
	public boolean deletePipelineDescription(long id) {
		PipelineDescription desc = findById(id);
		try {
			if (desc != null) {
				entityManager.remove(desc);
				return true;
			} else {
				logger.warn("Unable to find pipelineDescription {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public PipelineDescription fetchByPipelineType(Type type) {
		String hql = "select c from PipelineDescription c where c.type=:type";
		TypedQuery<PipelineDescription> query = entityManager.createQuery(hql, PipelineDescription.class).setParameter("type", type);
		List<PipelineDescription> descr = query.getResultList();
		return descr.size() == 1 ? descr.get(0) : null;
	}

	@Override
	public List<PipelineDescription> findAll() {
		String hql = "SELECT i FROM PipelineDescription i";
		TypedQuery<PipelineDescription> query = entityManager.createQuery(hql, PipelineDescription.class);
		List<PipelineDescription> i = query.getResultList();
		return i;
	}
	
}
