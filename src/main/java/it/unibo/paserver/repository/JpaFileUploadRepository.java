package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.FileUpload;

@Repository("fileUploadRepository")
public class JpaFileUploadRepository implements FileUploadRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public FileUpload findById(long id) {
		
		return entityManager.find(FileUpload.class, id);
	}

	@Override
	public FileUpload findByFilename(String filename) {
		
		String hql = "SELECT f FROM FileUpload f WHERE f.filename LIKE:filename";
		TypedQuery<FileUpload> query = entityManager.createQuery(hql, FileUpload.class).setParameter("filename", filename.toString());
		List<FileUpload> f = query.getResultList();
		return f.size() == 1 ? f.get(0) : null;
	}

	@Override
	public FileUpload saveOrUpdate(FileUpload f) {
		
		if (f.getId() != 0) {
			f.setUpdateDate(new DateTime());
			return entityManager.merge(f);
		} else {
			f.setId(0);
			f.setCreationDate(new DateTime());
			f.setUpdateDate(new DateTime());
			entityManager.persist(f);
			return f;
		}
	}
}