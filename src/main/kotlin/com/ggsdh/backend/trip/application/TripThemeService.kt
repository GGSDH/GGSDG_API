package com.ggsdh.backend.trip.application

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
        val tripThemeRepository: TripThemeRepository
) {
    fun getOnboardingThemes(): List<TripThemeConstants> {
        return TripThemeConstants.entries
    }

    fun updateTripThemes(
            id: Long,
            onboardingRequest: OnboardingRequest
    ) {
        val member = memberRepository.findById(id)
                .orElseThrow { BusinessException(MemberError.NOT_FOUND) }

        val tripThemes = generateTripThemes(member, onboardingRequest.tripThemes)
        tripThemeRepository.saveAll(tripThemes)
    }

    private fun generateTripThemes(
            member: Member,
            tripThemeConstants: List<TripThemeConstants>
    ): List<TripTheme> {
        return tripThemeConstants.map { TripTheme(member, it) }.toList()
    }
}
