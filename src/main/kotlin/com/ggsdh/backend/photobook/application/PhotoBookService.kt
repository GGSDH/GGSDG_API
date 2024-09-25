package com.ggsdh.backend.photobook.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook
import com.ggsdh.backend.photobook.domain.PhotoBookRepository
import com.ggsdh.backend.photobook.presentation.dto.CreatePhotobookRequest
import com.ggsdh.backend.trip.exception.TripError
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Component
class PhotoBookService(
    private val naverGcService: NaverGcService,
    private val photoBookRepository: PhotoBookRepository,
) {
    fun deletePhotoBook(
        memberId: Long,
        photobookId: Long,
    ) = photoBookRepository.delete(memberId, photobookId)

    fun getAllPhotoBooks(memberId: Long): List<PhotoBook> = photoBookRepository.getAllByMemberId(memberId)

    fun getPhotoBook(
        memberId: Long,
        photobookId: Long,
    ): PhotoBook {
        val photoBook =
            photoBookRepository.find(memberId, photobookId) ?: throw BusinessException(
                GlobalError.PHOTOBOOK_NOT_FOUND,
            )

        return photoBook
    }

    @Transactional
    fun getPhoto(
        memberId: Long,
        input: CreatePhotobookRequest,
    ): PhotoBook {
        val photos: MutableList<Photo> = mutableListOf()
        val chunkSize = 10
        val photoChunks = input.photos.chunked(chunkSize)

        photoChunks.forEach { chunk ->
            chunk.parallelStream().forEach { photo ->
                naverGcService.getLocation(photo.toGetLocationInputDto()).thenApply {
                    photos.add(
                        Photo(
                            id = UUID.randomUUID().toString(),
                            path = photo.path,
                            location = it,
                            dateTime = photo.toGetLocationInputDto().date,
                        ),
                    )
                }.exceptionally {
                    println(it)

                    null
                }.join()
            }
        }

        println("photo: ${input.photos}")
        println("photo: ${photos}")
        val filteredPhoto = photos.filter {
            it.location?.city == "경기도" || it.location?.city?.contains("경기") == true
        }

        println("filteredPhoto: $filteredPhoto")
        val photoBook =
            PhotoBook.create(
                id = -1,
                input.title,
                LocalDateTime.parse(input.startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                LocalDateTime.parse(input.endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                filteredPhoto,
            )

        if(photoBook.photos.isEmpty()) {
            throw BusinessException(TripError.EMPTY_PHOTOBOOK)
        }
        val saved =
            photoBookRepository.save(
                memberId,
                photoBook,
            )

        return saved
    }

    fun deletePhoto(
        memberId: Long,
        photoId: List<String>,
    ) = photoBookRepository.deletePhotos(memberId, photoId)

    fun getRandomPhotobookWithoutPhotoTicket(
        memberId: Long,
    ): PhotoBook? {
        val photoBooks = photoBookRepository.getAllPhotobookWithoutPhototicket(memberId)
        return photoBooks.firstOrNull()
    }
}
