package com.springcloudms.xauthservice.domain.auth.command

data class CreateUserCommand(
    val username: String,
    val password: String,
    val email: String,
    val roles: List<String> = listOf("USER")
)
