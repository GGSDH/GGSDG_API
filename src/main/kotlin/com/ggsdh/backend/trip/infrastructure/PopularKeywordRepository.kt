package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.PopularKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface PopularKeywordRepository : JpaRepository<PopularKeyword, Long> {

    fun findByKeyword(keyword: String): PopularKeyword?

}
