package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.trip.exception.TripError
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.QLaneRepository
import org.springframework.stereotype.Service

@Service
class LaneService(
    private val laneRepository: LaneRepository,
    private val qLaneRepository: QLaneRepository,
) {
    fun test() {
        val lane = qLaneRepository.test()
    }

    fun getThemeLane() {
        val lane =
            laneRepository
                .findById(889L)
                .orElseThrow { BusinessException(TripError.LANE_NOT_FOUND) }

        return
    }
}
