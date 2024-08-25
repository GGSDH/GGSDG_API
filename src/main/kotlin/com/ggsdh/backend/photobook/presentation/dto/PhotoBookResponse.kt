package com.ggsdh.backend.photobook.presentation.dto

import com.ggsdh.backend.photobook.domain.DailyPhotoGroup
import com.ggsdh.backend.photobook.domain.Location
import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook

class PhotoBookResponse(
    val id: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val dailyPhotoGroup: List<DailyPhotoGroup>,
    val photos: List<Photo>,
    val mainPhoto: Photo?,
    val location: Location?,
    val photoTicketImage: Photo?,
) {
    companion object {
        fun from(it: PhotoBook): PhotoBookResponse {
            return PhotoBookResponse(
                it.id,
                it.title,
                it.startDateTime.toString(),
                it.endDateTime.toString(),
                it.getDailyPhotoGroups(),
                it.photos,
                it.photos.firstOrNull(),
                it.getLocation(),
                it.getPhotoTicket()
            )
        }
    }
}
