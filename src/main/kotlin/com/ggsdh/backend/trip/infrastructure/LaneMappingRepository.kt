package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.LaneMapping
import org.springframework.data.jpa.repository.JpaRepository

interface LaneMappingRepository : JpaRepository<LaneMapping, Long> {

    fun findAllByLaneId(laneId: Long): List<LaneMapping>
}
