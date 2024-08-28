package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.AILaneService
import com.ggsdh.backend.trip.application.LaneService
import com.ggsdh.backend.trip.application.LikeService
import com.ggsdh.backend.trip.application.dto.request.AIUserRequest
import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.presentation.dto.AIResponseDto
import com.ggsdh.backend.trip.presentation.dto.LaneResponse
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

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
    ): BaseResponse<LaneResponse> {
        val lane = laneService.findLaneById(laneId) ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
        val specificLaneResponse = laneService.getSpecificLaneResponse(id, laneId)
        return BaseResponse.success(
                LaneResponse(
                        lane.laneId,
                        lane.days,
                        lane.laneName,
                        lane.image,
                        lane.tripThemeConstants,
                        specificLaneResponse,
                ),
        )
    }

    @GetMapping
    fun getLanes(
            @AuthId id: Long,
            @RequestParam sigunguCodes: List<SigunguCode>?
    ): BaseResponse<List<LaneResponses>> {
        val laneResponse = laneService.getThemeLane(id, sigunguCodes)
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
