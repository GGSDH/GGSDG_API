package com.ggsdh.backend.trip.application.dto.response

data class TourAreaResponse(
        val tourAreaId: Long?,
        val tourAreaName: String,
        val latitude: Double,
        val longitude: Double,
        val image: String?,
        val likeCnt: Long?,
        val likedByMe: Boolean,
)
