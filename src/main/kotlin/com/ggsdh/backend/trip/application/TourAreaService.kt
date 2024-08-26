package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import com.ggsdh.backend.trip.application.dto.request.TourAreaSearchRequest
import com.ggsdh.backend.trip.domain.TourArea
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TourAreaService(
        private val tourAreaRepository: TourAreaRepository,
) {
    fun search(
            request: TourAreaSearchRequest,
            pageable: Pageable
    ): Page<TourArea> =
            if (request.sigunguCode.isEmpty()) {
                tourAreaRepository.findAllByTripThemeConstant(request.tripThemeConstant, pageable)
            } else {
                tourAreaRepository.findAllBySigunguCodeInAndTripThemeConstant(
                        request.sigunguCode,
                        request.tripThemeConstant,
                        pageable
                )
            }

    fun getAllBySigunguCodes(sigunguCodes: List<SigunguCode>): List<TourArea> = tourAreaRepository.findAllBySigunguCodeIn(sigunguCodes)

    fun getTourAreaById(tourAreaId: Long): TourArea =
            tourAreaRepository.findById(tourAreaId).orElseThrow {
                BusinessException(GlobalError.PHOTOBOOK_NOT_FOUND)
            }

    fun getNearTourAreas(tourAreaId: Long): List<TourArea> {
        val tourArea = getTourAreaById(tourAreaId)
        val tourAreas = tourAreaRepository.findAll()
        if (tourArea.tripThemeConstants == TripThemeConstants.RESTAURANT) {
            return tourAreas
                    .filter {
                        it.id != tourAreaId &&
                                it.sigunguCode == tourArea.sigunguCode &&
                                it.tripThemeConstants != TripThemeConstants.RESTAURANT
                    }.asSequence()
                    .shuffled()
                    .take(5)
                    .toList()
        }

        return tourAreas
                .filter {
                    it.id != tourAreaId &&
                            it.sigunguCode == tourArea.sigunguCode
                }.asSequence()
                .shuffled()
                .take(5)
                .toList()
    }
}
