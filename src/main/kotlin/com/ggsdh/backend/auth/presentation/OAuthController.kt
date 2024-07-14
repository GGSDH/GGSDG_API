package com.ggsdh.backend.auth.presentation

import com.ggsdh.backend.auth.application.AuthService
import com.ggsdh.backend.auth.application.dto.request.LoginRequest
import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.auth.application.dto.response.TokenResponse
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationServiceFactory
import com.ggsdh.backend.auth.infrastructure.kakao.KakaoMemberIdentificationService
import com.ggsdh.backend.auth.presentation.dto.KakaoLoginRequest
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(
    private val authService: AuthService,
    private val kakaoMemberIdentificationService: KakaoMemberIdentificationService,
    private val memberRepository: MemberRepository,
    private val memberIdentificationServiceFactory: MemberIdentificationServiceFactory,
) {
    @PostMapping("/{provider}/login")
    fun kakaoLogin(
        @PathVariable provider: String,
        @RequestBody request: KakaoLoginRequest,
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(
            AuthResponse(
                success = true,
                member = null,
                token =
                    TokenResponse(
                        accessToken =
                            authService.login(
                                LoginRequest(
                                    providerType = ProviderType.valueOf(provider),
                                    accessToken = request.accessToken,
                                    refreshToken = request.refreshToken,
                                ),
                            ),
                    ),
            ),
        )
    }
}
