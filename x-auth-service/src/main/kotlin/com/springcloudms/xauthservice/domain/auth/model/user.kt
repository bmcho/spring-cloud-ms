package com.springcloudms.xauthservice.domain.auth.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    val email: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    var roles: List<String> = emptyList(),
) {
    fun changePassword(newPassword: String) {
        this.password = newPassword
    }

    fun changeRoles(roles: List<String>) {
        this.roles = roles
    }

    fun changeUserName(username: String) {
        this.username = username
    }
}
