package com.springcloudms.xauthservice.infrastructure.persistence

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordManager(
    private val passwordEncoder: PasswordEncoder,
) {
    companion object {
        private const val SECRET = "%Go9Z}e2QR9Nf8!7+mbFo!uA[gTJ3m_s-u#P0YaqGWXB)3*q~*TGep(jA_r9pJ]x\n!"
    }

    fun encode(rawPassword: String): String {
        val pepperedPassword = rawPassword + SECRET
        return passwordEncoder.encode(pepperedPassword)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        val pepperedPassword = rawPassword + SECRET
        return passwordEncoder.matches(pepperedPassword, encodedPassword)
    }

    fun validatePassword(rawPassword: String, encodedPassword: String) {
        if (!matches(rawPassword, encodedPassword)) {
            throw IllegalArgumentException("Invalid password")
        }
    }

}