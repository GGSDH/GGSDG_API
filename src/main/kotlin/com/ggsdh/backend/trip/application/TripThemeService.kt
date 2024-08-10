package com.ggsdh.backend.trip.application

import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.exception.MemberError
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import com.ggsdh.backend.trip.domain.TripTheme
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import com.ggsdh.backend.trip.infrastructure.TripThemeRepository
import org.springframework.stereotype.Service

@Service
class TripThemeService(
        val memberRepository: MemberRepository,
        val tripThemeRepository: TripThemeRepository,
) {
    fun getMemberThemes(id: Long): List<TripTheme> = tripThemeRepository.findAllByMemberId(id)

    fun getOnboardingThemes(): List<TripThemeConstants> = TripThemeConstants.entries

    fun updateTripThemes(
            id: Long,
            onboardingRequest: OnboardingRequest,
    ) {
        val member =
                memberRepository
                        .findById(id)
                        .orElseThrow { BusinessException(MemberError.NOT_FOUND) }
        member.role = Role.ROLE_USER
        memberRepository.save(member)

        tripThemeRepository.removeAllByMemberId(member.id!!)
        val tripThemes = generateTripThemes(member, onboardingRequest.tripThemes)
        tripThemeRepository.saveAll(tripThemes)
    }

    private fun generateTripThemes(
            member: Member,
            tripThemeConstants: List<TripThemeConstants>,
    ): List<TripTheme> = tripThemeConstants.map { TripTheme(member, it) }.toList()
}
