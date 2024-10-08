package com.ggsdh.backend.auth.application

import com.ggsdh.backend.auth.application.dto.request.LoginRequest
import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationRepository
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationServiceFactory
import com.ggsdh.backend.global.security.jwt.JwtFactory
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.domain.Nickname
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Suppress("NAME_SHADOWING")
@Component
@Transactional
class AuthService(
        private val jwtFactory: JwtFactory,
        private val memberRepository: MemberRepository,
        private val memberIdentificationRepository: MemberIdentificationRepository,
        private val memberIdentificationServiceFactory: MemberIdentificationServiceFactory,
) {
    fun refreshToken(id: Long): AuthResponse {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }
        val accessToken = jwtFactory.createAccessToken(member)
        return AuthResponse(
                accessToken,
                member.role,
        )
    }

    fun login(loginRequest: LoginRequest): AuthResponse {
        val memberIdentificationService =
                memberIdentificationServiceFactory.create(
                        loginRequest.providerType,
                )

        val identification =
                memberIdentificationService.toMemberIdentification(
                        loginRequest.accessToken,
                        loginRequest.refreshToken,
                )

        val member =
                memberIdentificationRepository.findMemberByIdentification(
                        identification,
                )

        if (member == null) {
            val nickname = Nickname.generate()
            val member = Member(Role.ROLE_TEMP_USER, identification, nickname.value)
            val savedMember = memberRepository.save(member)

            val accessToken = jwtFactory.createAccessToken(savedMember)
            return AuthResponse(
                    accessToken,
                    savedMember.role,
            )
        }

        val accessToken = jwtFactory.createAccessToken(member)
        return AuthResponse(
                accessToken,
                member.role,
        )
    }
}
