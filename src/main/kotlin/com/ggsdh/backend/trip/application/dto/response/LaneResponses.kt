package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class LaneResponses(
    val laneId: Long?,
    val laneName: String,
    val tripThemeConstants: TripThemeConstants,
    val likes: Long,
    val image: String,
    val days: Int,
    val description: String?,
    var likedByMe: Boolean,
)

// softwareupdate —-fetch-full-installer —-full-installer-version 15.0