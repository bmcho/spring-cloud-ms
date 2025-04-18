package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.domain.auth.command.AssignRoleCommand
import com.springcloudms.xauthservice.application.auth.usercase.AssignRoleUseCase
import org.springframework.stereotype.Service

@Service
class AssignRoleCommandHandler(
    private val assignRoleUseCase: AssignRoleUseCase,
) {
    fun handle(command: AssignRoleCommand) {
        assignRoleUseCase.assignRoles(command)
    }
}
