package com.springcloudms.xauthservice.application.auth.usercase

import com.springcloudms.xauthservice.domain.auth.command.ChangePasswordCommand
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import com.springcloudms.xauthservice.infrastructure.passwordEncorder.PasswordManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ChangePasswordUseCase(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager
) {

    @Transactional
    fun changePassword(command: ChangePasswordCommand) {
        val user = userRepository.findById(command.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        passwordManager.validatePassword(command.oldPassword, user.password)
        user.changePassword(passwordManager.encode(command.newPassword))
    }
}