package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/lane")
class LaneController(
    private val laneService: LaneService,
    private val likeService: LikeService,
) {
    @GetMapping("/test")
    fun test() {
        laneService.test()
    }

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
    fun getLanes(): BaseResponse<Unit> {
        val laneResponse = laneService.getThemeLane()
        return BaseResponse.success(laneResponse)
    }
}
