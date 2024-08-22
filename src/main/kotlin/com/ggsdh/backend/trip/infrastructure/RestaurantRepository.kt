package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.Restaurant
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface RestaurantRepository : JpaRepository<Restaurant, Long> {


    @Query("SELECT r FROM Restaurant r WHERE r.dataModifiedAt > :recentDay ORDER BY FUNCTION('RAND')")
    fun findAllByDataModifiedAfter(recentDay: LocalDate): List<Restaurant>

    @Query("SELECT r FROM Restaurant r WHERE r.dataModifiedAt > :recentDay AND r.sigunguCode IN :sigunguCodes ORDER BY FUNCTION('RAND')")
    fun findAllByDataModifiedAfter(recentDay: LocalDate, sigunguCodes: List<SigunguCode>?): List<Restaurant>
}
