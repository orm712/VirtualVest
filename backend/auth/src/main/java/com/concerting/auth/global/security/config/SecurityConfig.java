package com.concerting.auth.global.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler = new AuthenticationSuccessHandler();
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception{
//        http.authorizeHttpRequests(authz -> authz
//                .anyRequest().authenticated()
//        ).oauth2Login(oauth2 -> oauth2
//                .loginPage("/oauth2/authorization/google")
//                .defaultSuccessUrl("http://localhost:8080/user")
//                .failureUrl("/oauth2/authorization/google"));
        http.authorizeExchange((exchanges) -> exchanges
                        .pathMatchers("/api/*").authenticated())
                .oauth2Login(oAuth2LoginSpec -> oAuth2LoginSpec.authenticationSuccessHandler(authenticationSuccessHandler));
        return http.build();
    }
}

