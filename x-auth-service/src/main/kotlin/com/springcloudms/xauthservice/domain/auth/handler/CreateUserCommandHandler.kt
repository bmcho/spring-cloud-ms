package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.domain.auth.command.CreateUserCommand
import com.springcloudms.xauthservice.application.auth.usercase.CreateUserUseCase
import org.springframework.stereotype.Service

@Service
class CreateUserCommandHandler(
    private val createUserUseCase: CreateUserUseCase
) {
    fun handle(command: CreateUserCommand): Long {
        return createUserUseCase.create(command)
    }
}
