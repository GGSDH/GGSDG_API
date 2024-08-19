package com.ggsdh.backend.photobook.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.photobook.application.PhotoBookService
import com.ggsdh.backend.photobook.application.PhototicketService
import com.ggsdh.backend.photobook.application.dto.PhotoTicketResponse
import com.ggsdh.backend.photobook.domain.PhotoBook
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1/photo-ticket")
class PhotoTicketController(
    private val photobookService: PhotoBookService,
    private val phototicketService: PhototicketService,
) {
    @GetMapping("/random")
    fun getRandomPhotobook(
        @AuthId memberId: Long,
    ): BaseResponse<PhotoBook> {
        val photoBook = photobookService.getRandomPhotobookWithoutPhotoTicket(memberId)
            ?: throw BusinessException(GlobalError.PHOTOBOOK_NOT_FOUND)

        return BaseResponse.success(photoBook)
    }

    @PostMapping("/{id}/save")
    fun savePhotoTicket(
        @PathVariable id: String,
        @AuthId memberId: Long,
    ): BaseResponse<PhotoTicketResponse>{
        val photo = phototicketService.save(id, memberId)

        return BaseResponse.success(photo)
    }

    @GetMapping()
    fun getAllPhotoTickets(@AuthId memberId: Long): List<PhotoTicketResponse> {
        return phototicketService.getAllPhototicketsByMemberId(memberId)
    }
}