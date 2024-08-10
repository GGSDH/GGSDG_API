package com.ggsdh.backend.photobook.infra

import com.ggsdh.backend.photobook.domain.Location
import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook
import com.ggsdh.backend.photobook.domain.PhotoBookRepository
import org.springframework.stereotype.Repository

@Repository
class PhotoBookRepositoryImpl(
    private val jpaPhotoBookRepository: JpaPhotoBookRepository,
    private val jpaPhotoRepository: JpaPhotoRepository,
) : PhotoBookRepository {
    override fun delete(
        memberId: Long,
        photoBookId: Long,
    ): Boolean {
        val entity = jpaPhotoBookRepository.findByMemberIdAndId(memberId, photoBookId) ?: return false

        val photos = jpaPhotoRepository.findAllByPhotobookId(entity.id)
        jpaPhotoRepository.deleteAllById(photos.map { it.id!! })
        jpaPhotoBookRepository.delete(entity)

        return true
    }

    override fun save(
        memberId: Long,
        photoBook: PhotoBook,
    ): PhotoBook {
        val entity = photoBook.toEntity(memberId)

        val savedEntity = jpaPhotoBookRepository.save(entity)

        val photos =
            photoBook.photos.map { photo ->
                jpaPhotoRepository.save(photo.toEntity(savedEntity.id)).toDomain()
            }

        return savedEntity.toDomain(photos)
    }

    override fun getAllByMemberId(memberId: Long): List<PhotoBook> {
        val entities = jpaPhotoBookRepository.findAllByMemberId(memberId)

        return entities.map { entity ->
            val photos = jpaPhotoRepository.findAllByPhotobookId(entity.id).map { it.toDomain() }

            entity.toDomain(photos)
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
        photoId: List<Long>,
    ): Boolean {
        val photos = jpaPhotoRepository.findAllById(photoId)

        if (photos.isEmpty()) {
            return false
        }

        jpaPhotoRepository.deleteAll(photos)

        return true
    }
}

fun PhotoEntity.toDomain(): Photo =
    Photo(
        id = this.id!!,
        path = this.path,
        location =
            if (this.lat != null && this.lon != null) {
                Location(this.lat!!, this.lon!!, this.location, "경기도")
            } else {
                null
            },
        dateTime = this.date,
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
