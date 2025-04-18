package com.springcloudms.xauthservice.application.auth.usercase

import com.springcloudms.xauthservice.domain.auth.command.UpdateUserCommand
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import com.springcloudms.xauthservice.infrastructure.passwordEncorder.PasswordManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UpdateUserUseCase(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager,
) {

    @Transactional
    fun update(command: UpdateUserCommand) {
        val user = userRepository.findById(command.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        passwordManager.validatePassword(command.password, user.password)
        command.userName?.let {
            user.changeUserName(it)
        }
    }
}