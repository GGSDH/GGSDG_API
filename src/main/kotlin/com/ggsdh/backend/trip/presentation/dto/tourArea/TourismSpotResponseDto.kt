package com.ggsdh.backend.trip.presentation.dto.tourArea

import com.ggsdh.backend.trip.domain.constants.ContentType
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class TourismSpotResponseDto(
    tourAreaId: Long,
    name: String,
    address: String,
    image: String?,
    latitude: Double,
    longitude: Double,
    ranking: Long?,
    sigungu: String,
    telNo: String?,
    tripTheme: TripThemeConstants,
    likeCount: Long,
    likedByMe: Boolean,
) : TourAreaResponseDto(
        tourAreaId,
        name,
        address,
        image,
        latitude,
        longitude,
        ranking,
        sigungu,
        telNo,
        tripTheme,
        likeCount,
        likedByMe,
        ContentType.TOURISM_SPOT,
    )
