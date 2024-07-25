package com.ggsdh.backend.member.application.dto.response

data class MemberTokenResponse(
        val memberId: Long?,
        val accessToken: String
)
