package com.ggsdh.backend.trip.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.trip.application.TripSearchService
import com.ggsdh.backend.trip.application.TripThemeService
import com.ggsdh.backend.trip.application.dto.request.KeywordRequest
import com.ggsdh.backend.trip.application.dto.request.OnboardingRequest
import com.ggsdh.backend.trip.application.dto.response.SearchedResponse
import com.ggsdh.backend.trip.presentation.dto.KeywordResponse
import com.ggsdh.backend.trip.presentation.dto.TripThemeOutputDto
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1")
class TripController(
        val tripThemeService: TripThemeService,
        val tripSearchService: TripSearchService
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

    @PostMapping("/trip/search")
    fun getSearchResponse(@RequestBody keywordRequest: KeywordRequest): BaseResponse<List<SearchedResponse>> {
        val result = tripSearchService.searchByKeyword(keywordRequest.keyword)
        return BaseResponse.success(result)
    }

    @GetMapping("/trip/popularKeyword")
    fun getPopularKeywordResponse(): BaseResponse<List<KeywordResponse>> {
        val result = tripSearchService.getPopularKeywords()
        return BaseResponse.success(result)
    }
}
