package com.springcloudms.xauthservice.application.auth.service

import com.springcloudms.xauthservice.application.auth.dto.*
import com.springcloudms.xauthservice.domain.auth.command.*
import com.springcloudms.xauthservice.domain.auth.model.User
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import com.springcloudms.xauthservice.infrastructure.persistence.PasswordManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager
) {

    @Transactional
    fun assignRoles(command: AssignRoleCommand) {
        val user = userRepository.findById(command.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.changeRoles(command.roles)
    }

    @Transactional
    fun changePassword(command: ChangePasswordCommand) {
        val user = userRepository.findById(command.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        passwordManager.validatePassword(command.oldPassword, user.password)
        user.changePassword(passwordManager.encode(command.newPassword))
    }

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

    @Transactional
    fun delete(command: DeleteUserCommand) {
        if (!userRepository.existsById(command.userId)) {
            throw IllegalArgumentException("User not found")
        }
        userRepository.deleteById(command.userId)
    }

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