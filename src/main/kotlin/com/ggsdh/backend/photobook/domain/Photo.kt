package com.ggsdh.backend.photobook.domain

import java.time.LocalDateTime
import java.util.UUID

class Photo(
    val id: String,
    val path: String,
    val location: Location?,
    val dateTime: LocalDateTime,
) {
    companion object {
        fun create(
            path: String,
            dateTime: LocalDateTime,
        ): Photo = Photo(UUID.randomUUID().toString(), path, null, dateTime)
    }
}
