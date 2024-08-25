package com.ggsdh.backend.photobook.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.photobook.application.PhotoBookService
import com.ggsdh.backend.photobook.presentation.dto.CreatePhotobookRequest
import com.ggsdh.backend.photobook.presentation.dto.DeletePhotoRequest
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookListingResponse
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookResponse
import jakarta.transaction.Transactional
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/photobook")
class PhotoBookController(
    val photoLocationService: PhotoBookService,
) {
    @DeleteMapping("/{id}")
    fun delete(
        @AuthId memberId: Long,
        @PathVariable id: Long,
    ): BaseResponse<Any> = BaseResponse.success(photoLocationService.deletePhotoBook(memberId, id))

    @PostMapping("/photo/delete")
    fun deletePhoto(
        @AuthId memberId: Long,
        @RequestBody request: DeletePhotoRequest,
    ): BaseResponse<Any> {
        photoLocationService.deletePhoto(memberId, request.photoId)

        return BaseResponse.success(null)
    }

    @GetMapping()
    fun getAllPhotoBooks(
        @AuthId id: Long,
    ): BaseResponse<List<PhotoBookListingResponse>> =
        BaseResponse.success(
            photoLocationService.getAllPhotoBooks(id).map {
                PhotoBookListingResponse(
                    it.id,
                    it.title,
                    it.startDateTime.toString(),
                    it.endDateTime.toString(),
                    it.photos.firstOrNull()?.path ?: "",
                    it.getLocation(),
                    it.getPhotoTicket()
                )
            },
        )

    @GetMapping("/{id}")
    fun getPhotoBook(
        @AuthId memberId: Long,
        @PathVariable id: Long,
    ): BaseResponse<PhotoBookResponse> {
        val photoBook = photoLocationService.getPhotoBook(memberId, id)

        return BaseResponse.success(
            PhotoBookResponse(
                photoBook.id,
                photoBook.title,
                photoBook.startDateTime.toString(),
                photoBook.endDateTime.toString(),
                photoBook.getDailyPhotoGroups(),
                photoBook.getLocation(),
                photoBook.getPhotoTicket()
            ),
        )
    }

    @PostMapping()
    fun save(
        @AuthId id: Long,
        @RequestBody request: CreatePhotobookRequest,
    ): BaseResponse<Any> {
        val photoBook =
            photoLocationService
                .getPhoto(
                    id,
                    request,
                )

        return BaseResponse.success(
            PhotoBookResponse(
                photoBook.id,
                photoBook.title,
                photoBook.startDateTime.toString(),
                photoBook.endDateTime.toString(),
                photoBook.getDailyPhotoGroups(),
                photoBook.getLocation(),
                photoBook.getPhotoTicket()
            ),
        )
    }
}
