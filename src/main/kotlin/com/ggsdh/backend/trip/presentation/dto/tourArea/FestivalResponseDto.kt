package com.ggsdh.backend.trip.presentation.dto.tourArea

import com.ggsdh.backend.trip.domain.constants.ContentType
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import java.time.LocalDate

class FestivalResponseDto(
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
    val ageLimit: String?,
    val discountInfo: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val eventPlace: String?,
    val spendTime: String?,
    val sponsor: String?,
    val useTime: String?,
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
        ContentType.FESTIVAL_EVENT,
        description,
    )
