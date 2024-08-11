package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.member.exception.MemberError
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.domain.Lane
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.QLaneRepository
import org.springframework.stereotype.Service

@Service
class LaneService(
    private val laneRepository: LaneRepository,
    private val qLaneRepository: QLaneRepository,
    private val memberRepository: MemberRepository,
) {
    fun findLaneById(id: Long): LaneResponses? = qLaneRepository.findLaneById(id)

    fun getThemeLane(id: Long): List<LaneResponses> {
        val member =
            memberRepository
                .findById(id)
                .orElseThrow { BusinessException(MemberError.NOT_FOUND) }

        return qLaneRepository.getThemeLanesByMemberId(id)
    }

    fun getAllLanesByTourAreaId(tourAreaId: Long): List<Lane> {
        val laneIds = qLaneRepository.getAllLanesWithTourAreaId(tourAreaId)
        val lanes = laneRepository.findAllById(laneIds)

        return lanes
    }
}
