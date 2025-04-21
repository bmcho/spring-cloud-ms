package com.springcloudms.xauthservice.application.auth.dto

import com.springcloudms.xauthservice.domain.auth.command.CreateUserCommand

data class RegisterRequestDto(
    val username: String,
    val password: String,
    val email: String,
) {
    fun toCommand() = CreateUserCommand(
        username = username,
        password = password,
        email = email,
        roles = listOf("ROLE_USER")
    )
}

