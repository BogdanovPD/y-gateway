package org.why.studio.gateway.services;

public interface AuthService {

    String getAccessToken(String clientId, String clientSecret, String email, String pass);
}
