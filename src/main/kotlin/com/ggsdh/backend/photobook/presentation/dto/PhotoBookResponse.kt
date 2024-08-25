package com.ggsdh.backend.photobook.presentation.dto

import com.ggsdh.backend.photobook.domain.DailyPhotoGroup
import com.ggsdh.backend.photobook.domain.Location
import com.ggsdh.backend.photobook.domain.Photo

class PhotoBookResponse(
    val id: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val dailyPhotoGroup: List<DailyPhotoGroup>,
    val location: Location,
    val photoTicketImage: Photo?,
)
