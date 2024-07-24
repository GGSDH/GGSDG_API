package com.ggsdh.backend.trip.application.dto.request

import com.ggsdh.backend.trip.domain.constants.TripMateConstants
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class OnboardingRequest(
        val tripThemes: List<TripThemeConstants>,
        val tripMateConstants: List<TripMateConstants>
)
