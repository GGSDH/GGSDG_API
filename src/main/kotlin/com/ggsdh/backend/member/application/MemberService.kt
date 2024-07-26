package com.ggsdh.backend.member.application

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.global.security.jwt.JwtFactory
import com.ggsdh.backend.member.application.dto.request.MemberRoleRequest
import com.ggsdh.backend.member.application.dto.response.MemberTokenResponse
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.domain.Nickname
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtFactory: JwtFactory,
) {
    fun updateNickname(
        id: Long,
        nickname: String,
    ) {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }
        member.nickname = nickname

        memberRepository.save(member)
    }

    fun createDummyMember(memberRoleRequest: MemberRoleRequest): MemberTokenResponse {
        val role = memberRoleRequest.role

        val dummyIdentification = MemberIdentification(null, ProviderType.DUMMY, null, null, "123456", null)
        val nickname = Nickname.generate()
        val member = Member(role, dummyIdentification, nickname.value)

        val savedMember = memberRepository.save(member)
        val accessToken = jwtFactory.createAccessToken(savedMember)

        return MemberTokenResponse(savedMember, accessToken)
    }

    fun getMember(id: Long): Member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }
}
