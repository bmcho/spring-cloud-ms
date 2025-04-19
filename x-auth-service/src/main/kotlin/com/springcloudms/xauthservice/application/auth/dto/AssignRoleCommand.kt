package com.springcloudms.xauthservice.application.auth.dto

data class AssignRoleCommand(
    val userId: Long,
    val roles: List<String>
)
