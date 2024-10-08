package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.TourAreaService
import com.ggsdh.backend.trip.application.dto.request.TourAreaSearchRequest
import com.ggsdh.backend.trip.presentation.dto.DetailedTourAreaOutputDto
import com.ggsdh.backend.trip.presentation.dto.LaneResponseDto
import com.ggsdh.backend.trip.presentation.dto.tourArea.TourAreaResponseDto
import com.ggsdh.backend.trip.presentation.dto.tourArea.toResponseDto
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

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
            @PathVariable tourAreaId: Long
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

    @GetMapping("/search")
    fun search(
            @AuthId memberId: Long,
            @RequestBody request: TourAreaSearchRequest,
            pageable: Pageable
    ): BaseResponse<Page<TourAreaResponseDto>> {

        val tourAreas = tourAreaService.search(request, pageable)
        val likes = likeService.getAllLikedTourAreaIdsByMember(memberId)

        // 각 TourArea 객체를 TourAreaResponseDto로 변환하고
        // 변환된 결과를 리스트로 변환
        val tourAreaResponseDtos = tourAreas.content.map { tourArea ->
            tourArea.toResponseDto(likes)
        }.toMutableList()

        // 리스트의 순서를 랜덤하게 섞음
        tourAreaResponseDtos.shuffle(Random(System.currentTimeMillis()))

        // 랜덤하게 섞인 리스트로 Page 객체를 생성
        val pagedTourAreaResponseDtos = PageImpl(
                tourAreaResponseDtos,
                PageRequest.of(tourAreas.number, tourAreas.size, tourAreas.sort),
                tourAreas.totalElements
        )

        return BaseResponse.success(pagedTourAreaResponseDtos)
    }
}
