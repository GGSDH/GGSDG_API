package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.PopularKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PopularKeywordRepository : JpaRepository<PopularKeyword, Long> {

    fun findByKeyword(keyword: String): PopularKeyword?

    @Query("SELECT p FROM PopularKeyword p ORDER BY p.count DESC LIMIT 10")
    fun findPopularKeywords(): List<PopularKeyword>
}
