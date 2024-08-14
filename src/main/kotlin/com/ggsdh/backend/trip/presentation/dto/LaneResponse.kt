package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse

class LaneResponse(
    val id: Long?,
    val days: Int,
    val laneName: String,
    val image: String?,
    val laneSpecificResponses: List<LaneSpecificResponse>,
)
