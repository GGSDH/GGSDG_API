package com.ggsdh.backend.member.application.dto.response

import com.ggsdh.backend.member.domain.Member

data class MemberTokenResponse(
    val member: Member?,
    val accessToken: String,
)
