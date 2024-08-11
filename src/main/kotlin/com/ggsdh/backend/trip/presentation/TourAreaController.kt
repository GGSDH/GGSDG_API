package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LikeService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/tour-area")
class TourAreaController(
    private val likeService: LikeService,
) {
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
