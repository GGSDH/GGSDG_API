package com.ggsdh.backend.trip.application.dto.request

import com.ggsdh.backend.trip.domain.constants.TripThemeConstant

data class OnboardingRequest(
    val memberId: Long,
    val tripThemes: List<TripThemeConstant>,
)
