package com.ggsdh.backend.photobook.infra

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaPhotoRepository : JpaRepository<PhotoEntity, String> {
    fun findAllByPhotobookId(photobookId: Long): List<PhotoEntity>

    fun findFirstByPhotobookId(photobookId: Long): PhotoEntity?
    fun findFirstByPhotobookIdIn(photobookIds: List<Long>): List<PhotoEntity>
    fun findAllByPhotobookIdAndIsPhototicket(photobookId: Long, isPhototicket: Boolean): List<PhotoEntity>
}
