package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class RankingRestaurantResponse(
        val ranking: Int,
        val image: String?,
        val name: String,
        val sigunguValue: String,
        val category: TripThemeConstants
)
