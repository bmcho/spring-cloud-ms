package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.domain.auth.command.ChangePasswordCommand
import com.springcloudms.xauthservice.application.auth.usercase.ChangePasswordUseCase
import org.springframework.stereotype.Service

@Service
class ChangePasswordCommandHandler(
    private val changePasswordUseCase: ChangePasswordUseCase
) {
    fun handle(command: ChangePasswordCommand) {
        changePasswordUseCase.changePassword(command)
    }
}
