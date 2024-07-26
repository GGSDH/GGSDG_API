package com.ggsdh.backend.member.presentation.dto

import com.ggsdh.backend.trip.domain.constants.TripThemeConstant

class MemberResponse(
    val id: Long,
    val nickname: String,
    val memberIdentificationType: String,
    val role: String,
    val themes: List<TripThemeConstant>,
)
