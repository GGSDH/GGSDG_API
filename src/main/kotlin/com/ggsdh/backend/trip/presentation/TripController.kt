package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.TripMateService
import com.ggsdh.backend.trip.application.TripThemeService
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1")
class TripController(
        val tripThemeService: TripThemeService,
        val tripMateService: TripMateService
) {

    @PostMapping("/trip/onboarding")
    fun updateOnboardingInfo(
            @AuthId id: Long,
            @RequestBody onboardingRequest: OnboardingRequest
    ): BaseResponse<Any> {
        tripThemeService.updateTripThemes(id, onboardingRequest)
        tripMateService.updateTripMates(id, onboardingRequest)

        return BaseResponse.success(null);
    }

    @GetMapping("/trip/onboarding/themes")
    fun getOnboardingThemes(): BaseResponse<List<TripThemeConstants>> {
        return BaseResponse.success(tripThemeService.getOnboardingThemes())
    }
}
