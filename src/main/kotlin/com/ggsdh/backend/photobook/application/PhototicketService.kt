package com.ggsdh.backend.photobook.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.photobook.application.dto.PhotoTicketResponse
import com.ggsdh.backend.photobook.domain.PhotoBookRepository
import com.ggsdh.backend.photobook.infra.JpaPhotoBookRepository
import com.ggsdh.backend.photobook.infra.JpaPhotoRepository
import com.ggsdh.backend.photobook.infra.toDomain
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookResponse
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PhototicketService(
    private val photoBookService: PhotoBookService,
    private val photoBookRepository: PhotoBookRepository,
    private val photoRepository: JpaPhotoRepository,
) {
    fun getAllPhototicketsByMemberId(memberId: Long): List<PhotoTicketResponse> {
        return photoBookRepository.getAllPhototickets(memberId)
    }

    fun save(photoId: String, memberId: Long): PhotoTicketResponse {
        val photo =
            photoRepository.findById(photoId).orElseThrow { throw BusinessException(GlobalError.PHOTOBOOK_NOT_FOUND) }


        val photobook = photoBookService.getPhotoBook(memberId, photo.photobookId!!)
        val hasPhotoTicket = photobook.photos.any {
            it.isPhototicket
        }

        if (hasPhotoTicket) {
            throw BusinessException(GlobalError.PHOTOBOOK_ALREADY_HAS_PHOTOTICKET)
        }
        photo.isPhototicket = true
        photoRepository.save(photo)


        photobook.photos = listOf()

        return PhotoTicketResponse(
            photo = photo.toDomain(),
            photoBook = PhotoBookResponse.from(photobook),
        )
    }
}