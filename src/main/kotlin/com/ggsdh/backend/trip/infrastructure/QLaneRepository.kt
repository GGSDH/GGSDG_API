package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.application.dto.response.LaneResponses
import com.ggsdh.backend.trip.domain.QLane.lane
import com.ggsdh.backend.trip.domain.QLaneMapping.laneMapping
import com.ggsdh.backend.trip.domain.QTripTheme.tripTheme
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QLaneRepository(
        private val query: JPAQueryFactory,
) {
    fun getThemeLanesByMemberId(id: Long): List<LaneResponses> =
            query
                    .select(themeLanesConstructorExpression())
                    .from(lane)
                    .leftJoin(tripTheme)
                    .on(tripTheme.tripThemeConstants.eq(lane.tripThemeConstants))
                    .leftJoin(laneMapping)
                    .on(laneMapping.lane.id.eq(lane.id)) // Adjust the join condition as needed
                    .where(tripTheme.member.id.eq(id))
                    .orderBy(Expressions.numberTemplate(Long::class.java, "RAND()").asc())
//            .limit(3) 전체조회로 변경
                    .fetch()

    fun findLaneById(id: Long): LaneResponses? =
            query
                    .select(themeLanesConstructorExpression())
                    .from(lane)
                    .leftJoin(laneMapping)
                    .on(laneMapping.lane.id.eq(lane.id)) // Adjust the join condition as needed
                    .where(lane.id.eq(id))
                    .fetchFirst()

    fun getAllLanesWithTourAreaId(tourAreaId: Long): List<Long> {
        val lanes =
                query
                        .selectDistinct(
                                laneMapping.id,
                        ).from(laneMapping)
                        .where(
                                laneMapping.tourArea.id
                                        .eq(tourAreaId),
                        ).fetch()

        return lanes
    }

    private fun themeLanesConstructorExpression(): ConstructorExpression<LaneResponses>? =
            Projections.constructor(
                    LaneResponses::class.java,
                    lane.id,
                    lane.name,
                    lane.tripThemeConstants,
                    lane.likes,
                    laneMapping.tourArea.image,
                    lane.days,
            )
}
