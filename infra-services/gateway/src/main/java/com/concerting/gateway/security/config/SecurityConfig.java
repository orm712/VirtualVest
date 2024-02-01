package com.concerting.gateway.security.config;

import com.concerting.gateway.security.filter.JwtFilter;
import com.concerting.gateway.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception{
        //jwt 로그인
//        http.cors().and()
//                .csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .authorizeExchange()
//                .pathMatchers("/api/v1/auth/**").permitAll()
//                .anyExchange().permitAll()
//                .and()
//                .addFilterAt(new JwtFilter(jwtProvider), SecurityWebFiltersOrder.AUTHENTICATION)
//                .logout().disable();

        //Spring Security 새로운 방식
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(formLogin -> formLogin.disable())
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/api/v1/auth/**").permitAll()
                    .anyExchange().permitAll()
            )
            .addFilterAt(new JwtFilter(jwtProvider), SecurityWebFiltersOrder.AUTHENTICATION)
            .logout(logout -> logout.disable());

        return http.build();
    }
}
