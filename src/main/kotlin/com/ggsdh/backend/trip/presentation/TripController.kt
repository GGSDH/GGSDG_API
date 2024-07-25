package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.TripThemeService
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import com.ggsdh.backend.trip.presentation.dto.TripThemeOutputDto
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1")
class TripController(
    val tripThemeService: TripThemeService,
) {
    @PostMapping("/trip/onboarding")
    fun updateOnboardingInfo(
        @AuthId id: Long,
        @RequestBody onboardingRequest: OnboardingRequest,
    ): BaseResponse<Any> {
        tripThemeService.updateTripThemes(id, onboardingRequest)

        return BaseResponse.success(null)
    }

    @GetMapping("/trip/onboarding/themes")
    fun getOnboardingThemes(): BaseResponse<List<TripThemeOutputDto>> =
        BaseResponse.success(
            tripThemeService.getOnboardingThemes().map {
                TripThemeOutputDto(
                    it.name,
                    it.icon,
                    it.title,
                )
            },
        )
}
