package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class LaneResponse(
    val id: Long?,
    val days: Int,
    val laneName: String,
    val image: String?,
    val tripThemeConstants: TripThemeConstants,
    val laneSpecificResponses: List<LaneSpecificResponse>,
    val laneDescription: String?,
)
