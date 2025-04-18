package com.springcloudms.xauthservice.domain.auth.command

data class ChangePasswordCommand(
    val userId: Long,
    val oldPassword: String,
    val newPassword: String
)
