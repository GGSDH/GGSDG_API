package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
@DiscriminatorValue("TOURISM_SPOT")
open class TourismSpot(
        tripThemeConstants: TripThemeConstants,
        sigunguCode: SigunguCode,
        contentId: Long,
        address1: String,
        address2: String?,
        image: String?,
        latitude: Double,
        longitude: Double,
        mapLevel: Long?,
        telNo: String?,
        name: String,
        dataModifiedAt: LocalDate,
        dataCreatedAt: LocalDate,
        ranking: Long?,
        open var grandTourYn: Boolean,
        open var associatedGrandTourYn: Boolean
) : TourArea(
        tripThemeConstants,
        sigunguCode,
        contentId,
        address1,
        address2,
        image,
        latitude,
        longitude,
        mapLevel,
        telNo,
        name,
        ranking,
        dataModifiedAt,
        dataCreatedAt
)
