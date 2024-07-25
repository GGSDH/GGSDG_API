package com.ggsdh.backend.auth.application.dto.response

import com.ggsdh.backend.auth.domain.constants.Role

class AuthResponse(
    val token: TokenResponse,
    val role: Role,
) {
    companion object {
        fun of(
            accessToken: String,
            role: Role,
        ): AuthResponse =
            AuthResponse(
                TokenResponse(accessToken),
                role,
            )
    }
}
