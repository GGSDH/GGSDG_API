package com.ggsdh.backend.photobook.domain

import java.io.Serializable
import java.time.LocalDateTime

// 2024-08-09 23:38:21.000
class HourlyPhotoGroup(
    val dateTime: LocalDateTime,
    val photos: List<Photo>,
): Serializable {
    fun getPhotosCount(): Number = photos.size

    fun getLocalizedTime(): String {
        val prefix = if (dateTime.hour < 12) "오전" else "오후"
        return "$prefix ${dateTime.hour % 12}시"
    }

    fun getDominantLocation(): Location? {
        val locationMap = mutableMapOf<Location, Int>()
        photos.forEach { photo ->
            val location = photo.location

            if (location != null) {
                locationMap[location] = (locationMap[location] ?: 0) + 1
            }
        }
        return locationMap.maxByOrNull { it.value }?.key
    }
}
