package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class RankingRestaurantResponse(
    val tourAreaId: Long?,
    val ranking: Int,
    val image: String?,
    val name: String,
    val sigunguValue: String,
    val category: TripThemeConstants,
    val likeCnt: Long?,
    val likedByMe: Boolean,
)
