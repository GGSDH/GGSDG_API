package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.RestaurantService
import com.ggsdh.backend.trip.application.dto.response.RandomRestaurantResponse
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/restaurant")
class RestaurantController(
        private val restaurantService: RestaurantService,
) {
    @GetMapping
    fun getRandomRestaurants(
            @AuthId memberId: Long,
            @RequestParam sigunguCodes: List<SigunguCode>?
    ): BaseResponse<List<RandomRestaurantResponse>> {
        val result = restaurantService.getRandomRestaurant(memberId, sigunguCodes)
        return BaseResponse.success(result)
    }
}
