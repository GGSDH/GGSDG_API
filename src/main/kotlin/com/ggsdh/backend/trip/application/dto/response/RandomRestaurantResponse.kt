package com.ggsdh.backend.trip.application.dto.response

data class RandomRestaurantResponse(
        val restaurantId: Long?,
        val image: String?,
        val name: String?,
        val likeCnt: Long?,
        val sigunguValue: String?
)
