package com.ggsdh.backend.auth.application.dto.response

class AuthResponse(
    val token: TokenResponse,
) {
    companion object {
        fun of(accessToken: String): AuthResponse {
            return AuthResponse(
                TokenResponse(accessToken),
            )
        }
    }
}
