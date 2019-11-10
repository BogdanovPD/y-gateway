package org.why.studio.gateway.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.AuthService;
import org.why.studio.gateway.services.util.RestHelperService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestHelperService restHelperService;
    @Value("${service.auth.token-url}")
    private String accessTokenUrl;

    @Value("${service.auth.user-info}")
    private String userInfoUrl;

    @Override
    public OAuth2AccessToken getAccessToken(String clientId, String clientSecret, String email, String pass) {
        var parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", "password");
        parameters.add("username", email);
        parameters.add("password", pass);
        return restHelperService.sendPostRequestWithBasicAuth(accessTokenUrl, parameters,
                MediaType.APPLICATION_FORM_URLENCODED, new DefaultOAuth2AccessToken(""), clientId, clientSecret);
    }

    @Override
    public UserDto getUserInfo(String userId) {
        return restHelperService.sendGetRequest(
                UriComponentsBuilder.fromUriString(userInfoUrl.concat(userId)), new UserDto());
    }

    @Override
    public String getUserUuidFromAccessToken() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        return (String) tokenAttributes.get("user_uuid");
    }

    @Override
    public String getTokenValue() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
    }

    private Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((JwtAuthenticationToken) authentication).getTokenAttributes();
    }
}
