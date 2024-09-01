package com.ggsdh.backend.trip.application

import com.ggsdh.backend.trip.application.dto.response.RandomRestaurantResponse
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.infrastructure.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RestaurantService(
        private val restaurantRepository: RestaurantRepository,
        private val likeService: LikeService,
) {
    fun getRandomRestaurant(memberId: Long, sigunguCodes: List<SigunguCode>?): List<RandomRestaurantResponse> {
        val recent90Day = LocalDate.now().minusDays(90)
        val result = if (sigunguCodes.isNullOrEmpty()) {
            restaurantRepository.findAllByDataModifiedAfter(recent90Day)
        } else {
            restaurantRepository.findAllByDataModifiedAfter(recent90Day, sigunguCodes)
        }
        val userLikes = likeService.getAllLikedTourAreaIdsByMember(memberId)

        return result
                .map {
                    RandomRestaurantResponse(
                            it.id,
                            it.firstMenuImage ?: it.image,
                            it.name,
                            it.likes,
                            it.sigunguCode.value,
                            userLikes.contains(it.id),
                    )
                }.toList()
    }
}
