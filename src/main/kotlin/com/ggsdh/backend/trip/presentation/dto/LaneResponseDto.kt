package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class LaneResponseDto(
    val laneId: Long,
    val name: String,
    val photo: String?,
    val likeCount: Long,
    val likedByMe: Boolean,
    val theme: TripThemeConstants,
)
