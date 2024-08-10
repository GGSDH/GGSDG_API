package com.ggsdh.backend.photobook.infra

import org.springframework.data.jpa.repository.JpaRepository

interface JpaPhotoBookRepository : JpaRepository<PhotobookEntity, Long> {
    fun findAllByMemberId(memberId: Long): List<PhotobookEntity>

    fun findByMemberIdAndId(
        memberId: Long,
        photobookId: Long,
    ): PhotobookEntity?
}
