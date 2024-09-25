package com.ggsdh.backend.photobook.presentation.dto

import com.ggsdh.backend.photobook.application.dto.GetLocationInputDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CreatePhotobookRequestPhoto(
    val path: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: String,
) {
    fun toGetLocationInputDto(): GetLocationInputDto =
        GetLocationInputDto(
            path,
            if (latitude == 0.0) null else latitude,
            if (longitude == 0.0) null else longitude,
            LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
        )
}

data class CreatePhotobookRequest(
    val title: String,
    val startDate: String,
    val endDate: String,
    val photos: List<CreatePhotobookRequestPhoto>,
)
