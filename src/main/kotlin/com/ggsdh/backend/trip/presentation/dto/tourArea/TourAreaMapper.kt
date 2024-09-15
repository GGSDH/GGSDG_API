package com.ggsdh.backend.trip.presentation.dto.tourArea

import com.ggsdh.backend.trip.domain.FestivalEvent
import com.ggsdh.backend.trip.domain.Restaurant
import com.ggsdh.backend.trip.domain.TourArea

fun TourArea.toResponseDto(userLikes: List<Long>): TourAreaResponseDto {
    if (this is Restaurant) {
        return RestautantResponseDto(
            tourAreaId = this.id!!,
            name = this.name,
            address = this.address1,
            image = this.image,
            latitude = this.latitude,
            longitude = this.longitude,
            ranking = this.ranking,
            sigungu = this.sigunguCode.name,
            telNo = this.telNo,
            tripTheme = this.tripThemeConstants,
            likeCount = this.likes,
            likedByMe = userLikes.contains(this.id),
            menuImage = this.firstMenuImage,
            menuName = this.firstMenuName,
            description = this.description,
        )
    } else if (this is FestivalEvent) {
        return FestivalResponseDto(
            tourAreaId = this.id!!,
            name = this.name,
            address = this.address1,
            image = this.image,
            latitude = this.latitude,
            longitude = this.longitude,
            ranking = this.ranking,
            sigungu = this.sigunguCode.name,
            telNo = this.telNo,
            tripTheme = this.tripThemeConstants,
            likeCount = this.likes,
            likedByMe = userLikes.contains(this.id),
            ageLimit = this.ageLimit,
            discountInfo = this.discountInfo,
            startDate = this.startDate,
            endDate = this.endDate,
            eventPlace = this.eventPlace,
            spendTime = this.spendTimeFestival,
            sponsor = this.sponsorName,
            useTime = this.usetimeFestival,
            description = this.description,
        )
    } else {
        return TourismSpotResponseDto(
            tourAreaId = this.id!!,
            name = this.name,
            address = this.address1,
            image = this.image,
            latitude = this.latitude,
            longitude = this.longitude,
            ranking = this.ranking,
            sigungu = this.sigunguCode.name,
            telNo = this.telNo,
            tripTheme = this.tripThemeConstants,
            likeCount = this.likes,
            likedByMe = userLikes.contains(this.id),
            description = this.description,
        )
    }
}
