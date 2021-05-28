package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.OAuthToken;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
public interface OAuthTokenRepository {
    OAuthToken saveOrUpdate(OAuthToken o);

    OAuthToken getLastAvaliable();

    boolean removed(OAuthToken o);

    OAuthToken findById(Long id);
}
