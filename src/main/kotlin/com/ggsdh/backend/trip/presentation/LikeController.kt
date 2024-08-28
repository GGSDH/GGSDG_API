package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.application.dto.response.TourAreaResponse
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/v1/like")
class LikeController(
    private val likeService: LikeService,
    private val tourAreaRepository: TourAreaRepository,
    private val laneRepository: LaneRepository,
    private val laneService: LaneService,
) {
    @GetMapping("/lane")
    fun getAllLikedLanes(
        @AuthId id: Long,
    ): BaseResponse<List<LaneResponses>> {
        val liked = likeService.getAllLikedLaneIdsByMember(id)
        val lanes =
            liked.mapNotNull {
                laneService.findLaneById(it)
            }

        return BaseResponse.success(
            lanes.map {
                LaneResponses(
                    it.laneId,
                    it.laneName,
                    it.tripThemeConstants,
                    it.likes,
                    it.image,
                    it.days,
                )
            },
        )
    }

    @GetMapping("/tourArea")
    fun getAllLikedTourAreas(
        @AuthId id: Long,
    ): BaseResponse<List<TourAreaResponse>> {
        val liked = likeService.getAllLikedTourAreaIdsByMember(id)
        val tourAreas = tourAreaRepository.findAllById(liked)

        return BaseResponse.success(
            tourAreas.map {
                TourAreaResponse(
                    it.id!!,
                    it.name,
                    it.latitude,
                    it.longitude,
                    it.image,
                    it.likes,
                    true,
                    it.sigunguCode
                )
            },
        )
    }
}
