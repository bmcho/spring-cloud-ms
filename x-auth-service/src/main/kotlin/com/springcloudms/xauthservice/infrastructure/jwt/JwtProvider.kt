package com.springcloudms.xauthservice.infrastructure.jwt

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.springcloudms.xauthservice.config.properties.JwtProperties
import jakarta.annotation.PostConstruct
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
) {
    private lateinit var privateKey: RSAPrivateKey
    private lateinit var publicKey: RSAPublicKey


    @PostConstruct
    fun initKeys() {
        val privateKeyBytes = ClassPathResource(jwtProperties.privateKeyPath.removePrefix("classpath:")).inputStream.readBytes()
        val publicKeyBytes = ClassPathResource(jwtProperties.publicKeyPath.removePrefix("classpath:")).inputStream.readBytes()

        val keyFactory = KeyFactory.getInstance("RSA")
        privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes)) as RSAPrivateKey
        publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes)) as RSAPublicKey
    }

    fun generateToken(
        userId: String,
        username: String,
        email: String,
        roles: List<String>,
        audience: String = "web",
    ): String {
        val now = Date()
        val expiry = Date(now.time + jwtProperties.expirationMs)

        val claims = JWTClaimsSet.Builder()
            .subject(username)         // sub
            .issueTime(now)             // iat
            .expirationTime(expiry)     // exp
            .issuer("my-service")       // iss
            .audience(audience)         // aud
            .claim("userId", userId)     // 사용자 식별자
            .claim("email", email)       // 사용자 이메일
            .claim("roles", roles)       // 사용자 권한 목록
            .build()

        val signedJWT = SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256).build(),
            claims
        )

        signedJWT.sign(RSASSASigner(privateKey))
        return signedJWT.serialize()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val signedJWT = SignedJWT.parse(token)
            val verifier = RSASSAVerifier(publicKey)
            signedJWT.verify(verifier) && !isTokenExpired(signedJWT)
        } catch (e: Exception) {
            false
        }
    }

    private fun isTokenExpired(signedJWT: SignedJWT): Boolean {
        val expiration = signedJWT.jwtClaimsSet.expirationTime
        return expiration.before(Date())
    }

    fun getUsername(token: String): String {
        val signedJWT = SignedJWT.parse(token)
        return signedJWT.jwtClaimsSet.subject
    }

    fun getRoles(token: String): List<String> {
        val signedJWT = SignedJWT.parse(token)
        return signedJWT.jwtClaimsSet.getStringListClaim("roles") ?: emptyList()
    }

    fun getUserId(token: String): String {
        val signedJWT = SignedJWT.parse(token)
        return signedJWT.jwtClaimsSet.getStringClaim("userId") ?: ""
    }

    fun getEmail(token: String): String {
        val signedJWT = SignedJWT.parse(token)
        return signedJWT.jwtClaimsSet.getStringClaim("email") ?: ""
    }
}