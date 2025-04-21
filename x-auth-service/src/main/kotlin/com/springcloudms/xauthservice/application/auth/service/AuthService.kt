package com.springcloudms.xauthservice.application.auth.service

import com.springcloudms.xauthservice.application.auth.dto.AuthTokenResponseDto
import com.springcloudms.xauthservice.application.auth.dto.LoginRequestDto
import com.springcloudms.xauthservice.application.auth.dto.RefreshTokenRequestDto
import com.springcloudms.xauthservice.application.auth.dto.RegisterRequestDto
import com.springcloudms.xauthservice.domain.auth.model.User
import com.springcloudms.xauthservice.domain.auth.repository.UserQueryRepository
import com.springcloudms.xauthservice.domain.auth.repository.UserRepository
import com.springcloudms.xauthservice.infrastructure.jwt.JwtProvider
import com.springcloudms.xauthservice.infrastructure.persistence.PasswordManager
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
    private val userQueryRepository: UserQueryRepository,
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager,
) {

    @Transactional
    fun register(req: RegisterRequestDto): Long {
        return userService.create(req.toCommand())
    }

    @Transactional
    fun login(req: LoginRequestDto): AuthTokenResponseDto {
        val user: User = userQueryRepository.findByEmail(req.email) ?: throw IllegalArgumentException("User not found")
        passwordManager.validatePassword(passwordManager.encode(req.password), user.password)

        val access = jwtProvider.generateToken(user.id, user.username, user.email, user.roles)
        val refresh = jwtProvider.generateRefreshToken(user.id, user.username)

        return AuthTokenResponseDto(access, refresh)
    }

    fun refresh(req: RefreshTokenRequestDto): AuthTokenResponseDto {
        val claims = jwtProvider.parseClaims(req.refreshToken)
        if (claims.getStringClaim("type") != "refresh") {
            throw IllegalArgumentException("유효하지 않은 토큰 타입입니다.")
        }
        // 2) 사용자 재검증(Optional)
        val userId = claims.getLongClaim("userId")
        val username = claims.subject
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }

        val newAccessToken = jwtProvider.generateToken(user.id, user.username, user.email, user.roles)
        val newRefreshToken = jwtProvider.generateRefreshToken(user.id, user.username)

        return AuthTokenResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}