package com.ggsdh.backend.trip.application

import com.ggsdh.backend.trip.application.dto.response.RandomRestaurantResponse
import com.ggsdh.backend.trip.infrastructure.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RestaurantService(
        private val restaurantRepository: RestaurantRepository
) {
    fun getRandomRestaurant(): List<RandomRestaurantResponse> {
        val recent90Day = LocalDate.now().minusDays(90)
        val result = restaurantRepository.findAllByDataModifiedAfter(recent90Day)

        return result.map {
            RandomRestaurantResponse(it.id, it.firstMenuImage, it.name, 35L, it.sigunguCode.value)
        }.toList()
    }
}
