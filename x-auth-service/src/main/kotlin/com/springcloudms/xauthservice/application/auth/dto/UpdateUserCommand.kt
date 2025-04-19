package com.springcloudms.xauthservice.application.auth.dto

data class UpdateUserCommand(
    val userId: Long,
    val password: String,
    val userName: String? = null,
)
