package com.ggsdh.backend.photobook.domain

import com.ggsdh.backend.photobook.application.dto.PhotoTicketResponse

interface PhotoBookRepository {
    fun delete(
        memberId: Long,
        photoBookId: Long,
    ): Boolean

    fun save(
        memberId: Long,
        photoBook: PhotoBook,
    ): PhotoBook

    fun getAllByMemberId(memberId: Long): List<PhotoBook>

    fun find(
        memberId: Long,
        photobookId: Long,
    ): PhotoBook?

    fun deletePhotos(
        memberId: Long,
        photoId: List<String>,
    ): Boolean

    fun getAllPhotobookWithoutPhototicket(memberId: Long): List<PhotoBook>
    fun getAllPhototickets(memberId: Long): List<PhotoTicketResponse>
}
