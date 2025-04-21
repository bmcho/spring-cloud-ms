package com.springcloudms.xauthservice.application.auth.dto

data class AuthTokenResponseDto(
    val accessToken: String,
    val refreshToken: String
) {
}