package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
@SuppressWarnings("Duplicates")
@Repository("jpaManifestacaoRespostaRepository")
public class JpaManifestacaoRespostaRepository implements ManifestacaoRespostaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ManifestacaoResposta saveOrUpdate(ManifestacaoResposta m) {
        if (m.getId() != null && m.getId() != 0 && find(m.getId()) != null) {
            m.setUpdateDate(new DateTime());
            return entityManager.merge(m);
        } else {
            m.setCreationDate(new DateTime());
            m.setUpdateDate(new DateTime());
            entityManager.persist(m);
            return m;
        }
    }

    @Override
    public ManifestacaoResposta find(Long id) {
        return null;
    }

    @Override
    public List<ManifestacaoResposta> fetchAll() {
        return null;
    }

    @Override
    public List<ManifestacaoResposta> filter(String search) {
        return null;
    }

    @Override
    public List<ManifestacaoResposta> fetchAllByRelationId(Long relationshipId) {
        String hql = "SELECT m FROM ManifestacaoResposta m WHERE m.relationshipId=:relationshipId";
        TypedQuery<ManifestacaoResposta> query = entityManager.createQuery(hql, ManifestacaoResposta.class);
        query.setParameter("relationshipId", relationshipId);
        List<ManifestacaoResposta> res = query.getResultList();
        return res;
    }


    @Override
    public ManifestacaoResposta getLastManifestacaoResposta(Long relationshipId) {
        String hql = "SELECT m FROM ManifestacaoResposta m WHERE m.relationshipId=:relationshipId ORDER BY m.creationDate DESC";
        //System.out.println(hql);
        TypedQuery<ManifestacaoResposta> query = entityManager.createQuery(hql, ManifestacaoResposta.class);
        query.setParameter("relationshipId", relationshipId);
        query.setMaxResults(1);
        List<ManifestacaoResposta> res = query.getResultList();
        return res != null && res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public boolean deleteAll(Long id) {
        Query query = entityManager.createQuery("DELETE FROM ManifestacaoResposta m WHERE m.relationshipId=:relationshipId");
        int deletedCount = query.setParameter("relationshipId", id).executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public boolean itContaimItem(Long relationshipId, DateTime dataResposta) {
        String hql = "SELECT m FROM ManifestacaoResposta m WHERE m.relationshipId=:relationshipId AND m.dataResposta=:dataResposta";
        TypedQuery<ManifestacaoResposta> query = entityManager.createQuery(hql, ManifestacaoResposta.class);
        query.setParameter("relationshipId", relationshipId);
        query.setParameter("dataResposta", dataResposta);
        query.setMaxResults(1);
        List<ManifestacaoResposta> res = query.getResultList();
        return res.size() > 0;
    }

    @Override
    public boolean itContaimItemById(Long relationshipId, Integer idRespostaManifestacao) {
        String hql = "SELECT m FROM ManifestacaoResposta m WHERE m.relationshipId=:relationshipId AND m.IdRespostaManifestacao=:idRespostaManifestacao";
        TypedQuery<ManifestacaoResposta> query = entityManager.createQuery(hql, ManifestacaoResposta.class);
        query.setParameter("relationshipId", relationshipId);
        query.setParameter("idRespostaManifestacao", idRespostaManifestacao);
        query.setMaxResults(1);
        List<ManifestacaoResposta> res = query.getResultList();
        return res.size() > 0;
    }
}
