package org.why.studio.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.AuthService;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class WhyAuthenticationProvider implements AuthenticationProvider {

    private final AuthService authService;

    @Value("${web-client-id}")
    private String webClientId;
    @Value("${web-client-secret}")
    private String webClientSecret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String email = token.getName().toLowerCase();
        String password = (String) token.getCredentials();

        OAuth2AccessToken accessToken;
        try {
            accessToken = authService.getAccessToken(webClientId, webClientSecret, email, password);
        } catch (Exception ex) {
            log.warn("User credentials are not valid: {}", email);
            throw new UsernameNotFoundException("User credentials are not valid.");
        }
        String userId = (String) accessToken.getAdditionalInformation().get("user_uuid");
        UserDto userInfo = authService.getUserInfo(userId);
        return new JwtAuthenticationToken(new Jwt(accessToken.getValue(), Instant.now(),
                Instant.now().plusSeconds(accessToken.getExpiresIn()), Map.of("typ", "jwt"),
                accessToken.getAdditionalInformation()),
                Set.of(new SimpleGrantedAuthority(userInfo.getRole())));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
