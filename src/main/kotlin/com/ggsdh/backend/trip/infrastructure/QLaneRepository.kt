package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.QLane
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QLaneRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun test() {
        val lanes =
            jpaQueryFactory
                .select(
                    QLane.lane,
                ).from(
                    QLane.lane,
                ).fetch()

        println(lanes)
    }
}
