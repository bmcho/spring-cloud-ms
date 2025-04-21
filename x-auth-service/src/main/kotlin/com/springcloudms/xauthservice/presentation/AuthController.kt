package com.springcloudms.xauthservice.presentation

import com.springcloudms.xauthservice.application.auth.dto.AuthTokenResponseDto
import com.springcloudms.xauthservice.application.auth.dto.LoginRequestDto
import com.springcloudms.xauthservice.application.auth.dto.RefreshTokenRequestDto
import com.springcloudms.xauthservice.application.auth.dto.RegisterRequestDto
import com.springcloudms.xauthservice.application.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterRequestDto): Long {
        return authService.register(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDto): AuthTokenResponseDto {
        return authService.login(request)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequestDto): AuthTokenResponseDto {
        return authService.refresh(request)
    }
}