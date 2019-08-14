package org.why.studio.gateway.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.why.studio.gateway.services.AuthService;
import org.why.studio.gateway.services.util.RestHelperService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestHelperService restHelperService;
    @Value("${service.auth.token-url}")
    private String accessTokenUrl;

    @Override
    public String getAccessToken(String clientId, String clientSecret, String email, String pass) {
        var parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", "password");
        parameters.add("username", email);
        parameters.add("password", pass);
        return restHelperService.sendPostRequestWithBasicAuth(accessTokenUrl, parameters,
                MediaType.APPLICATION_FORM_URLENCODED, "", clientId, clientSecret);
    }
}
