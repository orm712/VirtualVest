package com.concerting.gateway.security.filter;

import com.concerting.gateway.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {
    private final JwtProvider jwtProvider;
    private static final String HEADER_KEY = "Authorization";
    private static final String PREFIX = "Bearer ";

    private String resolveToken(ServerHttpRequest request){
        String authToken = request.getHeaders().getFirst(HEADER_KEY);
        if(StringUtils.hasText(authToken) && authToken.startsWith(PREFIX)){
            return authToken.substring(PREFIX.length());
        }
        return null;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();

        log.info(request.getPath() + " " + request.getMethod());

        String authToken = resolveToken(request);

        Authentication authentication = null;
        ServerWebExchange serverWebExchange = null;
        if(StringUtils.hasText(authToken)){
            log.info("authToken : " + authToken);
            if(jwtProvider.validTokens(authToken)){
                authentication = jwtProvider.getAuthentication(authToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String email = authentication.getPrincipal().toString();
                log.info(email);

                serverWebExchange = exchange.mutate()
                        .request(builder -> builder.header("email", email))
                        .build();
            }
        }

        if(authentication != null){
            return chain.filter(serverWebExchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        } else{
            return chain.filter(exchange);
        }
    }

}