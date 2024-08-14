package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class LaneResponses(
    val laneId: Long?,
    val laneName: String,
    val tripThemeConstants: TripThemeConstants,
    val likes: Long,
    val image: String,
    val days: Int,
)
