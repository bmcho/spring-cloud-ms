package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.domain.auth.command.UpdateUserCommand
import com.springcloudms.xauthservice.application.auth.usercase.UpdateUserUseCase
import org.springframework.stereotype.Service

@Service
class UpdateUserCommandHandler(
    private val updateUserUseCase: UpdateUserUseCase
) {
    fun handle(command: UpdateUserCommand) {
        updateUserUseCase.update(command)
    }
}
