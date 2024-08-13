package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.TourArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TourAreaRepository : JpaRepository<TourArea, Long> {
    @Query("SELECT t FROM TourArea t WHERE t.sigunguCode IN :sigunguCodes")
    fun findAllBySigunguCodeIn(sigunguCodes: List<String>): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE t.ranking IS NOT NULL ORDER BY t.ranking")
    fun findAllByRankingArea(): List<TourArea>
}
