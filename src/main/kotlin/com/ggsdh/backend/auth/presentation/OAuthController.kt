package com.ggsdh.backend.auth.presentation

import com.ggsdh.backend.auth.application.AuthService
import com.ggsdh.backend.auth.application.dto.request.LoginRequest
import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.presentation.dto.KakaoLoginRequest
import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(
        private val authService: AuthService,
) {
    @GetMapping("/refresh")
    fun refreshToken(
            @AuthId id: Long,
    ): BaseResponse<AuthResponse> {
        val token = authService.refreshToken(id)

        return BaseResponse.success(
                AuthResponse.of(
                        token.token,
                        token.role,
                ),
        )
    }

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
                        token.token,
                        token.role,
                ),
        )
    }
}
