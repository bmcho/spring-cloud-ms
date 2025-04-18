package com.springcloudms.xauthservice.application.auth.usercase

import com.springcloudms.xauthservice.domain.auth.command.CreateUserCommand
import com.springcloudms.xauthservice.domain.auth.model.User
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import com.springcloudms.xauthservice.infrastructure.passwordEncorder.PasswordManager
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager,
) {

    fun create(command: CreateUserCommand): Long {
        if (userRepository.existsByUsername(command.username)) {
            throw IllegalArgumentException("Username already exists")
        }

        val encryptedPassword = passwordManager.encode(command.password)

        val user = User(
            username = command.username,
            password = encryptedPassword,
            email = command.email,
            roles = command.roles
        )

        return userRepository.save(user).id
    }
}