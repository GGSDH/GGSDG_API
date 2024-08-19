package com.ggsdh.backend.photobook.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.photobook.application.PhotoBookService
import com.ggsdh.backend.photobook.domain.PhotoBook
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/photo-ticket")
class PhotoTicketController(
    private val photobookService: PhotoBookService,
) {
    @GetMapping("/random")
    fun getRandomPhotobook(
        @AuthId memberId: Long,
    ): BaseResponse<PhotoBook> {
        val photoBook = photobookService.getRandomPhotobookWithoutPhotoTicket(memberId)
            ?: throw BusinessException(GlobalError.PHOTOBOOK_NOT_FOUND)

        return BaseResponse.success(photoBook)
    }

    @PostMapping("/:id/save")
    fun savePhotoTicket() {

    }

    @GetMapping()
    fun getAllPhotoTickets() {

    }
}