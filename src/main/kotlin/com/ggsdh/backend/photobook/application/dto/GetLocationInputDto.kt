package com.ggsdh.backend.photobook.application.dto

import java.time.LocalDateTime

class GetLocationInputDto(
    val path: String,
    val lat: Double?,
    val lon: Double?, // 0.0
    val date: LocalDateTime,
)
