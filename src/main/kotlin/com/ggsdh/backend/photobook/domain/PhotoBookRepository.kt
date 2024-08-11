package com.ggsdh.backend.photobook.domain

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
}
