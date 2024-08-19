package com.ggsdh.backend.photobook.application.dto

import com.ggsdh.backend.photobook.domain.Photo
import com.ggsdh.backend.photobook.domain.PhotoBook

class PhotoTicketResponse(
    val photoBook: PhotoBook,
    val phototicket: Photo,
)