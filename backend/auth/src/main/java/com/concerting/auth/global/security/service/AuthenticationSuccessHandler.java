package com.concerting.auth.global.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        // 이때 주어지는 authentication는 org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
        // OAuth2AuthenticationToken.getAuthorizedClientRegistrationId << provider id가 어떤건지(google인지, kakao인지) 알려줌
        System.out.println(((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId());
        System.out.println(((OAuth2User)authentication.getPrincipal()).getAttributes().toString());
        ((OAuth2User)authentication.getPrincipal()).getAttributes().forEach((s, o) -> {
            System.out.println(s);
            System.out.println(o.getClass().getName());
        });
        return Mono.empty();
    }
}
