package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.AILaneService
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.dto.request.AIUserRequest
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse
import com.ggsdh.backend.trip.presentation.dto.AIResponseDto
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/lane")
class LaneController(
    private val laneService: LaneService,
    private val likeService: LikeService,
    private val aiLaneService: AILaneService,
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

    @GetMapping("/{laneId}")
    fun getLaneSpecificResponse(
        @AuthId id: Long,
        @PathVariable laneId: Long,
    ): BaseResponse<List<LaneSpecificResponse>> {
        val specificLaneResponse = laneService.getSpecificLaneResponse(id, laneId)
        return BaseResponse.success(specificLaneResponse)
    }

    @GetMapping
    fun getLanes(
        @AuthId id: Long,
    ): BaseResponse<List<LaneResponses>> {
        val laneResponse = laneService.getThemeLane(id)
        return BaseResponse.success(laneResponse)
    }

    @GetMapping("/aiLane")
    fun getAiLane(
        @AuthId id: Long,
        @RequestBody request: AIUserRequest,
    ): BaseResponse<AIResponseDto> {
        val laneResponse = aiLaneService.generateAILane(id, request)

        return BaseResponse.success(laneResponse)
    }

    @PostMapping("/aiLane/{laneId}/like")
    fun likeAILane(
        @AuthId id: Long,
        @PathVariable laneId: Long,
    ): BaseResponse<Boolean> {
        val laneResponse = likeService.likeAILane(id, laneId)

        return BaseResponse.success(true)
    }

    @PostMapping("/aiLane/{laneId}/unlike")
    fun unlikeAILane(
        @AuthId id: Long,
        @PathVariable laneId: Long,
    ): BaseResponse<Boolean> {
        val laneResponse = likeService.unLikeAILane(id, laneId)

        return BaseResponse.success(true)
    }
}
