package com.ggsdh.backend.trip.application

import com.ggsdh.backend.trip.application.dto.response.RandomRestaurantResponse
import com.ggsdh.backend.trip.infrastructure.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val likeService: LikeService,
) {
    fun getRandomRestaurant(memberId: Long): List<RandomRestaurantResponse> {
        val recent90Day = LocalDate.now().minusDays(90)
        val result = restaurantRepository.findAllByDataModifiedAfter(recent90Day)
        val userLikes = likeService.getAllLikedTourAreaIdsByMember(memberId)

        return result
            .map {
                RandomRestaurantResponse(
                    it.id,
                    it.firstMenuImage,
                    it.name,
                    it.likes,
                    it.sigunguCode.value,
                    userLikes.contains(it.id),
                )
            }.toList()
    }
}
