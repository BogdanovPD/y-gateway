package org.why.studio.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final WhyAuthenticationProvider authenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) {
        authManager
                .authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .requestMatchers(new NegatedRequestMatcher(new AntPathRequestMatcher("/oauth/token")))
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/account/specName/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_SPECIALIST")
                .antMatchers("/account/user/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_USER")
                .antMatchers("/account/admin/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/personal")
                .permitAll()
                .and()
                .logout().permitAll();
    }
}
