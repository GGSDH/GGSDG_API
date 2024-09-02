package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse
import com.ggsdh.backend.trip.application.dto.response.ParsedContent

class AIResponseDto(
    val id: Long,
    val title: String,
    val description: String,
    val days: List<AILaneResponse>
)

class AILaneResponse(
    val day: Int,
    val tripAreaNames: List<String>,
    val tourAreas: List<LaneSpecificResponse>
)