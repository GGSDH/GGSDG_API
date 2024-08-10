package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.Lane
import org.springframework.data.jpa.repository.JpaRepository

interface LaneRepository : JpaRepository<Lane, Long> {
}
