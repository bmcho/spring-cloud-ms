package com.springcloudms.xauthservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/auth/login").permitAll()   // 로그인 요청은 인증 없이 허용
                    .anyRequest().authenticated()                // 나머지 요청은 인증 필요
            }
            .formLogin { it.disable() } // formLogin도 명시적으로 꺼주는 게 깔끔합니다.
            .httpBasic { it.disable() }
            .build()
    }
}