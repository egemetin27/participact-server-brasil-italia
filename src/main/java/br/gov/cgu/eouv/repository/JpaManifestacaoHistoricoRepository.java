package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
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
@Repository("jpaManifestacaoHistoricoRepository")
public class JpaManifestacaoHistoricoRepository implements ManifestacaoHistoricoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ManifestacaoHistorico saveOrUpdate(ManifestacaoHistorico m) {
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
    public ManifestacaoHistorico find(Long id) {
        return null;
    }

    @Override
    public List<ManifestacaoHistorico> fetchAll() {
        return null;
    }

    @Override
    public List<ManifestacaoHistorico> filter(String search) {
        return null;
    }

    @Override
    public List<ManifestacaoHistorico> fetchAllByRelationId(Long relationshipId) {
        String hql = "SELECT m FROM ManifestacaoHistorico m WHERE m.relationshipId=:relationshipId";
        TypedQuery<ManifestacaoHistorico> query = entityManager.createQuery(hql, ManifestacaoHistorico.class);
        query.setParameter("relationshipId", relationshipId);
        List<ManifestacaoHistorico> res = query.getResultList();
        return res;
    }

    @Override
    public boolean deleteAll(Long id) {
        Query query = entityManager.createQuery("DELETE FROM ManifestacaoHistorico m WHERE m.relationshipId=:relationshipId");
        int deletedCount = query.setParameter("relationshipId", id).executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public boolean itContaimItem(Long relationshipId, DateTime dataAcao) {
        String hql = "SELECT m FROM ManifestacaoHistorico m WHERE m.relationshipId=:relationshipId AND m.dataAcao=:dataAcao";
        TypedQuery<ManifestacaoHistorico> query = entityManager.createQuery(hql, ManifestacaoHistorico.class);
        query.setParameter("relationshipId", relationshipId);
        query.setParameter("dataAcao", dataAcao);
        ///System.out.println(String.format("relationshipId %s dataAcao %s ", relationshipId, dataAcao.toString()));
        query.setMaxResults(1);
        List<ManifestacaoHistorico> res = query.getResultList();
        return res.size() > 0;
    }
}
