package com.ggsdh.backend.photobook.infra

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.photobook.domain.Photo
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "TB_PHOTO")
class PhotoEntity(
    @Id
    @Column(name = "photo_id")
    var id: String,
    val path: String,
    var lat: Double?,
    var lon: Double?,
    val location: String?,
    val date: LocalDateTime,
    val photobookId: Long?,
) : BaseEntity() {
    companion object {
        fun of(
            photo: Photo,
            photobookId: Long?,
        ): PhotoEntity =
            PhotoEntity(
                id = photo.id,
                path = photo.path,
                lat = photo.location?.lat,
                lon = photo.location?.lon,
                location = photo.location?.name,
                date = photo.dateTime,
                photobookId = photobookId,
            )
    }
}
