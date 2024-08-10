package com.ggsdh.backend.trip.application

import com.ggsdh.backend.trip.application.dto.response.RankingRestaurantResponse
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import org.springframework.stereotype.Service

@Service
class RankingService(
        private val tourAreaRepository: TourAreaRepository
) {
    fun findAllRankingArea(): List<RankingRestaurantResponse> {
        val result = tourAreaRepository.findAllByRankingArea()
        return result.map {
            RankingRestaurantResponse(it.ranking, it.image, it.name, it.sigunguCode.value, it.tripThemeConstants)
        }.toList()
    }
}
