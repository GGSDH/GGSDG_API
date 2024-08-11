package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.domain.QLane.lane
import com.ggsdh.backend.trip.domain.QTripTheme.tripTheme
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QLaneRepository(
        private val query: JPAQueryFactory,
) {
    fun getThemeLanesByMemberId(id: Long): List<LaneResponses> {
        val lanes = query
                .select(lane)
                .from(lane)
                .leftJoin(tripTheme)
                .on(tripTheme.tripThemeConstants.eq(lane.tripThemeConstants))
                .where(tripTheme.member.id.eq(id))
                .orderBy(Expressions.numberTemplate(Long::class.java, "RAND()").asc())
                .limit(3)
                .fetch()

        return lanes.map {
            LaneResponses(it.id, it.name, it.tripThemeConstants, it.likes)
        }.toList()
    }
}
