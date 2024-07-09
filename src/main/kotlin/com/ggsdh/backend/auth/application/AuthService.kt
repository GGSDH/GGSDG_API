package com.ggsdh.backend.auth.application

import com.ggsdh.backend.auth.application.dto.request.LoginRequest
import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationRepository
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationServiceFactory
import com.ggsdh.backend.global.security.jwt.JwtFactory
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
@Transactional
class AuthService(
    private val jwtFactory: JwtFactory,
    private val memberRepository: MemberRepository,
    private val memberIdentificationRepository: MemberIdentificationRepository,
    private val memberIdentificationServiceFactory: MemberIdentificationServiceFactory,
) {
    fun login(loginRequest: LoginRequest): String {
        val memberIdentificationService =
            memberIdentificationServiceFactory.create(
                loginRequest.providerType,
            )

        val id =
            memberIdentificationService.toMemberIdentification(
                loginRequest.accessToken,
                loginRequest.refreshToken,
            )
        val member =
            memberIdentificationRepository.findMemberByIdentification(
                id,
            )

        if (member != null) {
            val token = jwtFactory.createAccessToken(member)
            return token
        } else {
            val member =
                Member(
                    id = null,
                    role = Role.ROLE_TEMP_USER,
                    memberIdentification = id,
                )

            memberRepository.save(member)

            val token = jwtFactory.createAccessToken(member)
            return token
        }
    }
}
