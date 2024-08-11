package com.ggsdh.backend.trip.application.dto.response

data class RandomRestaurantResponse(
    val tourAreaId: Long?,
    val image: String?,
    val name: String?,
    val likeCnt: Long?,
    val sigunguValue: String?,
    val likedByMe: Boolean,
)
