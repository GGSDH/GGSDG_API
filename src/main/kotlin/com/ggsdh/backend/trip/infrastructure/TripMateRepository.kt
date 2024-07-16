package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.TripMate
import org.springframework.data.jpa.repository.JpaRepository

interface TripMateRepository : JpaRepository<TripMate, Long>
