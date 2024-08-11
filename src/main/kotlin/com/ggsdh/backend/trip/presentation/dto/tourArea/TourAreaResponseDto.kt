package com.ggsdh.backend.trip.presentation.dto.tourArea

import com.ggsdh.backend.trip.domain.constants.ContentType
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

open class TourAreaResponseDto(
    val tourAreaId: Long,
    val name: String,
    val address: String,
    val image: String?,
    val latitude: Double,
    val longitude: Double,
    val ranking: Long?,
    val sigungu: String,
    val telNo: String?,
    val tripTheme: TripThemeConstants,
    val likeCount: Long,
    val likedByMe: Boolean,
    val contentType: ContentType,
)
