package com.springcloudms.xauthservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@SpringBootApplication
class XAuthServerApplication

fun main(args: Array<String>) {
    runApplication<XAuthServerApplication>(*args)
}