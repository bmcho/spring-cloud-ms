package com.springcloudms.xauthservice.application.auth.usercase

import com.springcloudms.xauthservice.domain.auth.command.AssignRoleCommand
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AssignRoleUseCase(
    private val userRepository: UserRepository
) {

    @Transactional
    fun assignRoles(command: AssignRoleCommand) {
        val user = userRepository.findById(command.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.changeRoles(command.roles)
    }
}