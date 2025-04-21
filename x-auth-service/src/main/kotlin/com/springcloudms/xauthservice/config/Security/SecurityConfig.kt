package com.springcloudms.xauthservice.config.Security

import com.springcloudms.xauthservice.infrastructure.jwt.JwtProvider
import com.springcloudms.xauthservice.infrastructure.persistence.PasswordManager
import io.netty.handler.codec.http.HttpMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: ReactiveUserDetailsService,
    private val passwordManager: PasswordManager,
) {

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        val manager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        manager.setPasswordEncoder(passwordManager.getPasswordEncoder())
        return manager
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeExchange {
                it.pathMatchers("v1/auth/login", "v1/auth/register", "v1/auth/refresh").permitAll()
                it.anyExchange().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
//                    jwt.jwtAuthenticationConverter(JwtAuthenticationConverter(jwtProvider))
                }
            }
            .build()
    }
}