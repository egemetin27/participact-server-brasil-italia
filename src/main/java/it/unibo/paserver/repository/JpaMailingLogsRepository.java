package it.unibo.paserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.MailingLogs;

@Repository("mailingLogsRepository")
public class JpaMailingLogsRepository implements MailingLogsRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MailingLogs saveOrUpdate(MailingLogs t) {

		if (t.getId() != null && t.getId() != 0) {
			t.setUpdateDate(new DateTime());
			return entityManager.merge(t);
		} else {
			t.setId(null);
			t.setCreationDate(new DateTime());
			t.setUpdateDate(new DateTime());
			entityManager.persist(t);
			return t;
		}
	}
	

	@Override
	public MailingLogs findByQrCoded(String qrcode, boolean qrused) {
		// HQL
		String hql = "SELECT t FROM MailingLogs t WHERE qrCodeToken=:qrcode AND qrCodeUsed=:qrused ";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class).setParameter("qrcode",qrcode).setParameter("qrused",qrused);
		// LIMIT
		query.setMaxResults(1);
		// return
		List<MailingLogs> t = query.getResultList();
		return t.size() == 1 ? t.get(0) : null;
	}

	@Override
	public MailingLogs findByTaskId(long taskId) {
		// HQL
		String hql = "SELECT t FROM MailingLogs t WHERE TaskId=:TaskId";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class).setParameter("TaskId",
				taskId);
		// LIMIT
		query.setMaxResults(1);
		// return
		List<MailingLogs> t = query.getResultList();
		return t.size() == 1 ? t.get(0) : null;
	}

	@Override
	public boolean deleteByTaskId(long taskId) {

		MailingLogs t = this.findByTaskId(taskId);
		try {
			if (t != null) {
				entityManager.remove(t);
				return true;
			} else {
				System.out.println("Unable to find MailingLogs {} " + taskId + "");
			}
		} catch (Exception e) {
			System.out.println("Exception: {} " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<MailingLogs> findAllByTaskId(long taskId) {
		// HQL
		String hql = "SELECT t FROM MailingLogs t WHERE TaskId=:TaskId";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class).setParameter("TaskId",
				taskId);
		// return
		List<MailingLogs> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<MailingLogs> findAllTaskIdAndUserId(long taskId, long userId) {
		// HQL
		String hql = "SELECT t FROM MailingLogs t "
				+ " WHERE TaskId=:TaskId AND UserId=:UserId ";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class).setParameter("TaskId", taskId).setParameter("UserId", userId);
		// return
		List<MailingLogs> list = query.getResultList();
		return list;
	}

	@Override
	public List<MailingLogs> findAllByEmailId(Long emailId, Long count, boolean isPushed) {
		// HQL,
		String hql = "SELECT t FROM MailingLogs t"
				+ "	WHERE emailId=:emailId AND deliveryDate<=:deliveryDate AND isAccepted=:isAccepted AND isProcessed=:isProcessed AND isQueued=:isQueued AND isPushed=:isPushed AND removed=:removed";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class);
		//System.out.println(hql);
		// WHERE
		query.setParameter("emailId", emailId);
		query.setParameter("removed", false);
		query.setParameter("isAccepted", true);
		query.setParameter("isProcessed", false);
		query.setParameter("isQueued", true);
		query.setParameter("isPushed", isPushed);
		DateTime now =DateTime.now().withZone(DateTimeZone.forID("UTC"));
		query.setParameter("deliveryDate", now);
		// LIMIT
		System.out.println(" " + count.intValue());
		query.setMaxResults(count!=null?count.intValue():500);		
		// return
		List<MailingLogs> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<MailingLogs> findAllByEmailId(Long emailId, Long count) {
		// HQL,
		String hql = "SELECT t FROM MailingLogs t"
				+ "	WHERE emailId=:emailId AND deliveryDate<=:deliveryDate AND isAccepted=:isAccepted AND isProcessed=:isProcessed AND isQueued=:isQueued AND removed=:removed";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class);
		//System.out.println(hql);
		// WHERE
		query.setParameter("emailId", emailId);
		query.setParameter("removed", false);
		query.setParameter("isAccepted", true);
		query.setParameter("isProcessed", false);
		query.setParameter("isQueued", true);
		DateTime now =DateTime.now().withZone(DateTimeZone.forID("UTC"));
		query.setParameter("deliveryDate", now);
		// LIMIT
		System.out.println(" " + count.intValue());
		query.setMaxResults(count!=null?count.intValue():500);		
		// return
		List<MailingLogs> list = query.getResultList();
		return list;
	}

	@Override
	public List<MailingLogs> findAllResendByEmailId(Long emailId, Long limitSending) {
		// HQL,
		String hql = "SELECT t FROM MailingLogs t"
				+ "	WHERE emailId=:emailId AND deliveryDate <= NOW() "
				+ "	AND isAccepted=:isAccepted "
				+ "	AND isProcessed=:isProcessed "
				+ "	AND isQueued=:isQueued "
				+ "	AND isPushed=:isPushed "				
				+ "	AND isDelivered=:isDelivered "
				+ "	AND isResend=:isResend "
				+ "	AND isDropped=:isDropped "
				+ "	AND isRejected=:isRejected "
				+ "	AND removed=:removed";
		TypedQuery<MailingLogs> query = entityManager.createQuery(hql, MailingLogs.class);
		// WHERE
		query.setParameter("emailId", emailId);
		query.setParameter("removed", false);
		query.setParameter("isAccepted", true);
		query.setParameter("isProcessed", true);
		query.setParameter("isQueued", false);
		query.setParameter("isPushed", true);
		query.setParameter("isDelivered", false);
		query.setParameter("isResend", true);		
		query.setParameter("isDropped", false);
		query.setParameter("isRejected", false);
		// LIMIT
		query.setMaxResults(limitSending!=null?limitSending.intValue():500);		
		// return
		List<MailingLogs> list = query.getResultList();
		return list;
	}
}