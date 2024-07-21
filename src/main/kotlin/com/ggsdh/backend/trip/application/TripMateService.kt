package com.ggsdh.backend.trip.application

import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.domain.TripMate
import com.ggsdh.backend.trip.domain.constants.TripMateConstants
import com.ggsdh.backend.trip.infrastructure.TripMateRepository
import org.springframework.stereotype.Service

@Service
class TripMateService(
    val memberRepository: MemberRepository,
    val tripMateRepository: TripMateRepository,
) {
    private fun generateTripMates(
        member: Member,
        tripMateConstants: List<TripMateConstants>,
    ): List<TripMate> = tripMateConstants.map { TripMate(member, it) }.toList()
}
