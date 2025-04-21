package com.springcloudms.xauthservice.domain.auth.handler

import com.springcloudms.xauthservice.application.auth.service.UserService
import com.springcloudms.xauthservice.domain.auth.command.*
import org.springframework.stereotype.Service

@Service
class UserHandler(
    private val userService: UserService,
) {
    fun handle(command: AssignRoleCommand) {
        userService.assignRoles(command)
    }
    fun handle(command: ChangePasswordCommand) {
        userService.changePassword(command)
    }
    fun handle(command: CreateUserCommand): Long {
        return userService.create(command)
    }
    fun handle(command: DeleteUserCommand) {
        userService.delete(command)
    }
    fun handle(command: UpdateUserCommand) {
        userService.update(command)
    }
}