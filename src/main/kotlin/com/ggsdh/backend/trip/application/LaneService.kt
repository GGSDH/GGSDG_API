package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.trip.exception.TripError
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import org.springframework.stereotype.Service

@Service
class LaneService(
        private val laneRepository: LaneRepository
) {

    fun getThemeLane(): Unit {
        val lane = laneRepository.findById(889L)
                .orElseThrow { BusinessException(TripError.LANE_NOT_FOUND) }

        return Unit
    }
}
