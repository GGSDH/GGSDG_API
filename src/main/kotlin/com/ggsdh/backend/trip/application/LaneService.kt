package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.member.exception.MemberError
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse
import com.ggsdh.backend.trip.application.dto.response.TourAreaResponse
import com.ggsdh.backend.trip.domain.Lane
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.infrastructure.LaneMappingRepository
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.QLaneRepository
import org.springframework.stereotype.Service

@Service
class LaneService(
        private val laneRepository: LaneRepository,
        private val laneMappingRepository: LaneMappingRepository,
        private val qLaneRepository: QLaneRepository,
        private val memberRepository: MemberRepository,
        private val likeService: LikeService
) {
    fun findLaneById(id: Long): LaneResponses? = qLaneRepository.findLaneById(id)

    fun getThemeLane(
            id: Long,
            sigunguCodes: List<SigunguCode>?
    ): List<LaneResponses> {
        val member =
                memberRepository
                        .findById(id)
                        .orElseThrow { BusinessException(MemberError.NOT_FOUND) }

        return qLaneRepository.getThemeLanesByMemberId(id, sigunguCodes)
    }

    fun getAllLanesByTourAreaId(tourAreaId: Long): List<Lane> {
        val laneIds = qLaneRepository.getAllLanesWithTourAreaId(tourAreaId)
        val lanes = laneRepository.findAllById(laneIds)

        return lanes
    }

    fun getSpecificLaneResponse(
            id: Long,
            laneId: Long
    ): List<LaneSpecificResponse> {
        val likedTourAreaIds = likeService.getAllLikedTourAreaIdsByMember(id)
        val laneMappings = laneMappingRepository.findAllByLaneId(laneId)
        return laneMappings
                .map {
                    LaneSpecificResponse(
                            it.sequence,
                            TourAreaResponse(
                                    it.tourArea!!.id,
                                    it.tourArea!!.name,
                                    it.tourArea!!.latitude,
                                    it.tourArea!!.longitude,
                                    it.tourArea!!.image,
                                    it.tourArea!!.likes,
                                    likedTourAreaIds.contains(it.tourArea!!.id!!),
                                    it.tourArea!!.sigunguCode,
                                    it.tourArea!!.tripThemeConstants,
                                    it.tourArea!!.address1
                            ),
                        it.day
                    )
                }.toList()
    }
}
