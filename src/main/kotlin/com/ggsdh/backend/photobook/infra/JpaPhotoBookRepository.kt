package com.ggsdh.backend.photobook.infra

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaPhotoBookRepository : JpaRepository<PhotobookEntity, Long> {
    fun findAllByMemberId(memberId: Long): List<PhotobookEntity>

    fun findByMemberIdAndId(
        memberId: Long,
        photobookId: Long,
    ): PhotobookEntity?

    @Query(
        """
        SELECT pb
        FROM PhotobookEntity pb
        WHERE pb.memberId = :memberId
        AND NOT EXISTS (
            SELECT pt
            FROM PhotoEntity pt
            WHERE pt.isPhototicket = true and pt.photobookId = pb.id
        )
        """
    )
    fun findAllWithoutPhototicketByMemberId(memberId: Long): List<PhotobookEntity>
}
