package com.springcloudms.xauthservice.domain.auth.command

data class AssignRoleCommand(
    val userId: Long,
    val roles: List<String>
)
