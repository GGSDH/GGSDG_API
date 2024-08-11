package com.ggsdh.backend.trip.presentation.dto

import com.ggsdh.backend.trip.presentation.dto.tourArea.TourAreaResponseDto

class DetailedTourAreaOutputDto(
    val tourArea: TourAreaResponseDto,
    val lanes: List<LaneResponseDto>,
    val otherTourAreas: List<TourAreaResponseDto>,
)
