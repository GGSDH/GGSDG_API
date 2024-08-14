package com.ggsdh.backend.trip.application.dto.request

import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants

class AIUserRequest(
    val days: Int,
    val sigunguCode: List<SigunguCode>,
    val tripThemeConstants: List<TripThemeConstants>,
)

class AIRequestMessage(
    val role: String,
    val content: String,
)

class AIRequest(
    val model: String,
    val frequency_penalty: Double,
    val messages: List<AIRequestMessage>,
    val response_format: Map<String, Any>,
)

class AITourArea(
    val tourAreaId: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val ranking: Int?,
    val likes: Int,
    val sigunguCode: SigunguCode,
    val tripThemeConstants: TripThemeConstants,
)

class AIUserMessage(
    val days: Int,
    val data: List<AITourArea>,
)
