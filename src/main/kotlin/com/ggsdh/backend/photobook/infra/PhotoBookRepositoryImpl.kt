package com.ggsdh.backend.photobook.infra

import com.ggsdh.backend.photobook.application.dto.PhotoTicketResponse
import com.ggsdh.backend.photobook.domain.Location
import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook
import com.ggsdh.backend.photobook.domain.PhotoBookRepository
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookResponse
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PhotoBookRepositoryImpl(
    private val jpaPhotoBookRepository: JpaPhotoBookRepository,
    private val jpaPhotoRepository: JpaPhotoRepository,
    private val jdbcTemplate: JdbcTemplate,
) : PhotoBookRepository {
    override fun delete(
        memberId: Long,
        photoBookId: Long,
    ): Boolean {
        val entity = jpaPhotoBookRepository.findByMemberIdAndId(memberId, photoBookId) ?: return false

        val photos = jpaPhotoRepository.findAllByPhotobookId(entity.id)
        jpaPhotoRepository.deleteAllById(photos.map { it.id })
        jpaPhotoBookRepository.delete(entity)

        return true
    }

    private fun batchInsertPhotos(
        photos: List<Photo>,
        photobookId: Long,
    ) {
        val sql =
            """
            INSERT INTO tb_photo (photo_id, path, lat, lon, location, date, photobook_id, created_date, updated_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()

        jdbcTemplate.batchUpdate(
            sql,
            photos.map { photo ->
                arrayOf(
                    photo.id.toString(),
                    photo.path,
                    photo.location?.lat,
                    photo.location?.lon,
                    photo.location?.name,
                    photo.dateTime,
                    photobookId,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                )
            },
        )
    }

    override fun save(
        memberId: Long,
        photoBook: PhotoBook,
    ): PhotoBook {
        val entity = photoBook.toEntity(memberId)

        val savedEntity = jpaPhotoBookRepository.save(entity)

        batchInsertPhotos(photoBook.photos, savedEntity.id)

        return savedEntity.toDomain(photoBook.photos)
    }

    override fun getAllByMemberId(memberId: Long): List<PhotoBook> {
        val entities = jpaPhotoBookRepository.findAllByMemberId(memberId)

        val photos = jpaPhotoRepository.findAllByPhotobookIdIn(entities.map { it.id })
            .groupBy { it.photobookId }
            .mapValues { it.value.map { photo -> photo.toDomain() } }

        return entities.map { entity ->
            entity.toDomain(photos[entity.id] ?: emptyList())
        }
    }

    override fun find(
        memberId: Long,
        photobookId: Long,
    ): PhotoBook? {
        val entity = jpaPhotoBookRepository.findByMemberIdAndId(memberId, photobookId) ?: return null

        val photos = jpaPhotoRepository.findAllByPhotobookId(entity.id).map { it.toDomain() }

        return entity.toDomain(photos)
    }

    override fun deletePhotos(
        memberId: Long,
        photoId: List<String>,
    ): Boolean {
        val photos = jpaPhotoRepository.findAllById(photoId)

        if (photos.isEmpty()) {
            return false
        }

        jpaPhotoRepository.deleteAll(photos)

        return true
    }

    override fun getAllPhotobookWithoutPhototicket(memberId: Long): List<PhotoBook> {
        val entities = jpaPhotoBookRepository.findAllWithoutPhototicketByMemberId(memberId)

        return entities.map { entity ->
            val photos = jpaPhotoRepository.findAllByPhotobookId(entity.id).map { it.toDomain() }

            entity.toDomain(photos)
        }
    }

    override fun getAllPhototickets(memberId: Long): List<PhotoTicketResponse> {
        val entities = jpaPhotoBookRepository.findAllWithPhotoTicket(memberId)

        return entities.mapNotNull { entity ->
            val photo = jpaPhotoRepository.findAllByPhotobookIdAndIsPhototicket(entity.id, true)
                .firstOrNull() ?: return@mapNotNull null

            val photobook = entity.toDomain(listOf(photo.toDomain()))
            PhotoTicketResponse(
                photoBook = PhotoBookResponse.from(photobook),
                photo = photo.toDomain(),
            )
        }
    }
}

fun PhotoEntity.toDomain(): Photo =
    Photo(
        id = this.id,
        path = this.path,
        location =
        if (this.lat != null && this.lon != null) {
            Location(this.lat!!, this.lon!!, this.location, "경기도")
        } else {
            null
        },
        dateTime = this.date,
        isPhototicket = this.isPhototicket,
    )

fun Photo.toEntity(photobookId: Long): PhotoEntity =
    PhotoEntity(
        id = this.id,
        path = this.path,
        lat = this.location?.lat,
        lon = this.location?.lon,
        location = this.location?.name,
        date = this.dateTime,
        photobookId = photobookId,
    )

fun PhotoBook.toEntity(memberId: Long): PhotobookEntity =
    PhotobookEntity(
        id = this.id,
        title = this.title,
        startDate = this.startDateTime,
        endDate = this.endDateTime,
        memberId = memberId,
    )

fun PhotobookEntity.toDomain(photos: List<Photo>): PhotoBook =
    PhotoBook.create(
        id = this.id,
        title = this.title,
        startDateTime = this.startDate,
        endDateTime = this.endDate,
        photos = photos,
    )
