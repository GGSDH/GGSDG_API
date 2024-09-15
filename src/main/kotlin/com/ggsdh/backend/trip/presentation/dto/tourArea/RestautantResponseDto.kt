package com.ggsdh.backend.trip.presentation.dto.tourArea

import com.ggsdh.backend.trip.domain.constants.ContentType
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class RestautantResponseDto(
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
    description: String?,
    val menuImage: String?,
    val menuName: String?,
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
        ContentType.RESTAURANT,
    description,
    )
