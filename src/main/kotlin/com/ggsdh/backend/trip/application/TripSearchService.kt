package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.trip.application.dto.response.SearchedResponse
import com.ggsdh.backend.trip.domain.PopularKeyword
import com.ggsdh.backend.trip.exception.TripError
import com.ggsdh.backend.trip.infrastructure.LaneMappingRepository
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.PopularKeywordRepository
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import org.springframework.stereotype.Service

@Service
class TripSearchService(
        private val laneRepository: LaneRepository,
        private val tourAreaRepository: TourAreaRepository,
        private val popularKeywordRepository: PopularKeywordRepository,
        private val laneMappingRepository: LaneMappingRepository
) {
    fun searchByKeyword(keyword: String): List<SearchedResponse> {

        validateKeyword(keyword)

        val trimmedKeyword = keyword.replace(" ", "")

        val foundLanes = laneRepository.findByNameContainingIgnoreSpaces(trimmedKeyword)
        val foundTourAreas = tourAreaRepository.findByNameContainingIgnoreSpaces(trimmedKeyword)

        if (isSearchResultEmpty(foundLanes, foundTourAreas)) {
            throw BusinessException(TripError.RESULT_NOT_FOUND)
        }

        val lanes = foundLanes.map {
            SearchedResponse(it.id, "LANE", it.tripThemeConstants, it.name, laneMappingRepository.findAllByLaneId(it.id!!).get(0).tourArea!!.sigunguCode)
        }

        val tourAreas = foundTourAreas.map {
            SearchedResponse(it.id, "TOUR_AREA", it.tripThemeConstants, it.name, it.sigunguCode)
        }.shuffled().take(30 - lanes.size)

        val finalResult = lanes + tourAreas

        val foundKeyword = popularKeywordRepository.findByKeyword(keyword)

        if (foundKeyword == null) {
            val newKeyword = PopularKeyword(keyword, 1L)
            popularKeywordRepository.save(newKeyword)
        } else {
            foundKeyword.count += 1L
            popularKeywordRepository.save(foundKeyword)
        }

        return finalResult
    }

    private fun validateKeyword(keyword: String) {
        if (keyword.isBlank()) {
            throw BusinessException(TripError.BLANK_KEYWORD)
        }
    }

    private fun isSearchResultEmpty(foundLanes: List<Any>, foundTourAreas: List<Any>): Boolean {
        return foundLanes.isEmpty() && foundTourAreas.isEmpty()
    }
}
