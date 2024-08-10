package com.ggsdh.backend.photobook.domain

import java.time.LocalDateTime

class Photo(
    val id: Long,
    val path: String,
    val location: Location?,
    val dateTime: LocalDateTime,
) {
    companion object {
        fun create(
            path: String,
            dateTime: LocalDateTime,
        ): Photo = Photo(-1, path, null, dateTime)
    }
}
