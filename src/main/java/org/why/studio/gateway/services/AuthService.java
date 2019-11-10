package org.why.studio.gateway.services;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.why.studio.gateway.dto.UserDto;

public interface AuthService {

    OAuth2AccessToken getAccessToken(String clientId, String clientSecret, String email, String pass);

    UserDto getUserInfo(String userId);

    String getUserUuidFromAccessToken();

    String getTokenValue();
}
