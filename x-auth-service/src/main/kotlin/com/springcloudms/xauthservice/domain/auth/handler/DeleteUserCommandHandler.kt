package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.domain.auth.command.DeleteUserCommand
import com.springcloudms.xauthservice.application.auth.usercase.DeleteUserUseCase
import org.springframework.stereotype.Service

@Service
class DeleteUserCommandHandler(
    private val deleteUserUseCase: DeleteUserUseCase
) {
    fun handle(command: DeleteUserCommand) {
        deleteUserUseCase.delete(command)
    }
}