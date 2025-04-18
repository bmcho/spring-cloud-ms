package com.springcloudms.xauthservice.domain.auth.command

data class UpdateUserCommand(
    val userId: Long,
    val password: String,
    val userName: String? = null,
)
