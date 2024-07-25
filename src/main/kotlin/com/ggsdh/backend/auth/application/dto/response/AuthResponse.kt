package com.ggsdh.backend.auth.application.dto.response

import com.ggsdh.backend.member.domain.Member

class AuthResponse(
    val token: TokenResponse,
    val member: Member,
) {
    companion object {
        fun of(
            accessToken: String,
            member: Member,
        ): AuthResponse =
            AuthResponse(
                TokenResponse(accessToken),
                member,
            )
    }
}
