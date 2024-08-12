package com.ggsdh.backend.trip.application.dto.response

data class LaneSpecificResponse(
        val laneId: Long?,
        val sequence: Long?,
        val tourAreaResponse: TourAreaResponse
)
