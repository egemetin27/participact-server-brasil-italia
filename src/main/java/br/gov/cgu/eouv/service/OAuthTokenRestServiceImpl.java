package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.repository.OAuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
@Service
@Transactional(readOnly = true)
public class OAuthTokenRestServiceImpl implements OAuthTokenRestService {
    @Autowired
    OAuthTokenRepository repos;

    @Override
    @Transactional(readOnly = false)
    public OAuthToken saveOrUpdate(OAuthToken o) {
        return repos.saveOrUpdate(o);
    }

    @Override
    @Transactional(readOnly = false)
    public OAuthToken getLastAvaliable() {
        return repos.getLastAvaliable();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean removed(OAuthToken o) {
        return repos.removed(o);
    }
}
