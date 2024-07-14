package com.ggsdh.backend.auth.infrastructure.kakao

import com.ggsdh.backend.auth.domain.MemberIdentificationService
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.auth.infrastructure.kakao.dto.KakaoGetMeResponse
import com.ggsdh.backend.auth.infrastructure.kakao.dto.KakaoRefreshResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoMemberIdentificationService(
    @Value("\${kakao.client-id}")
    private val clientId: String,
    @Value("\${kakao.client-secret}")
    private val clientSecret: String,
) : MemberIdentificationService {
    private fun refresh(refreshToken: String): KakaoRefreshResponse {
        val template = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val body = LinkedMultiValueMap<String, String>()
        body.set("grant_type", "refresh_token")
        body.set("client_id", clientId)
        body.set("refresh_token", refreshToken)
        body.set("client_secret", clientSecret)

        val entity = HttpEntity<Any>(body, headers)

        val response: ResponseEntity<KakaoRefreshResponse> =
            template.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                KakaoRefreshResponse::class.java,
            )

        if (response.body == null) throw Exception("Kakao API Error")
        return response.body!!
    }

    override fun getToken(memberIdentification: MemberIdentification): String {
        return this.refresh(memberIdentification.refreshToken).access_token
    }

    override fun refreshIdentification(memberIdentification: MemberIdentification): MemberIdentification {
        val response = this.refresh(memberIdentification.refreshToken)

        return MemberIdentification(
            id = null,
            type = memberIdentification.type,
            kakaoId = memberIdentification.kakaoId,
            sub = memberIdentification.sub,
            refreshToken = response.refresh_token ?: memberIdentification.refreshToken,
            member = memberIdentification.member,
        )
    }

    override fun toMemberIdentification(
        tokenString: String,
        refreshToken: String?,
    ): MemberIdentification {
        println(tokenString)
        val template = RestTemplate()

        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $tokenString")
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity<KakaoGetMeResponse>(headers)

        val response: ResponseEntity<KakaoGetMeResponse> =
            template.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                entity,
                KakaoGetMeResponse::class.java,
            )

        if (response.body == null) throw Exception("Kakao API Error")
        if (refreshToken == null) throw Exception("Refresh Token is null")

        return MemberIdentification(
            id = null,
            type = ProviderType.KAKAO,
            kakaoId = response.body!!.id,
            sub = null,
            refreshToken = refreshToken,
            member = null,
        )
    }

    override fun revoke(userIdentification: MemberIdentification): Boolean {
        val template = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val body = LinkedMultiValueMap<String, String>()
        body.set("client_id", clientId)
        body.set("client_secret", clientSecret)
        body.set("token", userIdentification.refreshToken)

        val entity = HttpEntity<Any>(body, headers)

        val response: ResponseEntity<String> =
            template.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                entity,
                String::class.java,
            )

        return response.statusCode == HttpStatus.OK
    }
}
