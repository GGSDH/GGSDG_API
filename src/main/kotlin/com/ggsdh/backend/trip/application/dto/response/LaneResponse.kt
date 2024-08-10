package com.ggsdh.backend.trip.application.dto.response

data class LaneResponse(
        val laneId: Long?,
        val responses: List<TourAreaResponse>
)
