package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.SigunguCode

data class TourAreaResponse(
        val tourAreaId: Long?,
        val tourAreaName: String,
        val latitude: Double,
        val longitude: Double,
        val image: String?,
        val likeCnt: Long?,
        val likedByMe: Boolean,
        val sigunguCode: SigunguCode,
)
