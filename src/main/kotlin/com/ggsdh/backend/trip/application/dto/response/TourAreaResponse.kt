package com.ggsdh.backend.trip.application.dto.response

data class TourAreaResponse(
        val tourAreaId: Long?,
        val latitude: Double,
        val longitude: Double,
        val image: String?,
        val likeCnt: Long?
) {

}
