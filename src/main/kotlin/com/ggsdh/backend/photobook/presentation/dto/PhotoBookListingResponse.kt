package com.ggsdh.backend.photobook.presentation.dto

import com.ggsdh.backend.photobook.domain.Location
import com.ggsdh.backend.photobook.domain.Photo

class PhotoBookListingResponse(
    val id: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val photo: String,
    val location: Location,
    val photoTicketImage: Photo?
)
