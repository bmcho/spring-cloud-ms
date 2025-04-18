package com.springcloudms.xauthservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("jwt")
class JwtProperties {
    lateinit var privateKeyPath: String // private.pem 파일 경로
    lateinit var publicKeyPath: String  // public.pem 파일 경로
    var expirationMs: Long = 600000     // 기분 10분
}