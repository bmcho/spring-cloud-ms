package com.springcloudms.xconfigserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer


@SpringBootApplication
@EnableConfigServer
class XConfigServerApplication

fun main(args: Array<String>) {
    runApplication<XConfigServerApplication>(*args)
}