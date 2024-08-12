package com.ggsdh.backend.trip.application.dto.response

data class LaneSpecificResponse(
        val sequence: Long?,
        val laneName: String,
        val tourAreaResponse: TourAreaResponse
)
