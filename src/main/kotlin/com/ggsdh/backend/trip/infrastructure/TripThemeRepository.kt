package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.TripTheme
import org.springframework.data.jpa.repository.JpaRepository

interface TripThemeRepository : JpaRepository<TripTheme, Long>
