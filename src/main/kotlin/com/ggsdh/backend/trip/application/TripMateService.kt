package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.exception.MemberError
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import com.ggsdh.backend.trip.domain.TripMate
import com.ggsdh.backend.trip.domain.constants.TripMateConstants
import com.ggsdh.backend.trip.infrastructure.TripMateRepository
import org.springframework.stereotype.Service

@Service
class TripMateService(
    val memberRepository: MemberRepository,
    val tripMateRepository: TripMateRepository
) {
    fun updateTripMates(onboardingRequest: OnboardingRequest) {
        val member = memberRepository.findById(onboardingRequest.memberId)
            .orElseThrow { BusinessException(MemberError.NOT_FOUND) }

        val tripMates = generateTripMates(member, onboardingRequest.tripMateConstants)
        tripMateRepository.saveAll(tripMates)
    }

    private fun generateTripMates(
        member: Member,
        tripMateConstants: List<TripMateConstants>
    ): List<TripMate> {
        return tripMateConstants.map { TripMate(member, it) }.toList()
    }
}
