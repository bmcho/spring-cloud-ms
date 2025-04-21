package com.springcloudms.xauthservice.domain.auth.repository

import com.springcloudms.xauthservice.domain.auth.model.User

interface UserQueryRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}