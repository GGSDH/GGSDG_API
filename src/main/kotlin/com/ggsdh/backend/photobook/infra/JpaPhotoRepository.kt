package com.ggsdh.backend.photobook.infra

import org.springframework.data.jpa.repository.JpaRepository

interface JpaPhotoRepository : JpaRepository<PhotoEntity, String> {
    fun findAllByPhotobookId(photobookId: Long): List<PhotoEntity>

    fun findAllByPhotobookIdAndIsPhototicket(photobookId: Long, isPhototicket: Boolean): List<PhotoEntity>
}
