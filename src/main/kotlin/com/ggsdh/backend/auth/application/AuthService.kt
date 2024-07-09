package com.ggsdh.backend.auth.application

import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.auth.application.dto.response.MemberResponse
import com.ggsdh.backend.auth.application.dto.response.TokenResponse
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.domain.constants.Role.ROLE_TEMP_USER
import com.ggsdh.backend.global.security.jwt.JwtFactory
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
@Transactional
class AuthService(
    private val authFactory: AuthFactory,
    private val jwtFactory: JwtFactory,
    private val memberRepository: MemberRepository,
) {
    fun login(
        providerType: String,
        code: String,
    ): AuthResponse {
        val authConnector = authFactory.getAuthConnector(providerType)
        val oauthToken = authConnector.fetchOauthToken(code)
        val memberInfo = authConnector.fetchMemberInfo(oauthToken.accessToken)
        val member = findOrCreateMember(memberInfo.id, ProviderType.from(providerType))

        val memberResponse = MemberResponse(member.id!!, member.role)
        val tokenResponse = TokenResponse(jwtFactory.createAccessToken(member))

        return AuthResponse(true, memberResponse, tokenResponse)
    }

    private fun findOrCreateMember(
        kakaoProviderId: Long,
        providerType: ProviderType,
    ): Member {
        return memberRepository.findByProviderIdAndProviderType(kakaoProviderId, providerType)
            ?: memberRepository.save(Member(null, kakaoProviderId, providerType, ROLE_TEMP_USER))
    }
}
