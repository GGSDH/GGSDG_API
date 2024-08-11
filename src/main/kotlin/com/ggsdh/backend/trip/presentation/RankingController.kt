package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.RankingService
import com.ggsdh.backend.trip.application.dto.response.RankingRestaurantResponse
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/ranking")
class RankingController(
    private val rankingService: RankingService,
) {
    @GetMapping
    fun getRankingResponses(
        @AuthId memberId: Long,
    ): BaseResponse<List<RankingRestaurantResponse>> {
        val result = rankingService.findAllRankingArea(memberId)
        return BaseResponse.success(result)
    }
}
