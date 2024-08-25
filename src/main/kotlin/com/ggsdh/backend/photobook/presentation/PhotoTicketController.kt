package com.ggsdh.backend.photobook.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.photobook.application.PhotoBookService
import com.ggsdh.backend.photobook.application.PhototicketService
import com.ggsdh.backend.photobook.application.dto.PhotoTicketResponse
import com.ggsdh.backend.photobook.domain.PhotoBook
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookResponse
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1/photo-ticket")
class PhotoTicketController(
    private val photobookService: PhotoBookService,
    private val phototicketService: PhototicketService,
    private val objectMapper: ObjectMapper,
) {
    @GetMapping("/random")
    fun getRandomPhotobook(
        @AuthId memberId: Long,
    ): BaseResponse<PhotoBookResponse> {
        val photoBook = photobookService.getRandomPhotobookWithoutPhotoTicket(memberId)
            ?: throw BusinessException(GlobalError.PHOTOBOOK_NOT_FOUND)


        return BaseResponse.success(
            PhotoBookResponse(
                photoBook.id,
                photoBook.title,
                photoBook.startDateTime.toString(),
                photoBook.endDateTime.toString(),
                photoBook.getDailyPhotoGroups(),
                photoBook.getLocation()
                ,
                photoBook.getPhotoTicket()
            ),
        )
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
    fun getAllPhotoTickets(@AuthId memberId: Long): BaseResponse<List<PhotoTicketResponse>> {
        return BaseResponse.success(phototicketService.getAllPhototicketsByMemberId(memberId))
    }
}