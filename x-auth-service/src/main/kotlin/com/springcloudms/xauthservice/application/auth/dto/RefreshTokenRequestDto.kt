package com.springcloudms.xauthservice.application.auth.dto

import com.springcloudms.xauthservice.domain.auth.command.CreateUserCommand

data class RefreshTokenRequestDto(
    val refreshToken: String
) {
}

