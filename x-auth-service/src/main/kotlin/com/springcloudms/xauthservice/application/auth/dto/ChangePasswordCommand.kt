package com.springcloudms.xauthservice.application.auth.dto

data class ChangePasswordCommand(
    val userId: Long,
    val oldPassword: String,
    val newPassword: String
)
