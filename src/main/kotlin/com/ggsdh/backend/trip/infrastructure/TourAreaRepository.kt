package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.TourArea
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TourAreaRepository : JpaRepository<TourArea, Long> {
    @Query("SELECT t FROM TourArea t WHERE t.sigunguCode IN :sigunguCodes")
    fun findAllBySigunguCodeIn(sigunguCodes: List<SigunguCode>): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE t.sigunguCode IN :sigunguCodes AND t.tripThemeConstants = :tripThemeConstant")
    fun findAllBySigunguCodeInAndTripThemeConstant(
            sigunguCodes: List<SigunguCode>,
            tripThemeConstant: TripThemeConstants,
    ): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE t.tripThemeConstants = :tripThemeConstant")
    fun findAllByTripThemeConstant(tripThemeConstant: TripThemeConstants): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE t.ranking IS NOT NULL ORDER BY t.ranking")
    fun findAllByRankingArea(): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE t.ranking IS NOT NULL AND t.sigunguCode IN :sigunguCodes ORDER BY t.ranking")
    fun findAllByRankingAreaBySigunguCodes(sigunguCodes: List<SigunguCode>): List<TourArea>

    @Query("SELECT t FROM TourArea t WHERE REPLACE(t.name, ' ', '') LIKE CONCAT('%', :keyword, '%')")
    fun findByNameContainingIgnoreSpaces(@Param("keyword") keyword: String): List<TourArea>
}
