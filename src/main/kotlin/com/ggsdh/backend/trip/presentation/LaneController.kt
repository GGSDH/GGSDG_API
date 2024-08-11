package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1/lane")
class LaneController(
        private val laneService: LaneService,
        private val likeService: LikeService,
) {
    @PostMapping("/{laneId}/like")
    fun likeLane(
            @AuthId memberId: Long,
            @PathVariable laneId: Long,
    ): BaseResponse<Boolean> {
        likeService.likeLane(memberId, laneId)

        return BaseResponse.success(true)
    }

    @PostMapping("/{laneId}/unlike")
    fun unlikeLane(
            @AuthId memberId: Long,
            @PathVariable laneId: Long,
    ): BaseResponse<Boolean> {
        likeService.unlikeLane(memberId, laneId)

        return BaseResponse.success(true)
    }

    @GetMapping
    fun getLanes(@AuthId id: Long): BaseResponse<List<LaneResponses>> {
        val laneResponse = laneService.getThemeLane(id)
        return BaseResponse.success(laneResponse)
    }
}
