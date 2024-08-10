package com.ggsdh.backend.photobook.presentation.dto

import com.ggsdh.backend.photobook.domain.DailyPhotoGroup

class PhotoBookResponse(
    val id: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val dailyPhotoGroup: List<DailyPhotoGroup>,
)
