package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.TripMateService
import com.ggsdh.backend.trip.application.TripThemeService
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Transactional
@RequestMapping("/api/v1")
class TripController(
    val tripThemeService: TripThemeService,
    val tripMateService: TripMateService
) {

    @PostMapping("/trip/onboarding")
    @Tag(name = "Onboarding API")
    @Operation(summary = "온보딩 생성", description = "온보딩 정보를 생성합니다.")
    fun updateOnboardingInfo(
        @AuthId id: Long,
        @RequestBody onboardingRequest: OnboardingRequest
    ): BaseResponse<Any> {
        tripThemeService.updateTripThemes(onboardingRequest)
        tripMateService.updateTripMates(onboardingRequest)

        return BaseResponse.success(null);
    }
}
