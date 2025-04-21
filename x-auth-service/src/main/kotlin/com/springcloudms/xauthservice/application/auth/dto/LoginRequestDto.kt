package com.springcloudms.xauthservice.application.auth.dto

data class LoginRequestDto(
    val email: String,
    val password: String
) {
}