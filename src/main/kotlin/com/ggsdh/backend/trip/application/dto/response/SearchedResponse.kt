package com.ggsdh.backend.trip.application.dto.response

import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class SearchedResponse(
        val id: Long?,
        val type: String,
        val tripThemeConstants: TripThemeConstants,
        val name: String?,
        val sigunguCode: SigunguCode
)
