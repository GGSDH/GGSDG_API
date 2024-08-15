package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.Lane
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LaneRepository : JpaRepository<Lane, Long> {

    @Query("SELECT t FROM Lane t WHERE REPLACE(t.name, ' ', '') LIKE CONCAT('%', :keyword, '%')")
    fun findByNameContainingIgnoreSpaces(keyword: String): List<Lane>
}
