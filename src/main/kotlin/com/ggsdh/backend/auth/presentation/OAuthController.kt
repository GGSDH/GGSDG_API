package com.ggsdh.backend.auth.presentation

import com.ggsdh.backend.auth.application.AuthService
import com.ggsdh.backend.auth.application.dto.request.LoginRequest
import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.presentation.dto.KakaoLoginRequest
import com.ggsdh.backend.global.dto.BaseResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(
    private val authService: AuthService,
) {
    @PostMapping("/{provider}/login")
    fun kakaoLogin(
        @PathVariable provider: String,
        @RequestBody request: KakaoLoginRequest,
    ): BaseResponse<AuthResponse> {
        val token =
            authService.login(
                LoginRequest(
                    providerType = ProviderType.valueOf(provider),
                    accessToken = request.accessToken,
                    refreshToken = request.refreshToken,
                ),
            )

        return BaseResponse.success(
            AuthResponse.of(
                token.token.accessToken,
                token.member,
            ),
        )
    }
}
