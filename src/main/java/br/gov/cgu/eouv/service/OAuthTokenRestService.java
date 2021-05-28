package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.OAuthToken;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
public interface OAuthTokenRestService {
    OAuthToken saveOrUpdate(OAuthToken o);

    OAuthToken getLastAvaliable();

    boolean removed(OAuthToken o);
}
