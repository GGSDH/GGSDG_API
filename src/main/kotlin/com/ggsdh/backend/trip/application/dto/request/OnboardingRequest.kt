package com.ggsdh.backend.trip.application.dto.request

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

data class OnboardingRequest(
        val memberId: Long,
        val tripThemes: List<TripThemeConstants>,
)
