package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface RestaurantRepository : JpaRepository<Restaurant, Long> {


    @Query("SELECT r FROM Restaurant r WHERE r.dataModifiedAt > :recentDay")
    fun findAllByDataModifiedAfter(recentDay: LocalDate): List<Restaurant>
}
