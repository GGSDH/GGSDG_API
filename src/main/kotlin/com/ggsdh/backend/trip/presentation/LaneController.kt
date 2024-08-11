package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.trip.application.LaneService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/lane")
class LaneController(
    private val laneService: LaneService,
) {
    @GetMapping("/test")
    fun test() {
        laneService.test()
    }

    @GetMapping
    fun getLanes(): BaseResponse<Unit> {
        val laneResponse = laneService.getThemeLane()
        return BaseResponse.success(laneResponse)
    }
}
