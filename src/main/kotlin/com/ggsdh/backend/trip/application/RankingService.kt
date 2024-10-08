package com.ggsdh.backend.trip.application

import com.ggsdh.backend.trip.application.dto.response.RankingRestaurantResponse
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import org.springframework.stereotype.Service

@Service
class RankingService(
        private val tourAreaRepository: TourAreaRepository,
        private val likeService: LikeService,
) {
    fun findAllRankingAreaWithRegionFilter(
            memberId: Long,
            region: List<SigunguCode>,
    ): List<RankingRestaurantResponse> {
        val result = tourAreaRepository.findAllByRankingArea()
        val userLikes = likeService.getAllLikedTourAreaIdsByMember(memberId)

        return result
                .filter {
                    region.contains(it.sigunguCode)
                }.mapIndexed { index, it ->
                    RankingRestaurantResponse(
                            it.id,
                            index + 1,
                            it.image,
                            it.name,
                            it.sigunguCode.value,
                            it.tripThemeConstants,
                            it.likes,
                            userLikes.contains(it.id),
                    )
                }.toList()
    }

    fun findAllRankingArea(
            memberId: Long,
            sigunguCodes: List<SigunguCode>?
    ): List<RankingRestaurantResponse> {
        val result = if (sigunguCodes.isNullOrEmpty()) {
            tourAreaRepository.findAllByRankingArea()
        } else {
            tourAreaRepository.findAllByRankingAreaBySigunguCodes(sigunguCodes)
        }

        val userLikes = likeService.getAllLikedTourAreaIdsByMember(memberId)

        return result
                .mapIndexed { index, it ->
                    RankingRestaurantResponse(
                            it.id,
                            index + 1,
                            it.image,
                            it.name,
                            it.sigunguCode.value,
                            it.tripThemeConstants,
                            it.likes,
                            userLikes.contains(it.id),
                    )
                }.toList()
    }
}
