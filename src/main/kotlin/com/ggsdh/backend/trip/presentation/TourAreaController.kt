package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.TourAreaService
import com.ggsdh.backend.trip.presentation.dto.DetailedTourAreaOutputDto
import com.ggsdh.backend.trip.presentation.dto.LaneResponseDto
import com.ggsdh.backend.trip.presentation.dto.tourArea.toResponseDto
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/tour-area")
class TourAreaController(
    private val likeService: LikeService,
    private val tourAreaService: TourAreaService,
    private val laneService: LaneService,
) {
    @GetMapping("/{tourAreaId}")
    fun getTourArea(
        @AuthId memberId: Long,
        @PathVariable tourAreaId: Long,
    ): BaseResponse<DetailedTourAreaOutputDto> {
        val tourArea = tourAreaService.getTourAreaById(tourAreaId)
        val likes = likeService.getAllLikedTourAreaIdsByMember(memberId)
        val laneLikes = likeService.getAllLikedLaneIdsByMember(memberId)
        val lanes = laneService.getAllLanesByTourAreaId(tourAreaId)
        val laneResponses = lanes.map { laneService.findLaneById(it.id!!) }.filterNotNull()
        val otherTourAreas = tourAreaService.getNearTourAreas(tourAreaId)

        return BaseResponse.success(
            DetailedTourAreaOutputDto(
                tourArea =
                    tourArea.toResponseDto(
                        likes,
                    ),
                lanes =
                    laneResponses.map {
                        LaneResponseDto(
                            laneId = it.laneId!!,
                            name = it.laneName,
                            photo = it.image,
                            likeCount = it.likes,
                            likedByMe = laneLikes.contains(it.laneId),
                            theme = it.tripThemeConstants,
                        )
                    },
                otherTourAreas =
                    otherTourAreas.map {
                        it.toResponseDto(likes)
                    },
            ),
        )
    }

    @PostMapping("/{tourAreaId}/like")
    fun likeLane(
        @AuthId memberId: Long,
        @PathVariable tourAreaId: Long,
    ): BaseResponse<Boolean> {
        likeService.likeTourArea(memberId, tourAreaId)

        return BaseResponse.success(true)
    }

    @PostMapping("/{tourAreaId}/unlike")
    fun unlikeLane(
        @AuthId memberId: Long,
        @PathVariable tourAreaId: Long,
    ): BaseResponse<Boolean> {
        likeService.unlikeTourArea(memberId, tourAreaId)

        return BaseResponse.success(true)
    }
}
