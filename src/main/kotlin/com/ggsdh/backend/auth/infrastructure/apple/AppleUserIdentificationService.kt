package com.ggsdh.backend.auth.infrastructure.apple

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ggsdh.backend.auth.domain.MemberIdentificationService
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.security.KeyFactory
import java.security.interfaces.ECPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date

class AppleTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String,
    val id_token: String,
)

@Service
class AppleUserIdentificationService(
    @Value("\${apple.client-id}")
    private val clientId: String,
    @Value("\${apple.team-id}")
    private val teamId: String,
    @Value("\${apple.key-id}")
    private val keyIdentifier: String,
    @Value("\${apple.key}")
    private val authKey: String,
) : MemberIdentificationService {
    private fun stringToKey(privateKeyString: String): ECPrivateKey {
        val keyData =
            privateKeyString
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\n", "")
                .trim()

        print(keyData)

        val keyBytes = Base64.getDecoder().decode(keyData)

        val keySpec = PKCS8EncodedKeySpec(keyBytes)

        val keyFactory = KeyFactory.getInstance("EC")

        return keyFactory.generatePrivate(keySpec) as ECPrivateKey
    }

    private fun generateClientSecret(): String {
        val algorithm = Algorithm.ECDSA256(stringToKey(authKey))

        return JWT
            .create()
            .withHeader(
                mapOf(
                    "kid" to keyIdentifier,
                ),
            ).withIssuer(teamId)
            .withAudience("https://appleid.apple.com")
            .withSubject(clientId)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000))
            .sign(algorithm)
    }

    override fun toMemberIdentification(
        tokenString: String,
        refreshToken: String?,
    ): MemberIdentification {
        val template = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        println("tokenstring: $tokenString")
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("client_id", clientId)
        formData.add("client_secret", generateClientSecret())
        formData.add("code", tokenString)
        formData.add("grant_type", "authorization_code")

        val entity = HttpEntity<Any>(formData, headers)

        val response =
            template.exchange(
                "https://appleid.apple.com/auth/token",
                HttpMethod.POST,
                entity,
                AppleTokenResponse::class.java,
            )

        if (response.body == null) throw Exception("Apple Login Failed")

        val decoded = JWT.decode(response.body!!.id_token)

        val sub = decoded.subject ?: throw Exception("Apple Login Failed")

        return MemberIdentification(
            id = null,
            sub = sub,
            refreshToken = response.body!!.refresh_token,
            kakaoId = null,
            member = null,
            type = ProviderType.APPLE,
        )
    }

    override fun getToken(memberIdentification: MemberIdentification): String =
        this.refreshIdentification(memberIdentification).refreshToken

    override fun refreshIdentification(userIdentification: MemberIdentification): MemberIdentification {
        try {
            val template = RestTemplate()
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
            val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
            formData.add("client_id", clientId)
            formData.add("client_secret", generateClientSecret())
            formData.add("refresh_token", userIdentification.refreshToken)
            formData.add("grant_type", "refresh_token")
            val entity = HttpEntity<Any>(formData, headers)

            val response =
                template.exchange(
                    "https://appleid.apple.com/auth/token",
                    HttpMethod.POST,
                    entity,
                    AppleTokenResponse::class.java,
                )

            if (response.body == null) throw Exception("Apple Login Failed")
            val decoded = JWT.decode(response.body!!.id_token)
            val sub = decoded.subject ?: throw Exception("Apple Login Failed")

            return MemberIdentification(
                id = null,
                sub = sub,
                refreshToken = response.body!!.refresh_token,
                kakaoId = null,
                member = null,
                type = ProviderType.APPLE,
            )
        } catch (e: Exception) {
            println(e)

            return userIdentification
        }
    }

    override fun revoke(userIdentification: MemberIdentification): Boolean {
        try {
            val template = RestTemplate()
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
            val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
            formData.add("client_id", clientId)
            formData.add("client_secret", generateClientSecret())
            formData.add("token", userIdentification.refreshToken)
            formData.add("hint", "refresh_token")

            val entity = HttpEntity<Any>(formData, headers)

            val response =
                template.exchange(
                    "https://appleid.apple.com/auth/revoke",
                    HttpMethod.POST,
                    entity,
                    String::class.java,
                )

            return true
        } catch (e: Exception) {
            println(e)

            return false
        }
    }
}
