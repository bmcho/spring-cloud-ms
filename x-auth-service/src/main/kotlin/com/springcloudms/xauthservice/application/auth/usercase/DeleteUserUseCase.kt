package com.springcloudms.xauthservice.application.auth.usercase

import com.springcloudms.xauthservice.domain.auth.command.DeleteUserCommand
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class DeleteUserUseCase(
    private val userRepository: UserRepository
) {

    @Transactional
    fun delete(command: DeleteUserCommand) {
        if (!userRepository.existsById(command.userId)) {
            throw IllegalArgumentException("User not found")
        }
        userRepository.deleteById(command.userId)
    }
}