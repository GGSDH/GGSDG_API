package com.ggsdh.backend.photobook.application.dto

import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook
import com.ggsdh.backend.photobook.presentation.dto.PhotoBookResponse

class PhotoTicketResponse(
    val photoBook: PhotoBookResponse,
    val photo: Photo,
)