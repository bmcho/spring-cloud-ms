package com.springcloudms.xauthservice.infrastructure.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import com.springcloudms.xauthservice.domain.auth.model.QUser.user
import com.springcloudms.xauthservice.domain.auth.model.User
import com.springcloudms.xauthservice.domain.auth.repository.UserQueryRepository
import org.springframework.stereotype.Repository

@Repository
class UserQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : UserQueryRepository {

    override fun findByUsername(username: String): User? {
        return queryFactory
            .selectFrom(user)
            .where(user.username.eq(username))
            .fetchOne()
    }
}