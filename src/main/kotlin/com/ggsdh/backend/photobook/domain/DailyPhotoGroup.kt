package com.ggsdh.backend.photobook.domain

import java.time.LocalDate

class DailyPhotoGroup(
    val day: Int,
    val dateTime: LocalDate,
    val hourlyPhotoGroups: List<HourlyPhotoGroup>,
)
