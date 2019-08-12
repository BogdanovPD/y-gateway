package org.why.studio.gateway.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final SecurityProperties securityProperties;

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(
                        "/oauth/token")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/api/schedules/services/**"
                ).hasAuthority("ROLE_SUPER_ADMIN")
                .antMatchers(
                        "/api/auth/user/admin/create/init"
                ).hasAuthority("ROLE_SUPER_ADMIN")

                .antMatchers(HttpMethod.POST,
                        "/api/schedules/services",
                        "/api/schedules/specialist/**/services"
                ).hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                .antMatchers(
                        "/api/auth/user/spec/create/init",
                        "/api/auth/user/all"
                ).hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")

                .antMatchers(
                        "/api/schedules/consultation-requests/spec/**",
                        "/api/schedules/consultation-requests/spec/**/approve",
                        "/api/schedules/consultation-requests/spec/**/reject",
                        "/api/schedules/specialist/**/client-requests",
                        "/api/schedules/specialist/**/clients",
                        "/api/schedules/specialist/**/clients/accept-request/**",
                        "/api/schedules/specialist/**/clients/reject-request/**"
                ).hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_SPECIALIST")

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public DefaultTokenServices tokenServices(final TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPublicKeyAsString());
        return converter;
    }

    private String getPublicKeyAsString() {
        try {
            return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String uuidPathPattern() {
        return "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
    }

    private String specAccess() {
        return "#oauth2.clientHasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_SPECIALIST')";
    }

    private String clientAccess() {
        return "#oauth2.clientHasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_SPECIALIST', 'ROLE_CLIENT')";
    }

}
