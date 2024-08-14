package com.ggsdh.backend.trip.application.dto.request

import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class TourAreaSearchRequest(
    val sigunguCode: List<SigunguCode>,
    val tripThemeConstant: TripThemeConstants,
)
