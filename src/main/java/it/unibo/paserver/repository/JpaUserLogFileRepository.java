package it.unibo.paserver.repository;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.UserLogFile;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("jpaUserLogFileRepository")
public class JpaUserLogFileRepository implements UserLogFileRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaUserLogFileRepository.class);

	@Override
	public UserLogFile findById(long id) {
		return entityManager.find(UserLogFile.class, id);
	}

	@Override
	public UserLogFile save(UserLogFile userLogFile) {
		if (userLogFile.getId() != null) {
			logger.trace("Merging data {}", userLogFile.toString());
			return entityManager.merge(userLogFile);
		} else {
			logger.trace("Persisting data {}", userLogFile.toString());
			entityManager.persist(userLogFile);
			return userLogFile;
		}
	}

	@Override
	public List<UserLogFile> getUserLogFiles() {
		String hql = "from UserLogFile";
		TypedQuery<UserLogFile> query = entityManager.createQuery(hql,
				UserLogFile.class);
		return query.getResultList();
	}

	@Override
	public boolean deleteUserLogFile(long id) {
		UserLogFile userLogFile = findById(id);
		try {
			if (userLogFile != null) {
				entityManager.remove(userLogFile);
				return true;
			} else {
				logger.warn("Unable to find task {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public BinaryDocument getLogFile(long userLogFileId) {
		UserLogFile userLogFile = findById(userLogFileId);
		BinaryDocument bd = userLogFile.getBinaryDocument();
		Hibernate.initialize(bd.getContent());
		return bd;
	}

	@Override
	public UserLogFile merge(UserLogFile userLogFile) {
		return entityManager.merge(userLogFile);
	}

}
