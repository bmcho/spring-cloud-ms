package com.springcloudms.xauthservice.domain.auth.repository

import com.springcloudms.xauthservice.domain.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
}