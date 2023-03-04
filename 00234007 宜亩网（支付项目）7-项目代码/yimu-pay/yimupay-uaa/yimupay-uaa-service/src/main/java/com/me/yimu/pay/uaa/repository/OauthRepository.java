package com.me.yimu.pay.uaa.repository;



import java.util.List;

import com.me.yimu.pay.uaa.domain.OauthClientDetails;

public interface OauthRepository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);

    
}