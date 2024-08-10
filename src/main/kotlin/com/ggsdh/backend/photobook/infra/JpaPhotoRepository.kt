package com.ggsdh.backend.photobook.infra

import org.springframework.data.jpa.repository.JpaRepository

interface JpaPhotoRepository : JpaRepository<PhotoEntity, Long> {
    fun findAllByPhotobookId(photobookId: Long): List<PhotoEntity>
}
