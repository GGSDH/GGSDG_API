package com.ggsdh.backend.member.application

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.auth.infrastructure.apple.AppleUserIdentificationService
import com.ggsdh.backend.global.security.jwt.JwtFactory
import com.ggsdh.backend.member.application.dto.request.MemberRoleRequest
import com.ggsdh.backend.member.application.dto.response.MemberTokenResponse
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.domain.Nickname
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.application.TripThemeService
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import com.ggsdh.backend.trip.infrastructure.TripThemeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberTripThemeRepository: TripThemeRepository,
    private val jwtFactory: JwtFactory,
    private val tripThemeService: TripThemeService,
    private val appleUserIdentificationService: AppleUserIdentificationService,
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

    fun getTripThemeList(id: Long): List<TripThemeConstants> {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }
        return tripThemeService
            .getMemberThemes(member.id!!)
            .map { it.tripThemeConstants }
    }

    fun getMember(id: Long): Member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }

    @Transactional
    fun withdraw(id: Long): Boolean {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException("Member not found") }

        memberTripThemeRepository.removeAllByMemberId(id)
        memberRepository.deleteById(id)

        if (member.memberIdentification.type == ProviderType.APPLE) {
            appleUserIdentificationService.revoke(member.memberIdentification)
        }

        return true
    }
}
