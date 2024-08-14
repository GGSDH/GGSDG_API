package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.application.dto.response.LaneSpecificResponse
import com.ggsdh.backend.trip.application.dto.response.ParsedContent

class AIResponseDto(
    val data: ParsedContent,
    val laneSpecificResponse: Map<Int, List<LaneSpecificResponse>>,
    val id: Long,
)
